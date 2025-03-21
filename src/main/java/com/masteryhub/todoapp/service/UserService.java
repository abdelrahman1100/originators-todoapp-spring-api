package com.masteryhub.todoapp.service;

import com.masteryhub.todoapp.dto.MessageDto;
import com.masteryhub.todoapp.dto.RequestFriendsDto;
import com.masteryhub.todoapp.dto.ResponseFriendsDto;
import com.masteryhub.todoapp.models.UserEntity;
import com.masteryhub.todoapp.repository.UserRepository;
import com.masteryhub.todoapp.security.UserDetailsImpl;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<ResponseFriendsDto> getAllFriends() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String email = userDetails.getEmail();
    Optional<UserEntity> user = userRepository.findByEmail(email);
    if (user.isEmpty()) {
      return Collections.emptyList();
    }
    List<ResponseFriendsDto> responseFriendsDtos =
        user.get().getFriends().stream()
            .map(
                username ->
                    userRepository
                        .findByUsername(username)
                        .map(friend -> new ResponseFriendsDto(friend.getUsername()))
                        .orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    return ResponseEntity.ok(responseFriendsDtos).getBody();
  }

  public ResponseEntity<MessageDto> addFriend(RequestFriendsDto friendsDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String email = userDetails.getEmail();
    Optional<UserEntity> user = userRepository.findByEmail(email);
    if (userRepository.findByUsername(friendsDto.getUsername()).isEmpty()) {
      return ResponseEntity.badRequest().body(new MessageDto("User not found"));
    }
    if (user.get().getFriends().contains(friendsDto.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageDto("Friend already exists"));
    }
    user.get().addFriend(friendsDto.getUsername());
    userRepository.save(user.get());
    return ResponseEntity.ok(new MessageDto("Friend added"));
  }

  public ResponseEntity<MessageDto> removeFriend(RequestFriendsDto friendsDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String email = userDetails.getEmail();
    Optional<UserEntity> user = userRepository.findByEmail(email);
    if (userRepository.findByUsername(friendsDto.getUsername()).isEmpty()) {
      return ResponseEntity.badRequest().body(new MessageDto("User not found"));
    }
    if (!user.get().getFriends().contains(friendsDto.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageDto("Friend doesn't exists"));
    }
    user.get().removeFriend(friendsDto.getUsername());
    userRepository.save(user.get());
    return ResponseEntity.ok(new MessageDto("Friend removed"));
  }
}
