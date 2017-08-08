package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.pojos.User;

public class EmployeeInfoServlet extends HttpServlet{

	private static final long serialVersionUID = 5977375577139819949L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("user");

		Gson gson = new Gson();
		
		String json = gson.toJson(sessionUser);
		
		resp.getWriter().print(json);
		
	}
	
}
