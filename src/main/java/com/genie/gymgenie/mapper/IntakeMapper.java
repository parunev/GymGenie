package com.genie.gymgenie.mapper;

import com.genie.gymgenie.models.CalorieIntake;
import com.genie.gymgenie.models.payload.diet.CalorieIntakeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IntakeMapper {

    CalorieIntakeResponse mapToCalorieIntake(CalorieIntake intake);
}
