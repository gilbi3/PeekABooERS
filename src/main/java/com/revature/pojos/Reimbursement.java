package com.revature.pojos;

import java.io.Serializable;
import java.sql.Blob;

public class Reimbursement implements Serializable{

	private static final long serialVersionUID = -1387889525730882027L;
	
	private int r_id;
	private int status_id;
	private int type_id;
	private Blob receipt;
	private double amount;
	private String date_submitted;
	private String date_resolved;
	private int submitter;
	private int resolver;
	private String description;
	
	public Reimbursement() {}
	
	public Reimbursement(int r_id, int status_id, int type_id, double amount, String date_submitted,
			String date_resolved, int submitter, int resolver, String description) {
		super();
		this.r_id = r_id;
		this.status_id = status_id;
		this.type_id = type_id;
		this.amount = amount;
		this.date_submitted = date_submitted;
		this.date_resolved = date_resolved;
		this.submitter = submitter;
		this.resolver = resolver;
		this.description = description;
	}

	public Reimbursement(int r_id, int status_id, int type_id, Blob receipt, double amount, String date_submitted,
			String date_resolved, int submitter, int resolver, String description) {
		super();
		this.r_id = r_id;
		this.status_id = status_id;
		this.type_id = type_id;
		this.receipt = receipt;
		this.amount = amount;
		this.date_submitted = date_submitted;
		this.date_resolved = date_resolved;
		this.submitter = submitter;
		this.resolver = resolver;
		this.description = description;
	}

	public int getR_id() {
		return r_id;
	}

	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public Blob getReceipt() {
		return receipt;
	}

	public void setReceipt(Blob receipt) {
		this.receipt = receipt;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double ammount) {
		this.amount = ammount;
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

	public int getSubmitter() {
		return submitter;
	}

	public void setSubmitter(int submitter) {
		this.submitter = submitter;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Reimbursement [r_id=" + r_id + ", status_id=" + status_id + ", type_id=" + type_id + ", receipt="
				+ receipt + ", amount=" + amount + ", date_submitted=" + date_submitted + ", date_resolved="
				+ date_resolved + ", submitter=" + submitter + ", resolver=" + resolver + ", description=" + description
				+ "]";
	}

	

}
