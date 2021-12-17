package com.example.kojiback.controllers;


import com.example.kojiback.models.Message;
import com.example.kojiback.models.relations.EventMessage;
import com.example.kojiback.payload.response.EventMessageResponse;
import com.example.kojiback.repositories.MessageRepository;
import com.example.kojiback.repositories.relations.EventMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    EventMessageRepository eventMessageRepository;

    @GetMapping("/all")
    private ResponseEntity<?> getAllByEventId(@RequestParam() Long eventId) {
        Collection<EventMessage> eventMessageRelation = eventMessageRepository.findAllByEventId(eventId);
        Set<EventMessageResponse> messages = new HashSet<>();
        eventMessageRelation.forEach(item->{
            Message message = messageRepository.getById(item.getMessage_id());
            EventMessageResponse messageResponse = new EventMessageResponse(
                    message.getWriter(),
                    message.getContent(),
                    message.getDate());
            messages.add(messageResponse);
        });
        return ResponseEntity.ok(messages);
    }


}