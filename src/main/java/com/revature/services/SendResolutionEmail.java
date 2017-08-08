package com.revature.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.revature.pojos.Reimbursement;

public class SendResolutionEmail {
	
public static void sendEmail(Reimbursement reim, String email){
		
		final String username = "gilbybirdsong@gmail.com";
		final String password = "password123!";
		String receiver = email;
		
		System.out.println(reim.getAmount());
		
		String status = "";
		
		if(reim.getStatus_id() == 1) {
			status = "approved";
		}else if(reim.getStatus_id() == 3) {
			status = "denied";
		}
		
		NumberFormat formatter = new DecimalFormat("#0.00");     
		String amount = formatter.format(reim.getAmount());

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
			message.setSubject("Your Expense Reimbursement has been resolved.");
			message.setText("Your request for reimbursement for the amount of $" + amount +
					" has been " + status + ". \nThank you for your service in chasing apparitions "
				  + "and the like.");			

			Transport.send(message);
			
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
