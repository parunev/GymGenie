package com.genie.gymgenie.models.payload.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(name = "Request payload for injuries", description = "Request object for injury information")
public class InjuryRequest {

     @JsonProperty("description")
     @Schema(description = "Description of the injury.", example = "Broken left leg", type = "String")
     private String injuryDescription;

     @JsonProperty("occurred")
     @Schema(description = "Date when the injury occurred.", example = "2023-07-15", type = "LocalDate")
     private LocalDate injuryOccurred;
}
