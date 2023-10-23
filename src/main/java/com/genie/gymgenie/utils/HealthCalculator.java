package com.genie.gymgenie.utils;

import com.genie.gymgenie.models.enums.user.ActivityLevel;
import com.genie.gymgenie.models.enums.user.BodyFat;
import com.genie.gymgenie.models.enums.user.Gender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HealthCalculator {

    public static BodyFat calcBF(Integer height, Integer weight, Integer age, Gender gender) {
        double bmi = calcBMI(height, weight);
        double bodyFatPercentage;
        double lowerBound;
        double upperBound;

        if (gender == Gender.MALE) {
            bodyFatPercentage = 1.2 * bmi + 0.23 * age - 16.2;
            lowerBound = 6;
            upperBound = 14;
        } else {
            bodyFatPercentage = 1.2 * bmi + 0.23 * age - 5.4;
            lowerBound = 14;
            upperBound = 21;
        }

        if (bodyFatPercentage < lowerBound) {
            return BodyFat.ESSENTIAL;
        } else if (bodyFatPercentage < upperBound) {
            return BodyFat.ATHLETES;
        }

        if (bodyFatPercentage < 18) {
            return BodyFat.FITNESS;
        } else if (bodyFatPercentage < 25) {
            return BodyFat.ACCEPTABLE;
        } else {
            return BodyFat.OBESITY;
        }
    }

    // Mifflin-St Jeor equation
    public static Double calcTDEE(Integer weight, Integer height, Integer age, Gender gender, ActivityLevel activityLevel) {
        double bmr;
        if (gender == Gender.MALE) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        return switch (activityLevel) {
            case SEDENTARY -> bmr * 1.2;
            case LIGHTLY_ACTIVE -> bmr * 1.375;
            case MODERATELY_ACTIVE -> bmr * 1.55;
            case VERY_ACTIVE -> bmr * 1.725;
            case EXTRA_ACTIVE -> bmr * 1.9;
        };
    }

    public static Double calcBMI(Integer height, Integer weight) {
        double mHeight = height / 100.0;
        return weight / (mHeight * mHeight);
    }
}
