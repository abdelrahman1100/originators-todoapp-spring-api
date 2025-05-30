package com.masteryhub.todoapp.security;

import com.masteryhub.todoapp.models.userModel.UserEntity;
import com.masteryhub.todoapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("email not found"));
    return UserDetailsImpl.build(user);
  }
}
