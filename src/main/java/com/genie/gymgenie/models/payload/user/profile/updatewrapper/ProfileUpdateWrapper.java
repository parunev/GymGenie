package com.genie.gymgenie.models.payload.user.profile.updatewrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.genie.gymgenie.models.payload.user.profile.InjuryRequest;
import com.genie.gymgenie.models.payload.user.profile.MedicalHistoryRequest;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdateWrapper {
    private ProfileUpdateRequest profileUpdate;
    private ProfileDescriptionUpdateRequest profileDescription;
    private InjuryRequest injury;
    private MedicalHistoryRequest medicalHistory;
}
