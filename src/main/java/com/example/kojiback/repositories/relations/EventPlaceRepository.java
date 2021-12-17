package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.EventPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventPlaceRepository extends JpaRepository<EventPlace,Long> {
    @Override
    EventPlace getById(Long aLong);

    @Query("SELECT u FROM EventPlace u WHERE u.place = ?1")
    Collection<EventPlace> findAllByPlace(String place);

    @Query("SELECT u FROM EventPlace u WHERE u.event_id = ?1")
    Collection<EventPlace> findAllByEventId(Long event_id);
}
