package com.revature.pojos;

import java.io.Serializable;

public class ViewableReimbursement implements Serializable{
	
	private static final long serialVersionUID = 9096211587530362938L;
	
	private int id;
	private String status;
	private String auth_first_name;
	private String auth_last_name;
	private String type;
	private double amount;
	private String date_submitted;
	private String date_resolved;
	private String reso_first_name;
	private String reso_last_name;
	private String description;
	private int user_id;

	
	public ViewableReimbursement(){}
	
	// Without Blob
	public ViewableReimbursement(int id, String status, String auth_first_name, String auth_last_name, String type, double amount,
			String date_submitted, String date_resolved, String reso_first_name, String reso_last_name,
			String description, int user_id) {
		super();
		this.id = id;
		this.status = status;
		this.auth_first_name = auth_first_name;
		this.auth_last_name = auth_last_name;
		this.type = type;
		this.amount = amount;
		this.date_submitted = date_submitted;
		this.date_resolved = date_resolved;
		this.reso_first_name = reso_first_name;
		this.reso_last_name = reso_last_name;
		this.description = description;
		this.user_id = user_id;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuth_first_name() {
		return auth_first_name;
	}

	public void setAuth_first_name(String auth_first_name) {
		this.auth_first_name = auth_first_name;
	}

	public String getAuth_last_name() {
		return auth_last_name;
	}

	public void setAuth_last_name(String auth_last_name) {
		this.auth_last_name = auth_last_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate_submitted() {
		return date_submitted;
	}

	public void setDate_submitted(String date_submitted) {
		this.date_submitted = date_submitted;
	}

	public String getDate_resolved() {
		return date_resolved;
	}

	public void setDate_resolved(String date_resolved) {
		this.date_resolved = date_resolved;
	}

	public String getReso_first_name() {
		return reso_first_name;
	}

	public void setReso_first_name(String reso_first_name) {
		this.reso_first_name = reso_first_name;
	}

	public String getReso_last_name() {
		return reso_last_name;
	}

	public void setReso_last_name(String reso_last_name) {
		this.reso_last_name = reso_last_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	@Override
	public String toString() {
		return "ViewableReimbursement [auth_first_name=" + auth_first_name + ", auth_last_name=" + auth_last_name
				+ ", type=" + type + ", amount=" + amount + ", date_submitted=" + date_submitted + ", date_resolved="
				+ date_resolved + ", reso_first_name=" + reso_first_name + ", reso_last_name=" + reso_last_name
				+ ", description=" + description + "]";
	}


}
