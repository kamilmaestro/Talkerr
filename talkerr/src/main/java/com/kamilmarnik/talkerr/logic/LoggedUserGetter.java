package com.kamilmarnik.talkerr.logic;

import com.kamilmarnik.talkerr.user.exception.LoggedUserNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class LoggedUserGetter {

  private LoggedUserGetter() {
    throw new AssertionError("This class can not be instantiated!");
  }

  public static String getLoggedUserName() throws LoggedUserNotFoundException {
    SecurityContext context = SecurityContextHolder.getContext();
    String userName = null;
    if(context != null) {
      Authentication auth = context.getAuthentication();
      if(auth != null) {
        userName = auth.getName();
      }
    }
    if(userName == null) {
      throw new LoggedUserNotFoundException("Can not find logged in user!");
    }
    return userName;
  }
}
