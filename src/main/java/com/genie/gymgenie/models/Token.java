package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GENIE_TOKEN")
@AttributeOverride(name = "id", column = @Column(name = "TOKEN_ID"))
public class Token extends BaseEntity {

    @Column(name = "VALUE", nullable = false)
    private String tokenValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TokenType tokenType;

    @Column(name = "CONFIRMED")
    private LocalDateTime confirmed;

    @Column(name = "EXPIRES", nullable = false)
    private LocalDateTime expires;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public static String generateValue(){
        return UUID.randomUUID().toString();
    }
}
