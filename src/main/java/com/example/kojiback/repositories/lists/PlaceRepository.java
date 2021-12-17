package com.example.kojiback.repositories.lists;

import com.example.kojiback.models.lists.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,String> {
}
