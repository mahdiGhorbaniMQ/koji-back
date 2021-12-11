package com.example.kojiback.repositories;

import com.example.kojiback.models.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition,Long> {
}
