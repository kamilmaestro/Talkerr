package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepository.findUserByLogin(userName)
        .orElseThrow(() -> new UsernameNotFoundException("Can not find user with such username: " + userName));

    return UserPrincipal.builder()
        .userId(user.getUserId())
        .userName(user.getLogin())
        .password(user.getPassword())
        .role(user.getStatus())
        .build();
  }
}
