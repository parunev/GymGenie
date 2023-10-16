package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_INJURY")
public class Injury extends BaseEntity {

    @Column(name = "INJURY_DESCRIPTION", length = 5000)
    private String injuryDescription;

    @Column(name = "INJURY_OCCURED")
    private LocalDate injuryOccurred;

    @ManyToOne
    private UserProfile userProfile;
}
