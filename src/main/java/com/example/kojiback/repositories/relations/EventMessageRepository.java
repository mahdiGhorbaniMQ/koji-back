package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.EventMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EventMessageRepository extends JpaRepository<EventMessage,Long> {
    @Override
    EventMessage getById(Long aLong);

    @Query("SELECT u FROM EventMessage u WHERE u.event_id = ?1")
    Collection<EventMessage> findAllByEventId(Long eventId);

    @Query("SELECT u FROM EventMessage u WHERE u.message_id = ?1")
    EventMessage findAllByMessageId(Long message_id);
}
