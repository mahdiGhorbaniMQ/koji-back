package com.example.kojiback.controllers;

import com.example.kojiback.models.Message;
import com.example.kojiback.models.relations.EventMessage;
import com.example.kojiback.repositories.MessageRepository;
import com.example.kojiback.repositories.relations.EventMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class WebsocketController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    EventMessageRepository eventMessageRepository;


    @MessageMapping("/{id}/messages")
    @SendTo("/event/{id}/messages")
    public Message sendMessage(Message message) throws Exception {
        Message newMessage = new Message(
                message.getWriter(),
                message.getContent(),
                new Date(),
                message.getEventId()
        );
        messageRepository.save(newMessage);
        EventMessage eventMessageRelation = new EventMessage(newMessage.getEventId(),newMessage.getId());
        eventMessageRepository.save(eventMessageRelation);
        return newMessage;
    }
}
