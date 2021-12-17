package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.EventDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventDateRepository extends JpaRepository<EventDate,Long> {
    @Override
    EventDate getById(Long aLong);

    @Query("SELECT u FROM EventDate u WHERE u.date = ?1")
    Collection<EventDate> findAllByDate(String date);

    @Query("SELECT u FROM EventDate u WHERE u.event_id = ?1")
    Collection<EventDate> findAllByEventId(Long event_id);
}
