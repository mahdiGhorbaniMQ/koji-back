package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.GroupEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface GroupEventRepository extends JpaRepository<GroupEvent,Long> {
    @Override
    GroupEvent getById(Long aLong);

    @Query("SELECT u FROM GroupEvent u WHERE u.group_id = ?1")
    Collection<GroupEvent> findAllByGroupId(Long groupId);

    @Query("SELECT u FROM GroupEvent u WHERE u.event_id = ?1")
    Collection<GroupEvent> findAllByEventId(Long eventId);
}
