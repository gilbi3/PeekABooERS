package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxRequestFormServlet extends HttpServlet{
	
	private static final long serialVersionUID = -3341845633371440350L;
	
	public AjaxRequestFormServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("features/requestform/requestform.html").forward(req, resp);
	}

}
