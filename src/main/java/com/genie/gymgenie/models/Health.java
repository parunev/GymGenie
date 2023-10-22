package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.user.BodyFat;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GENIE_HEALTH")
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "HEALTH_ID"))
public class Health extends BaseEntity {

    @Column(name = "BODY_MASS_INDEX", nullable = false)
    private Double bodyMassIndex;

    @Column(name = "TOTAL_DAILY_ENERGY_EXPENDITURE", nullable = false)
    private Double totalDailyEnergyExpenditure;

    @Enumerated(EnumType.STRING)
    @Column(name = "AVG_BODY_FAT", nullable = false)
    private BodyFat avgBodyFat;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
