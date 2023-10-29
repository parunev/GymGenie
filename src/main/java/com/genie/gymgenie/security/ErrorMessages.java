package com.genie.gymgenie.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String NO_ACCOUNT_FOUND = "No account associated with this username(%s) found.";
    public static final String NO_EMAIL_FOUND = "User with the provided email not found. Please ensure you have created an account";
    public static final String NO_WORKOUT_FOUND = "No workout with id(%s) found.";
    public static final String NO_TOKEN_FOUND = "Token not found. Please ensure you have the correct token or request a new one.";
    public static final String ALREADY_ENABLED = "Your account is already enabled.You can log in now.";
    public static final String NO_HEALTH_FOUND = "No health details associated with this username(%s) found.";
    public static final String NO_CALORIE_INTAKE_FOUND = "No calorie intake associated with this workout id(%s) found.";
    public static final String NO_WEIGHT_OPTION_FOUND = "No weight option with name(%s) found.";
    public static final String FOOD_NOUN_PROBLEM = "Something went wrong with the recipe generation. Please contact us if the error persists!";
}
