package com.genie.gymgenie.utils.password;

import com.genie.gymgenie.models.payload.user.login.ResetPasswordRequest;
import com.genie.gymgenie.models.payload.user.profile.ChangePasswordRequest;
import com.genie.gymgenie.models.payload.user.registration.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, Object> {

    @Override
    public void initialize(PasswordConfirmation constraintAnnotation) {
        // This method is intentionally empty.
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return false;
        }

        String password;
        String confirmPassword;

        if (obj instanceof RegistrationRequest yourClass) {
            password = yourClass.getPassword();
            confirmPassword = yourClass.getConfirmPassword();

            return isValidPassword(password, confirmPassword);
        } else if (obj instanceof ResetPasswordRequest yourClass){
            password = yourClass.getPassword();
            confirmPassword = yourClass.getConfirmPassword();

            return isValidPassword(password, confirmPassword);
        } else if (obj instanceof ChangePasswordRequest yourClass){
            password = yourClass.getNewPassword();
            confirmPassword = yourClass.getConfirmNewPassword();

            return isValidPassword(password, confirmPassword);
        }

        return false;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}
