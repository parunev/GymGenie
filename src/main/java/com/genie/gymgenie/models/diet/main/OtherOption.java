package com.genie.gymgenie.models.diet.main;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_OTHER_OPTION")
@AttributeOverride(name = "id", column = @Column(name = "OTHER_OPTION_ID"))
public class OtherOption extends BaseEntity{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MAINTAIN_WEIGHT_ID")
    private WeightOption maintainWeight;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MILD_WEIGHT_LOSS_ID")
    private WeightOption mildWeightLoss;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "WEIGHT_LOSS_ID")
    private WeightOption weightLoss;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXTREME_WEIGHT_LOSS_ID")
    private WeightOption extremeWeightLoss;

}
