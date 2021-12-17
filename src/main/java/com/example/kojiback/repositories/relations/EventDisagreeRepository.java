package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.EventAgree;
import com.example.kojiback.models.relations.EventDisagree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventDisagreeRepository extends JpaRepository<EventDisagree,Long> {
    @Override
    EventDisagree getById(Long aLong);

    @Query("SELECT u FROM EventDisagree u WHERE u.username = ?1")
    Collection<EventDisagree> findAllByUsername(String username);

    @Query("SELECT u FROM EventDisagree u WHERE u.event_id = ?1")
    Collection<EventDisagree> findAllByEventId(Long event_id);

    @Query("SELECT u FROM EventDisagree u WHERE u.event_id = ?1 and u.username = ?2")
    EventDisagree findByEventIdAndUsername(Long event_id, String username);
}
