package com.revature.dao;

import java.io.InputStream;
import java.util.ArrayList;

import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.pojos.ViewableReimbursement;

public interface Dao {
	
	public void createEmployee(User user);
	
	public void createReimbursement(Reimbursement reim);
	
	void uploadReimbursementReceipt(Reimbursement reim, InputStream stream);
	
	public User getUser(User user);
	
	public Reimbursement getReimbursementById(Reimbursement reim);
	
	int getNewestReimbursementId(User user);
	
	public void getReimbursementReceipt(Reimbursement reim);
	
	public ArrayList<User> getAllEmployees();
	
	public ArrayList<ViewableReimbursement> getAllReimbursements();
	
	public void resolveReimbursement(Reimbursement reim);
	
	public void ammendUser(User user);
	
	public void ammendPassword(User user);
	
	public void voidRequest(Reimbursement reim);


}
