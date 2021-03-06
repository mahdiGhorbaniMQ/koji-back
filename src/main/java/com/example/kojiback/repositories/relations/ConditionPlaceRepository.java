package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.ConditionPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ConditionPlaceRepository extends JpaRepository<ConditionPlace,Long> {
    @Override
    ConditionPlace getById(Long aLong);

    @Query("SELECT u FROM ConditionPlace u WHERE u.place = ?1")
    Collection<ConditionPlace> findAllByPlace(String place);

    @Query("SELECT u FROM ConditionPlace u WHERE u.condition_id = ?1")
    Collection<ConditionPlace> findAllByConditionId(Long condition_id);
}
