package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "JWT_TOKEN")
@AttributeOverride(name = "id", column = @Column(name = "JWT_TOKEN_ID"))
public class JwtToken extends BaseEntity {

    @Column(name = "TOKEN_VALUE", nullable = false, length = 1000)
    private String tokenValue;

    @Column(name = "IS_EXPIRED", nullable = false)
    private boolean expired;

    @Column(name = "IS_REVOKED", nullable = false)
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
