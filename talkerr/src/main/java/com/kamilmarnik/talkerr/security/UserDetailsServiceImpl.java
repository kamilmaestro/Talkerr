package com.kamilmarnik.talkerr.security;

import com.kamilmarnik.talkerr.user.domain.User;
import com.kamilmarnik.talkerr.user.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findUserByLogin(userName);
    if(!user.isPresent()) {
      throw new UsernameNotFoundException("Can not find user with such username: " + userName);
    }
    log.error(userName);
    log.error(user.get().dto().getPassword());
    UserDetailsDto userDetailsDto = new UserDetailsDto(user.get().dto());
    log.error(userDetailsDto.getPassword());
    return userDetailsDto;
  }
}
