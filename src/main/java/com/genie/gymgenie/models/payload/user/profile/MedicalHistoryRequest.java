package com.genie.gymgenie.models.payload.user.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Request payload for medical history", description = "Request object for medical history information")
public class MedicalHistoryRequest {

    @Schema(description = "Description of the medical history.", example = "I have no significant medical history.", type = "String")
    private String description;
}
