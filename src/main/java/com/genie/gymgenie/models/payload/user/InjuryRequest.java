    package com.genie.gymgenie.models.payload.user;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Data;

    import java.time.LocalDate;

    @Data
    public class InjuryRequest {

        @JsonProperty("description")
        private String injuryDescription;

        @JsonProperty("occurred")
        private LocalDate injuryOccurred;
    }
