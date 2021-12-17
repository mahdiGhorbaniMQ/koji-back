package com.example.kojiback.repositories;

import com.example.kojiback.models.Condition;
import com.example.kojiback.models.relations.EventCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ConditionRepository extends JpaRepository<Condition,Long> {
    @Override
    Condition getById(Long aLong);

    @Query("SELECT u FROM Condition u WHERE u.eventId = ?1 and u.username = ?2")
    Condition findByEventIdAndUsername(Long event_id, String username);

    @Query("SELECT u FROM Condition u WHERE u.eventId = ?1")
    Collection<Condition> findByEventId(Long event_id);
}
