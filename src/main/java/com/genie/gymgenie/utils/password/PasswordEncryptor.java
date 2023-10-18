package com.genie.gymgenie.utils.password;

import com.genie.gymgenie.models.payload.user.login.ResetPasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncryptor implements ConstraintValidator<EncryptPassword, Object> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initialize(EncryptPassword constraintAnnotation) {
        // This method is intentionally empty.
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (obj instanceof ResetPasswordRequest yourClass) {
            yourClass.setPassword(passwordEncoder.encode(yourClass.getPassword()));
            return true;
        }

        return false;
    }
}
