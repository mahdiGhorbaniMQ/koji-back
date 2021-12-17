package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.EventCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventConditionRepository extends JpaRepository<EventCondition,Long> {
    @Override
    EventCondition getById(Long aLong);

    @Query("SELECT u FROM EventCondition u WHERE u.event_id = ?1")
    Collection<EventCondition> findAllByEventId(Long eventId);

    @Query("SELECT u FROM EventCondition u WHERE u.condition_id = ?1")
    Collection<EventCondition> findAllByConditionId(Long condition_id);

}
