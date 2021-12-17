package com.example.kojiback.controllers;

import com.example.kojiback.models.Group;
import com.example.kojiback.models.User;
import com.example.kojiback.models.relations.UserGroup;
import com.example.kojiback.payload.response.UserDetailsResponse;
import com.example.kojiback.payload.response.UserProfileResponse;
import com.example.kojiback.repositories.GroupRepository;
import com.example.kojiback.repositories.UserRepository;
import com.example.kojiback.repositories.relations.UserGroupRepository;
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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByUsername(username);
        Set<Long> userGroups = new HashSet<>();
        userGroupRelations.forEach(item->{
            userGroups.add(item.getGroup_id());
        });

        return ResponseEntity.ok(new UserDetailsResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                userGroups));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(Principal principal,@RequestBody User newUser) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        userRepository.save(user);
        return ResponseEntity.ok("account successfully updated!");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Collection<UserGroup> userGroupRelations = userGroupRepository.findAllByUsername(username);
        userGroupRelations.forEach(item->{
            userGroupRepository.delete(item);
        });
        userRepository.delete(user);
        return ResponseEntity.ok("account successfully deleted!");
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return ResponseEntity.ok(new UserProfileResponse(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUsername()));
    }
}
