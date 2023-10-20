package com.genie.gymgenie.service;

import com.genie.gymgenie.mapper.UserMapper;
import com.genie.gymgenie.models.*;
import com.genie.gymgenie.models.payload.user.profile.InjuryRequest;
import com.genie.gymgenie.models.payload.user.profile.MedicalHistoryRequest;
import com.genie.gymgenie.models.payload.user.profile.UserProfileRequest;
import com.genie.gymgenie.models.payload.user.profile.updatewrapper.ProfileDescriptionUpdateRequest;
import com.genie.gymgenie.models.payload.user.profile.updatewrapper.ProfileUpdateRequest;
import com.genie.gymgenie.repositories.*;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.utils.email.EmailSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;
import static com.genie.gymgenie.utils.ExceptionThrower.authException;
import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;
import static com.genie.gymgenie.utils.email.RegistrationPatterns.PROFILE_DONE;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileDescriptionRepository userProfileDescriptionRepository;
    private final InjuryRepository injuryRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final UserMapper userMapper;
    private final EmailSender mail;
    private final GenieLogger genie = new GenieLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        genie.info("Attempt to load a user by username: {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", username);
                    throw new UsernameNotFoundException(
                            "User with username " + username + " not found!"
                    );
                });
    }

    public ApiResponse createUserProfile(@Valid UserProfileRequest request) {
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No account associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        if (userProfileRepository.existsByUser(user)){
            genie.warn("User profile already exists for user with username {}", getCurrentUserDetails().getUsername());
            throw resourceException("Dear %s, your profile already exists. If you want to make changes, please go to your profile settings."
                    .formatted(getCurrentUserDetails().getUsername()), HttpStatus.CONFLICT);
        }

        UserProfile userProfile = userMapper.requestToUserProfile(request);
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
        genie.info("User profile created with id: {}", userProfile.getId());

        UserProfileDescription userProfileDescription = userMapper.requestToUserProfileDescription(request.getUserProfileDescription());
        userProfileDescription.setUserProfile(userProfile);
        userProfileDescriptionRepository.save(userProfileDescription);
        genie.info("User profile description created with id: {}", userProfileDescription.getId());


        if (request.getInjuries() != null && !request.getInjuries().isEmpty()){
            List<Injury> injuries = userMapper.requestToInjury(request.getInjuries());

            for(Injury injury : injuries){
                injury.setUserProfile(userProfile);
                injuryRepository.save(injury);
            }

            genie.info("Injuries created");
        } else {
            genie.info("No injuries to create");
        }

        if (request.getMedicalHistories() != null && !request.getMedicalHistories().isEmpty()){
            List<MedicalHistory> medicalHistories = userMapper.requestToMedicalHistory(request.getMedicalHistories());

            for(MedicalHistory medicalHistory : medicalHistories){
                medicalHistory.setUserProfile(userProfile);
                medicalHistoryRepository.save(medicalHistory);
            }
            genie.info("Medical histories created");
        } else {
            genie.info("No medical histories to create");
        }

        mail.send(user.getEmail(), PROFILE_DONE, "GymGenie! Your profile creation is a done deal!");
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your profile has been created successfully!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .build();
    }

    public ApiResponse updateUserProfile(@Valid Object request) {
        User user = userRepository.findByUsername(getCurrentUserDetails().getUsername())
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", getCurrentUserDetails().getUsername());
                    throw resourceException("No account associated with this username(%s) found.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> {
                    genie.warn("User profile not found for user with username {}", getCurrentUserDetails().getUsername());
                    throw resourceException("Dear %s, your profile does not exist. That's strange! Contact us!."
                            .formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        switch (request.getClass().getSimpleName()){
            case "ProfileUpdateRequest"-> {
                ProfileUpdateRequest profileUpdateRequest = (ProfileUpdateRequest) request;
                genie.info("Updating user profile with a wrapper");
                return updateProfile(userProfile, profileUpdateRequest);
            }
            case "ProfileDescriptionUpdateRequest"-> {
                ProfileDescriptionUpdateRequest profileDescriptionUpdateRequest = (ProfileDescriptionUpdateRequest) request;
                genie.info("Updating user profile description with a wrapper");
                return updateDescription(userProfile, profileDescriptionUpdateRequest);
            }
            case "InjuryRequest"-> {
                InjuryRequest injuryRequest = (InjuryRequest) request;
                genie.info("Updating user injury with a wrapper");
                return addInjury(userProfile, injuryRequest);
            }
            case "MedicalHistoryRequest"-> {
                MedicalHistoryRequest medicalHistoryRequest = (MedicalHistoryRequest) request;
                genie.info("Updating user medical history with a wrapper");
                return addMedicalHistory(userProfile, medicalHistoryRequest);
            }
            default -> throw authException("Dear %s, you have tried to update the user profile with an invalid request. Please try again later.".formatted(getCurrentUserDetails().getUsername()), HttpStatus.BAD_REQUEST);
        }
    }

    private ApiResponse addMedicalHistory(UserProfile userProfile, @Valid MedicalHistoryRequest medicalHistoryRequest) {
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .description(medicalHistoryRequest.getDescription())
                .userProfile(userProfile)
                .build();
        genie.info("Medical history created");
        medicalHistoryRepository.save(medicalHistory);
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your medical history has been added successfully!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .build();
    }

    private ApiResponse addInjury(UserProfile userProfile, @Valid InjuryRequest injuryRequest) {
        Injury injury = Injury.builder()
                .userProfile(userProfile)
                .injuryDescription(injuryRequest.getInjuryDescription())
                .injuryOccurred(injuryRequest.getInjuryOccurred())
                .build();
        genie.info("Injury created");
        injuryRepository.save(injury);
        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your injury has been added successfully!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .build();
    }

    @SuppressWarnings("java:S3011")
    private ApiResponse updateDescription(UserProfile userProfile, @Valid ProfileDescriptionUpdateRequest profileDescriptionUpdateRequest) {
        UserProfileDescription description = userProfileDescriptionRepository.findByUserProfile(userProfile)
                .orElseThrow(() -> {
                    genie.warn("User profile description not found for user with username {}", getCurrentUserDetails().getUsername());
                    throw resourceException("Dear %s, your profile description does not exist. That's strange! Contact us!."
                            .formatted(getCurrentUserDetails().getUsername()), HttpStatus.NOT_FOUND);
                });

        Method[] methods = ProfileDescriptionUpdateRequest.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                String methodName = method.getName().substring(3);
                String fieldName = StringUtils.uncapitalize(methodName);
                try {
                    Field descriptionField = UserProfileDescription.class.getDeclaredField(fieldName);
                    if (descriptionField.getType() != List.class){
                        descriptionField.setAccessible(true);
                        Object updatedValue = method.invoke(profileDescriptionUpdateRequest);
                        Object currentValue = descriptionField.get(description);
                        if (updatedValue != null && !updatedValue.equals(currentValue)) {
                            genie.info("Updating user {} from {} to {}", fieldName, currentValue, updatedValue);
                            descriptionField.set(description, updatedValue);
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
                   genie.error("Error while updating user profile description", e);
                   throw authException(("Dear %s, you have tried to update the user profile description but error occurred." +
                                        "If the error persists contact us!").formatted(getCurrentUserDetails().getUsername()), HttpStatus.BAD_REQUEST);
                }
            }
        }

        appendListToExisting(profileDescriptionUpdateRequest.getUserShortTermGoals(), description.getUserShortTermGoals());
        appendListToExisting(profileDescriptionUpdateRequest.getUserLongTermGoals(), description.getUserLongTermGoals());
        appendListToExisting(profileDescriptionUpdateRequest.getUserDietaryRestrictions(), description.getUserDietaryRestrictions());
        appendListToExisting(profileDescriptionUpdateRequest.getUserMedicalConditions(), description.getUserMedicalConditions());
        appendListToExisting(profileDescriptionUpdateRequest.getUserAvailableEquipment(), description.getUserAvailableEquipment());
        appendListToExisting(profileDescriptionUpdateRequest.getUserDailySchedule(), description.getUserDailySchedule());
        appendListToExisting(profileDescriptionUpdateRequest.getUserDailyDiet(), description.getUserDailyDiet());

        userProfileDescriptionRepository.save(description);
        genie.info("User profile description updated");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your profile description has been updated successfully!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    private void appendListToExisting(List<String> updatedList, List<String> existingList) {
        if (updatedList != null && !updatedList.isEmpty()) {
            Set<String> existingSet = new HashSet<>(existingList);
            existingSet.addAll(updatedList);
            existingList.clear();
            existingList.addAll(existingSet);
        }
    }

    private ApiResponse updateProfile(UserProfile userProfile, @Valid ProfileUpdateRequest request) {
        if (request.getUserAge() != null){
            genie.info("Updating user age from {} to {}", userProfile.getUserAge(), request.getUserAge());
            userProfile.setUserAge(request.getUserAge());
        }

        if (request.getUserWeight() != null){
            genie.info("Updating user weight from {} to {}", userProfile.getUserWeight(), request.getUserWeight());
            userProfile.setUserWeight(request.getUserWeight());
        }

        if (request.getUserHeight() != null){
            genie.info("Updating user height from {} to {}", userProfile.getUserHeight(), request.getUserHeight());
            userProfile.setUserHeight(request.getUserHeight());
        }

        if (request.getUserExercisePerWeek() != null){
            genie.info("Updating user exercise per week from {} to {}", userProfile.getUserExercisePerWeek(), request.getUserExercisePerWeek());
            userProfile.setUserExercisePerWeek(request.getUserExercisePerWeek());
        }

        if (request.getUserAvgWorkoutDuration() != null){
            genie.info("Updating user average workout duration from {} to {}", userProfile.getUserAvgWorkoutDuration(), request.getUserAvgWorkoutDuration());
            userProfile.setUserAvgWorkoutDuration(request.getUserAvgWorkoutDuration());
        }

        if (request.getUserOpenToDietaryChange() != null){
            genie.info("Updating user open to dietary change from {} to {}", userProfile.getUserOpenToDietaryChange(), request.getUserOpenToDietaryChange());
            userProfile.setUserOpenToDietaryChange(request.getUserOpenToDietaryChange());
        }

        if (request.getUserSkipMeals() != null){
            genie.info("Updating user skip meals from {} to {}", userProfile.getUserSkipMeals(), request.getUserSkipMeals());
            userProfile.setUserSkipMeals(request.getUserSkipMeals());
        }

        if (request.getUserOpenToPurchase() != null){
            genie.info("Updating user open to purchase from {} to {}", userProfile.getUserOpenToPurchase(), request.getUserOpenToPurchase());
            userProfile.setUserOpenToPurchase(request.getUserOpenToPurchase());
        }

        userProfileRepository.save(userProfile);
        genie.info("User profile updated");

        return ApiResponse.builder()
                .path(getCurrentRequest())
                .message("Your profile has been updated successfully!")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }
}
