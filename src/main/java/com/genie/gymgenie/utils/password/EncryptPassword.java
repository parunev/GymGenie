package com.genie.gymgenie.utils.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordEncryptor.class)
public @interface EncryptPassword {

    String message() default "No password found to encrypt. Please try again.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
