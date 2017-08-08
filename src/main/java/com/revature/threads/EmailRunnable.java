package com.revature.threads;

import com.revature.services.SendEmail;

public class EmailRunnable implements Runnable{

	public EmailRunnable(String passwd, String email) {
		SendEmail.sendEmail(passwd, email);
	}
	
	@Override
	public void run() {
	}

}
