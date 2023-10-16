package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_MEDICAL_HISTORY")
public class MedicalHistory extends BaseEntity {

    @Column(name = "MEDICAL_HISTORY_DESCRIPTION", length = 5000)
    private String description;

    @ManyToOne
    private UserProfile userProfile;
}
