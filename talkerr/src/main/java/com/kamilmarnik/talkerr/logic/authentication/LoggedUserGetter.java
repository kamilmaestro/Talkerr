package com.kamilmarnik.talkerr.logic.authentication;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoggedUserGetter {

  public String getLoggedUserName() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    String userName = null;
    if(ctx != null) {
      Authentication auth = ctx.getAuthentication();
      if(auth != null) {
        userName = auth.getName();
      }
    }
    if(userName == null) {
      throw new UsernameNotFoundException("Can not get currently logged in user!");
    }
    return userName;
  }
}
