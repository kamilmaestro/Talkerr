package com.kamilmarnik.talkerr.logic;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public  final class Constants {
  private Constants() {
    throw new AssertionError("This class can not be instantiated!");
  }

  public static final String TALKERR_MAIL = "talkerrforum@gmail.com";
  public static final String TALKERR_MAIL_PASSWORD = "";

}
