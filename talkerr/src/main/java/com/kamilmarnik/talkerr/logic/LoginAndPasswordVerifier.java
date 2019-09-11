package com.kamilmarnik.talkerr.logic;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class LoginAndPasswordVerifier {

  private LoginAndPasswordVerifier() {
    throw new AssertionError("This class can not be instantiated!");
  }

  public static void verifyRegisteredLogAndPass(String login, String password) throws InvalidLoginException, InvalidPasswordException {
    if(!isCorrectRegisteredLogin(login)) {
      throw new InvalidLoginException("Incorrect registered login!");
    }

    if(!isCorrectRegisteredPassword(password)) {
      throw new InvalidPasswordException("Incorrect registered password");
    }
  }

  private static boolean isCorrectRegisteredLogin(String login) {
    return login.length() >= UserDto.MIN_LOG_LEN &&
        login.length() <= UserDto.MAX_LOG_LEN &&
        startsWithLetter(login) &&
        isAlphaNum(login);
  }

  private static boolean isCorrectRegisteredPassword(String password) {
    return password.length() >= UserDto.MIN_PASS_LEN &&
        password.length() <= UserDto.MAX_PASS_LEN &&
        startsWithLetter(password) &&
        containsNumber(password) &&
        containsCapitalLetter(password) &&
        isAlphaNum(password);
  }

  private static boolean startsWithLetter(String str) {
    return Character.isLetter(str.charAt(0));
  }

  private static boolean containsNumber(String str) {
    return str.chars()
        .anyMatch(Character::isDigit);
  }

  private static boolean containsCapitalLetter(String str) {
    return str.chars()
        .anyMatch(Character::isUpperCase);
  }

  private static boolean isAlphaNum(String str) {
    return str.chars()
        .allMatch(ch -> ch >= 'A' && ch <= 'Z' ||
            ch >= 'a' && ch <= 'z' ||
            ch >= '0' && ch <= '9');
  }
}
