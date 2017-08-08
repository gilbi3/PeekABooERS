package com.revature.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	
public static void sendEmail(String passwd, String email){
		
		final String username = "gilbybirdsong@gmail.com";
		final String password = "password123!";
		String receiver = email;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(receiver));
			message.setSubject("Welcome to Peek-a-Boo!");
			message.setText("Welcome to the team! Your temporary password is " + passwd + ", and your username is" +
					" " + email + ". Feel free to click the following link to set your own! \n"
						+ "Password Reset: http://localhost:7001/PeekABooERS/initiallogin.html");			

			Transport.send(message);
			
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
