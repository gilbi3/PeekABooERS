package com.revature.pojos;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -7160729072899067557L;
	
	private int user_id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private int user_role;
	
	public User() {}

	public User(int user_id, String firstname, String lastname, String email, String password, int user_role) {
		super();
		this.user_id = user_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.user_role = user_role;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUser_role() {
		return user_role;
	}

	public void setUser_role(int user_role) {
		this.user_role = user_role;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", user_role=" + user_role + "]";
	}
	

}
