package com.kamilmarnik.talkerr.mail;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "application.mail")
public class MailSender {

  String mail;
  String password;

  public MailSender() {}

  public void sendRegistrationConfirmationMail(String mailTo, String userName) {
    Properties prop = createProperties();
    Session session = authenticate(prop);

    try {
      Transport.send(createMessage(mailTo, userName, session));
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private Properties createProperties() {
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");

    return prop;
  }

  private Session authenticate(Properties prop) {
    return Session.getInstance(prop,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(mail, password);
          }
        });
  }

  private Message createMessage(String mailTo, String userName, Session session) throws MessagingException {
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(mail));
    message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse(mailTo)
    );
    message.setSubject("Talkerr says hello!");
    message.setText("Thank you for registration " + userName + "! You are now a part of Talkerr community.");

    return message;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
