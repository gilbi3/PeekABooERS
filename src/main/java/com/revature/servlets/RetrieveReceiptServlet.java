package com.revature.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.pojos.Reimbursement;
import com.revature.services.Service;

public class RetrieveReceiptServlet extends HttpServlet{

	private static final long serialVersionUID = -6660190454303544690L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Reimbursement reim = new Reimbursement();
		
		String path = req.getServletContext().getRealPath("/");
		Service.setPicturePath(path + "image.png");
		
		String r_id = req.getParameter("receipt-trigger");
		int id = Integer.parseInt(r_id);
		
		reim.setR_id(id);
		
		Service.getReceipt(reim);
		
		resp.sendRedirect("receipt.html");
	}
}
