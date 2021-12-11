package com.example.kojiback.controllers;

import com.example.kojiback.models.Group;
import com.example.kojiback.models.User;
import com.example.kojiback.models.relations.GroupEvent;
import com.example.kojiback.models.relations.UserGroup;
import com.example.kojiback.payload.request.CreateGroupRequest;
import com.example.kojiback.payload.response.GroupDetailsResponse;
import com.example.kojiback.payload.response.GroupProfileResponse;
import com.example.kojiback.payload.response.MessageResponse;
import com.example.kojiback.repositories.GroupRepository;
import com.example.kojiback.repositories.UserRepository;
import com.example.kojiback.repositories.relations.GroupEventRepository;
import com.example.kojiback.repositories.relations.UserGroupRepository;
import com.example.kojiback.security.jwt.AuthTokenFilter;
import com.example.kojiback.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthTokenFilter authTokenFilter;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    GroupEventRepository groupEventRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(Principal principal, @RequestBody CreateGroupRequest newGroup) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Group group = new Group(
                newGroup.getName(),
                newGroup.getBio(),
                user.getUsername());
        groupRepository.save(group);
        UserGroup userGroupRelation = new UserGroup(username,group.getId());
        userGroupRepository.save(userGroupRelation);
        return ResponseEntity.ok(group);
    }


    @GetMapping("/details")
    public ResponseEntity<?> getGroupDetails(Principal principal,@RequestParam Long groupId) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Group group = groupRepository.getById(groupId);

        Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByGroupId(group.getId());
        Set<String> groupUsers = new HashSet<>();
        userGroupRelations.forEach(item->{
            groupUsers.add(item.getUsername());
        });

        Collection<GroupEvent> groupEventRelations = groupEventRepository.findAllByGroupId(group.getId());
        Set<Long> groupEvents = new HashSet<>();
        groupEventRelations.forEach(item->{
            groupEvents.add(item.getEvent_id());
        });

        if(groupUsers.contains(username)){
            return ResponseEntity.ok(new GroupDetailsResponse(
                    group.getId(),
                    group.getName(),
                    group.getBio(),
                    group.getOwner(),
                    groupUsers,
                    groupEvents));
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the group!"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getGroupProfile(@RequestParam Long groupId) {
       Group group = groupRepository.getById(groupId);

       Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByGroupId(group.getId());
       Set<String> groupUsers = new HashSet<>();
       userGroupRelations.forEach(item->{
           groupUsers.add(item.getUsername());
       });

       return ResponseEntity.ok(new GroupProfileResponse(
               group.getName(),
               group.getBio(),
               group.getOwner(),
               groupUsers));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteGroup(Principal principal,@RequestParam Long groupId) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Group group = groupRepository.getById(groupId);

        if(group.getOwner().equals(user.getUsername())){
            Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByGroupId(group.getId());
            Set<String> groupUsers = new HashSet<>();
            userGroupRelations.forEach(item->{
                userGroupRepository.delete(item);
            });
            groupRepository.delete(group);
            return ResponseEntity.ok("group successfully deleted");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the group!"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGroupProfile(Principal principal,@RequestParam Long groupId,@RequestBody CreateGroupRequest newGroup) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Group group = groupRepository.getById(groupId);

        Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByGroupId(group.getId());
        Set<String> groupUsers = new HashSet<>();
        userGroupRelations.forEach(item->{
            groupUsers.add(item.getUsername());
        });
        if(groupUsers.contains(username) && group.getOwner().equals(user.getUsername())){
            group.setName(newGroup.getName());
            group.setBio(newGroup.getBio());
            groupRepository.save(group);
            return ResponseEntity.ok("group successfully updated");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: user have not access to the group!"));
    }

    @GetMapping("/addUser")
    public ResponseEntity<?> addUserToGroup(Principal principal,@RequestParam Long groupId, @RequestParam String username) {
        User clientUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + principal.getName()));

        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Group group = groupRepository.getById(groupId);

        Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByGroupId(group.getId());
        Set<String> groupUsers = new HashSet<>();
        userGroupRelations.forEach(item->{
            groupUsers.add(item.getUsername());
        });

        if((group.getOwner().equals(clientUser.getUsername()) || user.getUsername().equals(clientUser.getUsername())) && !groupUsers.contains(username)){
            UserGroup userGroupRelation = new UserGroup(user.getUsername(),group.getId());
            userGroupRepository.save(userGroupRelation);
            return ResponseEntity.ok("user successfully removed");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: access denied!"));
    }
    @GetMapping("/removeUser")
    public ResponseEntity<?> removeUserFromGroup(Principal principal,@RequestParam Long groupId, @RequestParam String username) {

        User clientUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + principal.getName()));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Group group = groupRepository.getById(groupId);

        Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByGroupId(group.getId());
        Set<String> groupUsers = new HashSet<>();
        userGroupRelations.forEach(item->{
            groupUsers.add(item.getUsername());
        });
        if((group.getOwner().equals(clientUser.getUsername()) || clientUser.getUsername().equals(user.getUsername()))&& groupUsers.contains(username)){
            UserGroup userGroupRelation = userGroupRepository.getByUsernameAndGroAndGroupId(user.getUsername(),group.getId());
            userGroupRepository.delete(userGroupRelation);
            return ResponseEntity.ok("user successfully removed");
        }
        else  return ResponseEntity.badRequest().body(new MessageResponse("Error: access denied!"));
    }
}
