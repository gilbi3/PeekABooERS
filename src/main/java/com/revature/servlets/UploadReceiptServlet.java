package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.revature.pojos.Reimbursement;
import com.revature.services.Service;


public class UploadReceiptServlet extends HttpServlet{
	private static final long serialVersionUID = -1683922674455359744L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();		
		Reimbursement reim = (Reimbursement) session.getAttribute("reimbursement");
		
		final Part receipt = req.getPart("receipt");
		
		if(receipt.getSize() == 0) {
			Service.nullifyRequest(reim);
			resp.sendRedirect("noreceipt.html");
			return;
		}
		
		InputStream stream = receipt.getInputStream();
		
		Service.uploadReceipt(reim, stream);
		
		stream.close();
		
		System.out.println("Receipt Uploaded");
		
		resp.sendRedirect("employeehome.html");
		
	}

}
