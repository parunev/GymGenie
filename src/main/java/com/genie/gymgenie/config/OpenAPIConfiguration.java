package com.genie.gymgenie.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Martin Parunev",
                        email = "parunev@gmail.com"),
                description = """
                        ONE-LINER: GymGenie is an AI-powered application that creates personalized fitness and nutrition plans for individuals.

                        TARGET USER PERSONA: Health-conscious individuals, aged 18-60, looking for customized fitness and nutrition guidance.

                        USER'S PAIN POINTS:
                        1. Lack of time to research and plan workouts and meals.
                        2. Confusion about which exercises and diets are right for their goals.
                        3. Struggling to stay motivated.

                        MAIN VALUE PROPOSITIONS:
                        1. Personalized fitness and nutrition plans.
                        2. AI-driven recommendations for optimal results.
                        3. Progress tracking and motivation features.

                        SALES AND MARKETING CHANNELS:
                        1. Mobile app stores.
                        2. Social media advertising.
                        3. Fitness influencers and partnerships.
                        4. Content marketing (blogs, videos).

                        REVENUE STREAM SOURCES:
                        1. Freemium model with basic features free and premium features for a subscription fee.
                        2. In-app advertisements for non-subscribers.
                        3. Affiliate partnerships with fitness product companies.

                        COST STRUCTURES:
                        1. Development and maintenance of the AI algorithm and app.
                        2. Marketing and advertising expenses.
                        3. Server and hosting costs.

                        KEY ACTIVITIES:
                        1. AI algorithm development and refinement.
                        2. App design and development.
                        3. User engagement and support.
                        4. Marketing and user acquisition.

                        POTENTIAL BUSINESS CHALLENGES:
                        1. Competition from established fitness apps.
                        2. User data privacy concerns.
                        3. Convincing users of the AI's effectiveness.
                        4. Retaining subscribers over time.""",
                title = "GymGenie"
        ),
        servers = {@Server(description = "Local Environment BE", url = "http://localhost:8080"),
        },
        security = @SecurityRequirement(name = "bearerAuth")
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
public class OpenAPIConfiguration {
}
