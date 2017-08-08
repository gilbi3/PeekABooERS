package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.exceptions.NoSessionFoundException;


public class LogoutServlet extends HttpServlet{
	
	private static final long serialVersionUID = -3917661277044139820L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		if(session == null) {
			throw new NoSessionFoundException("No such session. Session  may have been "
											+ "subject to external termination");
		}
		 if(session.getAttribute("user") != null){
			session.invalidate();
			System.out.println("Session invalidated.");
			resp.sendRedirect("goodbye.html");
		}
		
	}
}
