package com.example.demo.Service;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${gmail.username}")
    private String from;

    @Value("${gmail.password}")
    private String password;

    @Value("${gmail.to}")
    private String to;
    private static final Logger logger = LoggerFactory.getLogger(JobCheckService.class);
    public void sendEmail(String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Always use string "true"
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            logger.info("Email sent to: " + to);
            //System.out.println("Email sent to: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
