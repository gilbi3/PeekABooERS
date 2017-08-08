package com.revature.services;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.revature.dao.DaoImpl;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.pojos.ViewableReimbursement;
import com.revature.threads.EmailRunnable;
import com.revature.threads.ResolutionEmailRunnable;

public class Service {
	
	private static String picturePath;
	
	public User loginUser(User user) throws InvalidCredentialsException{
		DaoImpl imp = new DaoImpl();
		User storedUser = imp.getUser(user);
		if (storedUser.getEmail().equals(user.getEmail()) &&
			storedUser.getPassword().equals(user.getPassword())){
			return storedUser;
		}
		return null;
	}
	
	public static ArrayList<ViewableReimbursement> getAllRequests() {
		DaoImpl imp = new DaoImpl();
		ArrayList<ViewableReimbursement> all_reimbursements = imp.getAllReimbursements();
		return all_reimbursements;
	}
	
	public static ArrayList<User> listAllEmployees(){
		DaoImpl dao = new DaoImpl();
		ArrayList<User> all_users = dao.getAllEmployees();
		return all_users;
	}
	
	public static void registerEmployee(User user) {
		DaoImpl dao = new DaoImpl();
		dao.createEmployee(user);
		Runnable run = new EmailRunnable(user.getPassword(), "gibbirdsong@gmail.com"); // Using filler email.
		Thread th = new Thread(run);
		th.start();
	}
	
	public static void resetPassword(User user) {
		DaoImpl dao = new DaoImpl();
		dao.ammendPassword(user);
	}
	
	public static void submitRequest(Reimbursement reim, User user){
		DaoImpl dao = new DaoImpl();
		dao.createReimbursement(reim);
		int newId = dao.getNewestReimbursementId(user);
		reim.setR_id(newId);
	}
	
	public static void uploadReceipt(Reimbursement reim, InputStream stream) {
		DaoImpl dao = new DaoImpl();
		dao.uploadReimbursementReceipt(reim, stream);
	}
	
	public static void updateEmployee(User user){
		DaoImpl dao = new DaoImpl();
		dao.ammendUser(user);
	}
	
	public static void resolve(Reimbursement reim) {
		DaoImpl dao = new DaoImpl();
		dao.resolveReimbursement(reim);
		Reimbursement completeReim  = dao.getReimbursementById(reim);
		Runnable run = new ResolutionEmailRunnable(completeReim, "gibbirdsong@gmail.com"); // Using filler email.
		Thread th = new Thread(run);
		th.start();
	}
	
	public static String getCurrentDate() {
		java.util.Date today = Calendar.getInstance().getTime();  
	    SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
	    String sqlDate = format2.format(today);
	    return sqlDate;
	}
	
	public static void getReceipt(Reimbursement reim) {
		DaoImpl dao = new DaoImpl();
		dao.getReimbursementReceipt(reim);
	}
	
	public static void nullifyRequest(Reimbursement reim) {
		DaoImpl dao = new DaoImpl();
		dao.voidRequest(reim);
	}

	public static String getPicturePath() {
		return picturePath;
	}

	public static void setPicturePath(String picturePath) {
		Service.picturePath = picturePath;
	}	
	
	
	

}
