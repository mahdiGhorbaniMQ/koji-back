package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {
    @Override
    UserGroup getById(Long aLong);

    @Query("SELECT u FROM UserGroup u WHERE u.username = ?1")
    Collection<UserGroup> findAllByUsername(String  username);

    @Query("SELECT u FROM UserGroup u WHERE u.group_id = ?1")
    Collection<UserGroup> findAllByGroupId(Long groupId);

    @Query("SELECT u FROM UserGroup u WHERE u.username = ?1 and u.group_id = ?2")
    UserGroup getByUsernameAndGroAndGroupId(String username,Long groupId);
}
