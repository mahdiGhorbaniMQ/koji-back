package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.EventAgree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventAgreeRepository extends JpaRepository<EventAgree,Long> {
    @Override
    EventAgree getById(Long aLong);

    @Query("SELECT u FROM EventAgree u WHERE u.username = ?1")
    Collection<EventAgree> findAllByUsername(String username);

    @Query("SELECT u FROM EventAgree u WHERE u.event_id = ?1")
    Collection<EventAgree> findAllByEventId(Long event_id);

    @Query("SELECT u FROM EventAgree u WHERE u.event_id = ?1 and u.username = ?2")
    EventAgree findByEventIdAndUsername(Long event_id,String username);
}
