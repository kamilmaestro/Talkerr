package com.kamilmarnik.talkerr.user.domain;

import com.kamilmarnik.talkerr.user.dto.UserDto;
import com.kamilmarnik.talkerr.user.exception.InvalidLoginException;
import com.kamilmarnik.talkerr.user.exception.InvalidPasswordException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class LoginAndPasswordVerifier {

  String login;
  String password;

  private LoginAndPasswordVerifier(String login, String password) {
    this.login = login;
    this.password = password;
  }

  static LoginAndPasswordVerifier create(String login, String password) {
    return new LoginAndPasswordVerifier(login, password);
  }

  void verify() throws InvalidLoginException, InvalidPasswordException {
    if(!isCorrectRegisteredLogin()) {
      throw new InvalidLoginException("Incorrect registered username!");
    }

    if(!isCorrectRegisteredPassword()) {
      throw new InvalidPasswordException("Incorrect registered password");
    }
  }

  private boolean isCorrectRegisteredLogin() {
    return login.length() >= UserDto.MIN_LOG_LEN &&
        login.length() <= UserDto.MAX_LOG_LEN &&
        startsWithLetter(login) &&
        isAlphaNum(login);
  }

  private boolean isCorrectRegisteredPassword() {
    return password.length() >= UserDto.MIN_PASS_LEN &&
        password.length() <= UserDto.MAX_PASS_LEN &&
        startsWithLetter(password) &&
        containsNumber(password) &&
        containsCapitalLetter(password) &&
        isAlphaNum(password);
  }

  private boolean startsWithLetter(String str) {
    return Character.isLetter(str.charAt(0));
  }

  private boolean containsNumber(String str) {
    return str.chars()
        .anyMatch(Character::isDigit);
  }

  private boolean containsCapitalLetter(String str) {
    return str.chars()
        .anyMatch(Character::isUpperCase);
  }

  private boolean isAlphaNum(String str) {
    return str.chars()
        .allMatch(ch -> ch >= 'A' && ch <= 'Z' ||
            ch >= 'a' && ch <= 'z' ||
            ch >= '0' && ch <= '9');
  }
}
