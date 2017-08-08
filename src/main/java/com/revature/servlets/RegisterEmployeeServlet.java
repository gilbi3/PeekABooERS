package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.pojos.User;
import com.revature.services.Service;

public class RegisterEmployeeServlet extends HttpServlet{
	
	private static final long serialVersionUID = -1683922674751049744L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = new User();
		
		String firstname = req.getParameter("first-name");
		String lastname = req.getParameter("last-name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setPassword(password);
		
		Service.registerEmployee(user);
		
		resp.sendRedirect("managerhome.html");
		
	}

}
