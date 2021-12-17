package com.example.kojiback.controllers;

import com.example.kojiback.models.Condition;
import com.example.kojiback.models.relations.*;
import com.example.kojiback.payload.request.ConditionRequest;
import com.example.kojiback.payload.response.ConditionResponse;
import com.example.kojiback.payload.response.MessageResponse;
import com.example.kojiback.repositories.ConditionRepository;
import com.example.kojiback.repositories.UserRepository;
import com.example.kojiback.repositories.relations.ConditionDateRepository;
import com.example.kojiback.repositories.relations.ConditionPlaceRepository;
import com.example.kojiback.repositories.relations.EventConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/condition")
public class ConditionController {

    @Autowired
    ConditionRepository conditionRepository;
    @Autowired
    EventConditionRepository eventConditionRepository;
    @Autowired
    ConditionDateRepository conditionDateRepository;
    @Autowired
    ConditionPlaceRepository conditionPlaceRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("set")
    private ResponseEntity<?> setUserCondition(Principal principal, @RequestParam Long eventId, @RequestBody ConditionRequest conditionRequest){
        String username = principal.getName();

        if(conditionRepository.findByEventIdAndUsername(eventId,username)==null){
            Condition condition = new Condition(username);
            condition.setEventId(eventId);
	        condition.setState(conditionRequest.getState());

            conditionRepository.save(condition);

            EventCondition eventConditionRelation = new EventCondition(eventId,condition.getId());
            eventConditionRepository.save(eventConditionRelation);

            conditionRequest.getDates().forEach(item -> {
                ConditionDate conditionDateRelation = new ConditionDate(condition.getId(),item);
                conditionDateRepository.save(conditionDateRelation);
            });

            conditionRequest.getPlaces().forEach(item -> {
                ConditionPlace conditionPlaceRelation = new ConditionPlace(condition.getId(),item);
                conditionPlaceRepository.save(conditionPlaceRelation);
            });
            return ResponseEntity.ok(new MessageResponse("user condition successfully added"));
        }
        else {
            Condition condition = conditionRepository.findByEventIdAndUsername(eventId,username);

            condition.setState(conditionRequest.getState());
            conditionRepository.save(condition);

            Collection<ConditionDate> conditionDateRelations = conditionDateRepository.findAllByConditionId(condition.getId());
            conditionDateRelations.forEach(item->{
                conditionDateRepository.delete(item);
            });
            conditionRequest.getDates().forEach(item -> {
                ConditionDate conditionDateRelation = new ConditionDate(condition.getId(),item);
                conditionDateRepository.save(conditionDateRelation);
            });

            Collection<ConditionPlace> conditionPlaceRelations = conditionPlaceRepository.findAllByConditionId(condition.getId());
            conditionPlaceRelations.forEach(item->{
                conditionPlaceRepository.delete(item);
            });
            conditionRequest.getPlaces().forEach(item -> {
                ConditionPlace conditionPlaceRelation = new ConditionPlace(condition.getId(),item);
                conditionPlaceRepository.save(conditionPlaceRelation);
            });
            return ResponseEntity.ok(new MessageResponse("user condition successfully changed"));
	    }
    }
    @GetMapping("getOne")
    private ResponseEntity<?> getUserCondition(@RequestParam Long eventId,@RequestParam String username) {

        Condition condition = conditionRepository.findByEventIdAndUsername(eventId,username);

        if(condition!=null){
            Set<String> dates = new HashSet<>();
            Set<String> places = new HashSet<>();

            Collection<ConditionDate> conditionDateRelation = conditionDateRepository.findAllByConditionId(condition.getId());
            if(conditionDateRelation.size()>0){
                conditionDateRelation.forEach(item -> {
                    dates.add(item.getDate());
                });
            }

            Collection<ConditionPlace> conditionPlaceRelation = conditionPlaceRepository.findAllByConditionId(condition.getId());
            if(conditionPlaceRelation.size()>0) {
                conditionPlaceRelation.forEach(item -> {
                    places.add(item.getPlace());
                });
            } 
            ConditionResponse conditionResponse = new ConditionResponse(userRepository.findByUsername(username).orElseThrow().getFirstName(),userRepository.findByUsername(username).orElseThrow().getLastName(),username,dates,places,condition.getState());

            return ResponseEntity.ok(conditionResponse);
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not condition"));
    }
    @GetMapping("getAll")
    private ResponseEntity<?> getEventCondition(@RequestParam Long eventId) {

        Collection<Condition> conditions = conditionRepository.findByEventId(eventId);

        Set<ConditionResponse> response = new HashSet<>();

        conditions.forEach(condition -> {
            Set<String> dates = new HashSet<>();
            Set<String> places = new HashSet<>();

            Collection<ConditionDate> conditionDateRelation = conditionDateRepository.findAllByConditionId(condition.getId());
            conditionDateRelation.forEach(item -> {
                dates.add(item.getDate());
            });

            Collection<ConditionPlace> conditionPlaceRelation = conditionPlaceRepository.findAllByConditionId(condition.getId());
            conditionPlaceRelation.forEach(item -> {
                places.add(item.getPlace());
            });
            ConditionResponse conditionResponse = new ConditionResponse(userRepository.findByUsername(condition.getUsername()).orElseThrow().getFirstName(),userRepository.findByUsername(condition.getUsername()).orElseThrow().getLastName(),condition.getUsername(),dates,places,condition.getState());
            response.add(conditionResponse);
        });
        return ResponseEntity.ok(response);
    }
}
