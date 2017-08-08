package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.services.Service;


public class ResolutionServlet extends HttpServlet{

	private static final long serialVersionUID = 3307140377955297887L;
	
	public ResolutionServlet() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("user");
		
		Reimbursement target = new Reimbursement();
		User currentUser = sessionUser;
		
		int status = Integer.parseInt(req.getParameter("submitter"));
		int r_id = Integer.parseInt(req.getParameter("r_id"));
		
		target.setStatus_id(status);
		target.setR_id(r_id);
		target.setResolver(currentUser.getUser_id());
		target.setDate_resolved(Service.getCurrentDate());
		
		Service.resolve(target);
		
		resp.sendRedirect("managerhome.html");
		
	}

}
