package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.pojos.User;
import com.revature.services.Service;

public class EmployeeUpdateInfoServlet extends HttpServlet{
	
	private static final long serialVersionUID = -1683922674751049744L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("user");
		
		User current = sessionUser;
		
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		current.setFirstname(firstname);
		current.setLastname(lastname);
		current.setEmail(email);
		current.setPassword(password);
		
		Service.updateEmployee(current);

		resp.sendRedirect("employeehome.html");
		
	}

}
