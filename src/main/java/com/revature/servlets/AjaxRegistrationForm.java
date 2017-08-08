package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxRegistrationForm extends HttpServlet{

	private static final long serialVersionUID = -7280390963138349153L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("features/registeremployee/registeremployee.html").forward(req, resp);
	}
}
