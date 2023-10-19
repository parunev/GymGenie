package com.genie.gymgenie.mapper;

import com.genie.gymgenie.models.Injury;
import com.genie.gymgenie.models.MedicalHistory;
import com.genie.gymgenie.models.UserProfile;
import com.genie.gymgenie.models.UserProfileDescription;
import com.genie.gymgenie.models.payload.user.InjuryRequest;
import com.genie.gymgenie.models.payload.user.MedicalHistoryRequest;
import com.genie.gymgenie.models.payload.user.UserProfileDescriptionRequest;
import com.genie.gymgenie.models.payload.user.UserProfileRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfile requestToUserProfile(UserProfileRequest request);
    UserProfileDescription requestToUserProfileDescription(UserProfileDescriptionRequest request);
    List<Injury> requestToInjury(List<InjuryRequest> request);
    List<MedicalHistory> requestToMedicalHistory(List<MedicalHistoryRequest> request);
}
