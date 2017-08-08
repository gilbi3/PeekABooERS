package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxEmployeeUpdateFormServlet extends HttpServlet{
	
	private static final long serialVersionUID = -3342245633371450350L;
	
	public AjaxEmployeeUpdateFormServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("features/employeeupdateinfoform/employeeupdateinfoform.html").forward(req, resp);
	}

}
