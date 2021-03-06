package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.pojos.User;
import com.revature.services.Service;

public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = 5363702488891722177L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("login.html");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User client = new User();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		client.setEmail(username);
		client.setPassword(password);
		
		client = new Service().loginUser(client);
		
		if(client == null) {
			throw new InvalidCredentialsException("Invalid Credentials.");
		}else if(client != null) {
			
			HttpSession session = req.getSession();
			
			session.setAttribute("user", client);
			session.setMaxInactiveInterval(6000);
			
			System.out.println("New cookie " + session.getAttribute("user") + " assigned.");
				
			if(client.getUser_role() == 1) {
				resp.sendRedirect("managerhome.html");
			}else{
				resp.sendRedirect("employeehome.html");
			}
			
		}
	}

}
