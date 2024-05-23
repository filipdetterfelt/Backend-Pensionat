package com.example.backendpensionat.Security;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailTestSender {




        public void sendTestEmail(){


            final String username = "kattie58@ethereal.email";
            final String password = "M5ZRTxFt9DAxBnVZKc";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.ethereal.email");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("your_email@example.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("recipient2@example.com"));
                message.setSubject("Testing Ethereal2");
                message.setText("Hello, this is a test email from Ethereal.");

                Transport.send(message);

                System.out.println("Email sent successfully.");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

