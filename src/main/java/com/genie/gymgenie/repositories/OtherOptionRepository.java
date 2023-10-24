package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.diet.main.OtherOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherOptionRepository extends JpaRepository<OtherOption, Long> {
}
