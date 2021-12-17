package com.example.kojiback.repositories.relations;

import com.example.kojiback.models.relations.Contact;
import com.example.kojiback.models.relations.EventAgree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    @Query("SELECT u FROM Contact u WHERE u.username = ?1")
    Collection<Contact> findAllByUsername(String username);

    @Query("SELECT u FROM Contact u WHERE u.username = ?1 and u.contactUsername = ?2")
    Contact findByUsernameAndContactUsername(String username,String contactUsername);
}
