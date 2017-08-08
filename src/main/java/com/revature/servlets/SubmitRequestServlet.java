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

public class SubmitRequestServlet extends HttpServlet{
	
	private static final long serialVersionUID = -1683922674751049744L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Reimbursement newReim = new Reimbursement();
		
		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("user");
		session.setAttribute("reimbursement", newReim);
		
		int type = Integer.parseInt(req.getParameter("type"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		String description = req.getParameter("description");
		String date = Service.getCurrentDate();
		int submitter = sessionUser.getUser_id();
		
		newReim.setType_id(type);
		newReim.setAmount(amount);
		newReim.setDescription(description);
		newReim.setDate_submitted(date);
		newReim.setSubmitter(submitter);
		
		if(newReim.getAmount() != 0.00) {
			Service.submitRequest(newReim, sessionUser);
		}
		resp.sendRedirect("receiptupload.html");
		
	}

}
