package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.revature.pojos.User;
import com.revature.services.Service;


public class GetAllUsersServlet extends HttpServlet{

	private static final long serialVersionUID = -4621646451699032914L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ArrayList<User> all_users = Service.listAllEmployees();
		
		Gson gson = new Gson();
		String json = gson.toJson(all_users);
		
		resp.getWriter().print(json);
	}

}
