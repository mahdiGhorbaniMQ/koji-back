package com.example.kojiback.controllers;

import com.example.kojiback.models.relations.Contact;
import com.example.kojiback.payload.request.ConditionRequest;
import com.example.kojiback.payload.response.MessageResponse;
import com.example.kojiback.repositories.relations.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("add")
    private ResponseEntity<?> addContact(Principal principal, @RequestParam String contactUsername) {
        String username = principal.getName();
        if(contactRepository.findByUsernameAndContactUsername(username,contactUsername) == null){
            Contact contact = new Contact(username,contactUsername);
            contactRepository.save(contact);
            return ResponseEntity.ok("contact successfully added");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user already have the contact!"));
    }

    @DeleteMapping("remove")
    private ResponseEntity<?> removeContact(Principal principal, @RequestParam String contactUsername) {
        String username = principal.getName();
        Contact contact = contactRepository.findByUsernameAndContactUsername(username,contactUsername);
        if(contact != null){
            contactRepository.delete(contact);
            return ResponseEntity.ok("contact successfully removed");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: contact not found!"));
    }

    @GetMapping("all")
    private ResponseEntity<?> getAllContact(Principal principal) {
        String username = principal.getName();
        Collection<Contact> contacts = contactRepository.findAllByUsername(username);
        Set<String> usernames = new HashSet<>();
        contacts.forEach(contact -> {
            usernames.add(contact.getContactUsername());
        });
        return ResponseEntity.ok(usernames);
    }
}
