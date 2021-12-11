package com.example.kojiback.repositories.lists;

import com.example.kojiback.models.lists.ConditionDate;
import com.example.kojiback.models.lists.ConditionPlace;
import com.example.kojiback.models.lists.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ConditionDateRepository extends JpaRepository<ConditionDate,Long> {
    @Override
    ConditionDate getById(Long aLong);

    @Query("SELECT u FROM ConditionDate u WHERE u.date = ?1")
    Collection<ConditionDate> findAllByDate(String date);

    @Query("SELECT u FROM ConditionDate u WHERE u.condition_id = ?1")
    Collection<ConditionDate> findAllByConditionId(Long condition_id);
}
