package ar.edu.utn.frba.dds.apimail;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import java.util.Properties;
import javax.mail.internet.MimeMessage;
import javax.persistence.Transient;

public class ApiMail {
  @Transient
  final String username = "disenio454@gmail.com";
  @Transient
  final String password = "owdc hezu iabn peav";
  Properties props = new Properties();

  public void configProps() {
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
  }

  public Session configSession() {
    return Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });
  }

  public void enviar(String subject, String mensaje, String mail) {
    Session session = configSession();
    configProps();

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setSubject(subject);
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
      message.setText(mensaje);
      Transport.send(message);
    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
