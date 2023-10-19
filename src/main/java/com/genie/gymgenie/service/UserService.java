package com.genie.gymgenie.service;

import com.genie.gymgenie.mapper.UserMapper;
import com.genie.gymgenie.models.*;
import com.genie.gymgenie.models.payload.user.UserProfileRequest;
import com.genie.gymgenie.repositories.*;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.payload.ApiResponse;
import com.genie.gymgenie.utils.email.EmailSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

import static com.genie.gymgenie.security.CurrentUser.getCurrentUserDetails;
import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;
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
}
