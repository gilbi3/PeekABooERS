package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.exceptions.PasswordMismatchException;
import com.revature.pojos.User;
import com.revature.services.Service;

public class ResetPasswordServlet extends HttpServlet{
	
	private static final long serialVersionUID = 3729617544968612394L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		String password = req.getParameter("password");
		String passwordconfirm = req.getParameter("password-confirm");
		
		if(password.equals(passwordconfirm)) {
			System.out.println(user.getPassword());
			user.setPassword(password);
			Service.resetPassword(user);
			resp.sendRedirect("employeehome.html");
		}else{
			throw new PasswordMismatchException("Passwords do not match.");
		}
	}

}
