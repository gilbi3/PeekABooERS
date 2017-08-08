package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxNavbarServlet extends HttpServlet{
	
	private static final long serialVersionUID = -3341845633371440350L;
	
	public AjaxNavbarServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("features/navbar/navbar.html").forward(req, resp);
	}

}
