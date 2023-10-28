package com.genie.gymgenie.genie;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static dev.langchain4j.data.document.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

@Configuration
@RequiredArgsConstructor
public class GenieSettings {

    private static final int MAX_MESSAGES = 1000;
    private static final int MAX_RESULTS_RETRIEVED = 1;
    private static final double MIN_SCORE = 0.6;

    @Bean
    GenieAgent workoutAgent(ChatLanguageModel chatLanguageModel,
                            @Qualifier("exerciseRetriever") Retriever<TextSegment> retriever) {
        return createGenieAgent(chatLanguageModel, retriever);
    }

    @Bean
    GenieAgent calorieAgent(ChatLanguageModel chatLanguageModel) {
        return createGenieAgent(chatLanguageModel, null);
    }

    @Bean
    GenieAgent foodNounAgent(ChatLanguageModel chatLanguageModel,
                             @Qualifier("foodRetriever") Retriever<TextSegment> retriever) {
        return createGenieAgent(chatLanguageModel, retriever);
    }

    private GenieAgent createGenieAgent(ChatLanguageModel chatLanguageModel, Retriever<TextSegment> retriever) {
        return AiServices.builder(GenieAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(MAX_MESSAGES))
                .retriever(retriever)
                .build();
    }

    @Bean
    @Qualifier("exerciseRetriever")
    Retriever<TextSegment> exerciseRetriever(@Qualifier("exerciseStore") EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        return createRetriever(embeddingStore, embeddingModel);
    }

    @Bean
    @Qualifier("foodRetriever")
    Retriever<TextSegment> foodRetriever(@Qualifier("foodStore") EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        return createRetriever(embeddingStore, embeddingModel);
    }

    private Retriever<TextSegment> createRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, MAX_RESULTS_RETRIEVED, MIN_SCORE);
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    @Qualifier("exerciseStore")
    EmbeddingStore<TextSegment> exerciseStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) throws IOException {
        return createEmbeddingStore(embeddingModel, resourceLoader, "classpath:exercises.xlsx");
    }

    @Bean
    @Qualifier("foodStore")
    EmbeddingStore<TextSegment> foodStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) throws IOException {
        return createEmbeddingStore(embeddingModel, resourceLoader, "classpath:food.txt");
    }

    private EmbeddingStore<TextSegment> createEmbeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader, String resourcePath) throws IOException {
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        Resource resource = resourceLoader.getResource(resourcePath);
        Document document = loadDocument(resource.getFile().toPath());

        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_3_5_TURBO));
        EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        embeddingStoreIngestor.ingest(document);

        return embeddingStore;
    }
}
