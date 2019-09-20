package com.kamilmarnik.talkerr.logic;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class LoggedUserGetter {

  private LoggedUserGetter() {
    throw new AssertionError("This class can not be instantiated!");
  }

  public static UserDetails getLoggedUserDetails() {
    SecurityContext context = SecurityContextHolder.getContext();
    UserDetails loggedUserDetails = null;
    if(context != null) {
      Authentication auth = context.getAuthentication();
      if(auth != null) {
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
          loggedUserDetails = (UserDetails) principal;
        } else {
          throw new RuntimeException("Can not get User Details");
        }
      }
    }
    return loggedUserDetails;
  }

  public static String getLoggedUserName() {
    return getLoggedUserDetails().getUsername();
  }
}
