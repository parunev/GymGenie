package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USER_MEDICAL_HISTORY")
@AttributeOverride(name = "id", column = @Column(name = "MEDICAL_HISTORY_ID"))
public class MedicalHistory extends BaseEntity {

    @Column(name = "MEDICAL_HISTORY_DESCRIPTION", length = 5000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;
}
