package com.example.kojiback.repositories.lists;

import com.example.kojiback.models.lists.ConditionPlace;
import com.example.kojiback.models.lists.Place;
import com.example.kojiback.models.relations.EventCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface PlaceRepository extends JpaRepository<Place,String> {
}
