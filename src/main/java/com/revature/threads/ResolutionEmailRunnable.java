package com.revature.threads;

import com.revature.pojos.Reimbursement;
import com.revature.services.SendResolutionEmail;

public class ResolutionEmailRunnable implements Runnable{

	public ResolutionEmailRunnable(Reimbursement reim, String email) {
		SendResolutionEmail.sendEmail(reim, email);
	}
	
	@Override
	public void run() {
	}

}
