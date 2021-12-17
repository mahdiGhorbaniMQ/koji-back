package com.example.kojiback.controllers;

import com.example.kojiback.models.Condition;
import com.example.kojiback.models.Event;
import com.example.kojiback.models.Group;
import com.example.kojiback.models.lists.Date;
import com.example.kojiback.models.relations.*;
import com.example.kojiback.models.lists.Place;
import com.example.kojiback.payload.request.EventRequest;
import com.example.kojiback.payload.request.SetFinalCondition;
import com.example.kojiback.payload.response.EventDetailsResponse;
import com.example.kojiback.payload.response.EventVotesResponse;
import com.example.kojiback.payload.response.MessageResponse;
import com.example.kojiback.payload.response.VoteResponse;
import com.example.kojiback.repositories.ConditionRepository;
import com.example.kojiback.repositories.EventRepository;
import com.example.kojiback.repositories.GroupRepository;
import com.example.kojiback.repositories.UserRepository;
import com.example.kojiback.repositories.lists.AgreeRepository;
import com.example.kojiback.repositories.lists.DateRepository;
import com.example.kojiback.repositories.lists.DisagreeRepository;
import com.example.kojiback.repositories.relations.*;
import com.example.kojiback.repositories.lists.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    GroupEventRepository groupEventRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    EventDateRepository eventDateRepository;

    @Autowired
    EventPlaceRepository eventPlaceRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    DateRepository dateRepository;

    @Autowired
    EventConditionRepository eventConditionRepository;

    @Autowired
    ConditionRepository conditionRepository;

    @Autowired
    EventAgreeRepository eventAgreeRepository;

    @Autowired
    EventDisagreeRepository eventDisagreeRepository;

    @Autowired
    AgreeRepository agreeRepository;

    @Autowired
    DisagreeRepository disagreeRepository;

    @PostMapping("/create")
    private ResponseEntity<?> createEvent(Principal principal, @RequestBody EventRequest createEventRequest){
        String username = principal.getName();
        Event event = new Event(
                createEventRequest.getTitle(),
                createEventRequest.getDescriptions(),
                username
        );
        eventRepository.save(event);

        GroupEvent groupEventRelation = new GroupEvent(createEventRequest.getGroupId(), event.getId());
        groupEventRepository.save(groupEventRelation);

        createEventRequest.getPlaces().forEach(item -> {
            if (!placeRepository.existsById(item)) {
                Place place = new Place(item);
                placeRepository.save(place);
            }
            EventPlace eventPlaceRelation = new EventPlace(event.getId(),item);
            eventPlaceRepository.save(eventPlaceRelation);
        });
        createEventRequest.getDates().forEach(item -> {
            if (!dateRepository.existsById(item)) {
                Date date = new Date(item);
                dateRepository.save(date);
            }
            EventDate eventDateRelation = new EventDate(event.getId(),item);
            eventDateRepository.save(eventDateRelation);
        });
        EventDetailsResponse eventDetailsResponse = new EventDetailsResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getOwner(),
                event.getFinalDate(),
                event.getFinalPlace(),
                createEventRequest.getDates(),
                createEventRequest.getPlaces()
        );
        return ResponseEntity.ok(eventDetailsResponse);
    }

    @GetMapping("/addEventToGroup")
    private ResponseEntity<?> addEventToGroup(Principal principal,@RequestParam Long eventId, @RequestParam Long groupId){
        String username = principal.getName();
        Event event = eventRepository.getById(eventId);
        if(event.getOwner().equals(username) && groupRepository.getById(groupId) != null && groupEventRepository.getByGroupIdAndEventId(groupId,eventId) == null){
          GroupEvent groupEventRelation = new GroupEvent(groupId,eventId);
          groupEventRepository.save(groupEventRelation);
          return ResponseEntity.ok("event successfully added to group!");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the event!"));
    }

    @GetMapping("/removeEventFromGroup")
    private ResponseEntity<?> removeEventFromGroup(Principal principal,@RequestParam Long eventId, @RequestParam Long groupId){
        String username = principal.getName();
        Event event = eventRepository.getById(eventId);
        Group group = groupRepository.getById(groupId);
        if(event.getOwner().equals(username) || (group.getOwner().equals(username))){
            GroupEvent groupEventRelation = groupEventRepository.getByGroupIdAndEventId(groupId,eventId);;
            groupEventRepository.delete(groupEventRelation);
            return ResponseEntity.ok("event successfully removed from group!");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the event!"));
    }

    @PutMapping("update")
    private ResponseEntity<?> updateEvent(Principal principal,@RequestParam Long eventId,@RequestBody EventRequest createEventRequest){
        String username = principal.getName();
        Event event = eventRepository.getById(eventId);
        if (event.getOwner().equals(username)){
            event.setTitle(createEventRequest.getTitle());
            event.setDescription(createEventRequest.getDescriptions());
            eventRepository.save(event);

            Collection<EventPlace> eventPlaceRelations = eventPlaceRepository.findAllByEventId(event.getId());
            eventPlaceRelations.forEach(item -> {
                eventPlaceRepository.delete(item);
            });
            createEventRequest.getPlaces().forEach(item -> {
                if (!placeRepository.existsById(item)) {
                    Place place = new Place(item);
                    placeRepository.save(place);
                }
                EventPlace eventPlaceRelation = new EventPlace(event.getId(),item);
                eventPlaceRepository.save(eventPlaceRelation);
            });

            Collection<EventDate> eventDateRelations = eventDateRepository.findAllByEventId(event.getId());
            eventDateRelations.forEach(item -> {
                eventDateRepository.delete(item);
            });
            createEventRequest.getDates().forEach(item -> {
                if (!dateRepository.existsById(item)) {
                    Date date = new Date(item);
                    dateRepository.save(date);
                }
                EventDate eventDateRelation = new EventDate(event.getId(),item);
                eventDateRepository.save(eventDateRelation);
            });
            EventDetailsResponse eventDetailsResponse = new EventDetailsResponse(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getOwner(),
                    event.getFinalDate(),
                    event.getFinalPlace(),
                    createEventRequest.getDates(),
                    createEventRequest.getPlaces()
            );
            return ResponseEntity.ok(eventDetailsResponse);
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the event!"));
    }

    @PutMapping("setFinalCondition")
    private ResponseEntity<?> setFinalCondition(Principal principal,@RequestParam Long eventId,@RequestBody SetFinalCondition finalCondition){
//
        String username = principal.getName();
        Event event = eventRepository.getById(eventId);
        if (event.getOwner().equals(username)){
            event.setFinalPlace(finalCondition.getFinalPlace());
            event.setFinalDate(finalCondition.getFinalDate());
            eventRepository.save(event);
            Set<String> dates = new HashSet<>();
            Set<String> places = new HashSet<>();
            Collection<EventPlace> eventPlaces = eventPlaceRepository.findAllByEventId(event.getId());
            eventPlaces.forEach(item -> {
                places.add(item.getPlace());
            });
            Collection<EventDate> eventDates = eventDateRepository.findAllByEventId(event.getId());
            eventDates.forEach(item -> {
                dates.add(item.getDate());
            });
            EventDetailsResponse eventDetailsResponse = new EventDetailsResponse(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getOwner(),
                    finalCondition.getFinalDate(),
                    finalCondition.getFinalPlace(),
                    dates,
                    places
            );
            return ResponseEntity.ok(eventDetailsResponse);
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the event!"));
    }

    @GetMapping("details")
    private ResponseEntity<?> getDetails(Principal principal,@RequestParam Long eventId){
        String username = principal.getName();
        Event event = eventRepository.getById(eventId);
        Set<String> dates = new HashSet<>();
        Set<String> places = new HashSet<>();
        Collection<EventPlace> eventPlaces = eventPlaceRepository.findAllByEventId(event.getId());
        eventPlaces.forEach(item -> {
            places.add(item.getPlace());
        });
        Collection<EventDate> eventDates = eventDateRepository.findAllByEventId(event.getId());
        eventDates.forEach(item -> {
            dates.add(item.getDate());
        });
        EventDetailsResponse eventDetailsResponse = new EventDetailsResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getOwner(),
                event.getFinalDate(),
                event.getFinalPlace(),
                dates,
                places
        );
        return ResponseEntity.ok(eventDetailsResponse);
    }
    @DeleteMapping("delete")
    private ResponseEntity<?> deleteEvent(Principal principal,@RequestParam Long eventId){
        String username = principal.getName();
        Event event = eventRepository.getById(eventId);
        if (event.getOwner().equals(username)){
            Collection<EventPlace> eventPlaceRelations = eventPlaceRepository.findAllByEventId(event.getId());
            eventPlaceRelations.forEach(item -> {
                eventPlaceRepository.delete(item);
            });
            Collection<EventDate> eventDateRelations = eventDateRepository.findAllByEventId(event.getId());
            eventDateRelations.forEach(item -> {
                eventDateRepository.delete(item);
            });
            Collection<EventCondition> eventConditionRelations = eventConditionRepository.findAllByEventId(event.getId());
            eventConditionRelations.forEach(item -> {
                Condition condition = conditionRepository.getById(item.getCondition_id());
                conditionRepository.delete(condition);
                eventConditionRepository.delete(item);
            });
            Collection<GroupEvent> groupEventRelations = groupEventRepository.findAllByEventId(event.getId());
            groupEventRelations.forEach(item -> {
                groupEventRepository.delete(item);
            });
            eventRepository.delete(event);
            return ResponseEntity.ok("event successfully deleted");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the event!"));
    }
    @GetMapping("votes")
    public ResponseEntity<?> getVotes(Principal principal,@RequestParam Long eventId){
        String username = principal.getName();
        if (eventRepository.getById(eventId) != null){
            Set<VoteResponse> agrees = new HashSet<>();
            Set<VoteResponse> disagrees = new HashSet<>();
            Collection<EventAgree> eventAgreeRelation = eventAgreeRepository.findAllByEventId(eventId);
            eventAgreeRelation.forEach(item -> {
                VoteResponse voteResponse = new VoteResponse(
                            item.getUsername(),
                            userRepository.findByUsername(item.getUsername()).orElseThrow().getFirstName(),
                            userRepository.findByUsername(item.getUsername()).orElseThrow().getLastName());

                agrees.add(voteResponse);
            });
            Collection<EventDisagree> eventDisagreeRelation = eventDisagreeRepository.findAllByEventId(eventId);
            eventDisagreeRelation.forEach(item -> {
                VoteResponse voteResponse = new VoteResponse(
                        item.getUsername(),
                        userRepository.findByUsername(item.getUsername()).orElseThrow().getFirstName(),
                        userRepository.findByUsername(item.getUsername()).orElseThrow().getLastName());

                disagrees.add(voteResponse);
            });
            EventVotesResponse votes = new EventVotesResponse(agrees,disagrees);
            return ResponseEntity.ok(votes);
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("event not found"));
    }
    @GetMapping("/setUserAgreement")
    public ResponseEntity<?> setUserAgreement(Principal principal,@RequestParam Long eventId) {
        String username = principal.getName();
        EventDisagree eventDisagreeRelation = eventDisagreeRepository.findByEventIdAndUsername(eventId,username);
        if (eventDisagreeRelation != null) {
            eventDisagreeRepository.delete(eventDisagreeRelation);
        }
        if(eventAgreeRepository.findByEventIdAndUsername(eventId,username) == null){
            EventAgree eventAgreeRelation = new EventAgree(eventId,username);
            eventAgreeRepository.save(eventAgreeRelation);
            return ResponseEntity.ok(new MessageResponse("user vote successfully added"));
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("bad request!"));
    }
    @GetMapping("/setUserDisagreement")
    public ResponseEntity<?> setUserDisagreement(Principal principal,@RequestParam Long eventId) {
        String username = principal.getName();
        EventAgree eventAgreeRelation = eventAgreeRepository.findByEventIdAndUsername(eventId,username);
        if (eventAgreeRelation != null) {
            eventAgreeRepository.delete(eventAgreeRelation);
        }
        if(eventDisagreeRepository.findByEventIdAndUsername(eventId,username)==null){
            EventDisagree eventDisagreeRelation = new EventDisagree(eventId,username);
            eventDisagreeRepository.save(eventDisagreeRelation);
            return ResponseEntity.ok(new MessageResponse("user vote successfully added"));
        }
        else return ResponseEntity.badRequest().body("bad request!");
    }
    @GetMapping("/retractVote")
    public ResponseEntity<?> retractUserVote(Principal principal,@RequestParam Long eventId) {
        String username = principal.getName();
        EventAgree eventAgreeRelation = eventAgreeRepository.findByEventIdAndUsername(eventId,username);
	    EventDisagree eventDisagreeRelation = eventDisagreeRepository.findByEventIdAndUsername(eventId,username);
        if (eventDisagreeRelation != null) {
            eventDisagreeRepository.delete(eventDisagreeRelation);
	    return ResponseEntity.ok(new MessageResponse("user vote successfully retracted"));
        }
	    else if (eventAgreeRelation != null) {
            eventAgreeRepository.delete(eventAgreeRelation);
            return ResponseEntity.ok(new MessageResponse("user vote successfully retracted"));
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("bad request!"));
    }
}
