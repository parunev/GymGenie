package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.Authority;
import com.genie.gymgenie.models.enums.user.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GENIE_USER")
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
public class User extends BaseEntity implements UserDetails {

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = false)
    private Gender gender;

    @ElementCollection
    @CollectionTable(name = "USER_GOALS", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "GOAL", nullable = false)
    private List<Goal> goals;

    @ElementCollection
    @CollectionTable(name = "USER_MOTIVATIONS", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "MOTIVATION", nullable = false)
    private List<Motivation> motivations;

    @ElementCollection
    @CollectionTable(name = "USER_WORKOUT_AREAS", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "WORKOUT_AREAS", nullable = false)
    private List<WorkoutAreas> workoutAreas;

    @Enumerated(EnumType.STRING)
    @Column(name = "FITNESS_LEVEL", nullable = false)
    private FitnessLevel fitnessLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITY_LEVEL", nullable = false)
    private ActivityLevel activityLevel;

    @Column(name = "HEIGHT", nullable = false)
    private Integer height;

    @Column(name = "WEIGHT", nullable = false)
    private Integer weight;

    @Column(name = "TARGET_WEIGHT", nullable = false)
    private Integer targetWeight;

    @ElementCollection
    @CollectionTable(name = "USER_HEALTH_ISSUES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "HEALTH_ISSUES", nullable = false)
    private List<HealthIssues> healthIssues;

    @ElementCollection
    @CollectionTable(name = "USER_EQUIPMENT", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "EQUIPMENT", nullable = false)
    private List<Equipment> equipment;

    @Enumerated(EnumType.STRING)
    @Column(name = "WORKOUT_FREQUENCY", nullable = false)
    private WorkoutFrequency workoutFrequency;

    @ElementCollection
    @CollectionTable(name = "USER_WORKOUT_DAYS", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "WORKOUT_DAYS", nullable = false)
    private List<WorkoutDays> workoutDays;

    @Column(name = "USERNAME", length = 100, nullable = false)
    private String username;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "GENERATION_CREDITS", nullable = false)
    private Integer generationCredits;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTHORITY", nullable = false)
    private Authority authority;

    @Column(name = "IS_ENABLED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authority.getAuthorityName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
