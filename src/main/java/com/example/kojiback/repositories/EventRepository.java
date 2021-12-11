package com.example.kojiback.repositories;

import com.example.kojiback.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
