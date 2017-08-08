package com.revature.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.exceptions.ImageNotFoundException;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.pojos.ViewableReimbursement;
import com.revature.services.Service;

public class DaoImpl implements Dao{


	static {
		try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	
	private static final String USERNAME = "AWS_BANK_DB";
	private static final String PASSWORD = "p4ssw0rd";
	private static final String URL = "jdbc:oracle:thin:@anaximander11.cfwfoelqq77b.us-east-1.rds.amazonaws.com:1521:orcl";
	
	
	@Override
	public void createEmployee(User user) {
		String sql = "INSERT INTO r_user(f_name, l_name, email, passwd, user_role_id) VALUES(?, ?, ?, ?, 2)";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			System.out.println("Submitting Request.");
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void createReimbursement(Reimbursement reim) {
		String sql = "INSERT INTO reimbursement(r_status_id, r_type_id, r_ammount, r_submitted, r_author, r_description) VALUES(2, ?, ?, ?, ?, ?)";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			System.out.println("Submitting Request.");
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, reim.getType_id());
			ps.setDouble(2, reim.getAmount());
			ps.setString(3, reim.getDate_submitted());
			ps.setInt(4, reim.getSubmitter());
			ps.setString(5, reim.getDescription());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void uploadReimbursementReceipt(Reimbursement reim, InputStream stream) {
		String sql = "UPDATE reimbursement set r_receipt = ? WHERE r_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setBlob(1, stream);
			ps.setInt(2, reim.getR_id());
			
			ps.executeUpdate();
			
			System.out.println("Receipt Uploaded.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public User getUser(User user) {
		User returnedUser = null;
		String sql = "SELECT * FROM r_user WHERE email = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
		
			PreparedStatement ps = connect.prepareStatement(sql);
			connect.setAutoCommit(false);
			System.out.println(user.getEmail());
			ps.setString(1, user.getEmail());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				 returnedUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
			}
			connect.commit();
			return returnedUser;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Reimbursement getReimbursementById(Reimbursement reim) {
		Reimbursement returnedReimbursement = null;
		String sql = "SELECT r_id, r_status_id, r_type_id, r_ammount, r_submitted, r_resolved, r_author," +
		" r_resolver, r_description FROM reimbursement WHERE r_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
		
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, reim.getR_id());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				 returnedReimbursement = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4),
						 rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9));
			}
			return returnedReimbursement;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public int getNewestReimbursementId(User user) {
					 // Retrieves current user's most recent request's ID.
		String sql = "SELECT MAX(r_id) FROM reimbursement WHERE r_author = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			int current = user.getUser_id();
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, current);
			
			ResultSet rs = ps.executeQuery();
			int newest = 0;
			rs.next();
			newest = (rs.getInt(1));
			return newest;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	@Override
	public void getReimbursementReceipt(Reimbursement reim){

		Blob picture = null;
		String sql = "SELECT r_receipt FROM reimbursement WHERE r_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
		
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, reim.getR_id());
			ResultSet rscheck = ps.executeQuery();
			rscheck.next();
			if(rscheck.getBlob(1)==null){
				throw new ImageNotFoundException("Image not found in database.");
			}else{
			
				ResultSet rs = ps.executeQuery();
				rs.next();
				picture = rs.getBlob(1);
	
				File newFile = null;
				// Create new file at path derived from ServletContext in RetrieveReceiptServlet
				System.out.println(Service.getPicturePath());
				newFile = new File(Service.getPicturePath());
				newFile.createNewFile();
				// InputStream created to take in result from PreparedStatement
				InputStream is = picture.getBinaryStream();
				// ByteArrayOutputStream created to direct InputStream
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				// FileOutputStream created to funnel ByteArrayOutputStream into created file.
				OutputStream output = new FileOutputStream(newFile);
				int length = (int) picture.length();
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				//Reporting number of bytes coming through InputStream
				System.out.println("Writing " + length + " bytes");
				while((length = is.read(buffer)) != -1) {
					out.write(buffer, 0, length);
				}
			out.writeTo(output);
			// Closing streams
			out.close();
			is.close();
			return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}


	@Override
	public ArrayList<User> getAllEmployees() {
		ArrayList<User> all_users = new ArrayList<User>();
		String sql = "SELECT * FROM r_user";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			// Querying all transactions belonging to current user
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				all_users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6)));
			}
			return all_users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<ViewableReimbursement> getAllReimbursements() {
		ArrayList<ViewableReimbursement> all_reimbursements = new ArrayList<ViewableReimbursement>();
		String sql = "SELECT re.r_id, rstat.r_status, u.f_name, u.l_name, ty.r_type, re.r_ammount, re.r_submitted, re.r_resolved, "
				+ "u2.f_name, u2.l_name, re.r_description, u.user_id FROM reimbursement re FULL JOIN r_user u ON "
				+ "re.r_author = u.user_id FULL JOIN r_user u2 ON re.r_resolver = u2.user_id JOIN r_status_type "
				+ "ty ON ty.r_type_id = re.r_type_id JOIN r_status rstat ON rstat.r_status_id = re.r_status_id"
				+ " ORDER BY rstat.r_status";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			// Querying all transactions belonging to current user
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				all_reimbursements.add(new ViewableReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), 
						rs.getString(11), rs.getInt(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all_reimbursements;
	}
	

	@Override
	public void resolveReimbursement(Reimbursement reim) {
		String sql = "UPDATE reimbursement SET r_status_id = ?, r_resolved=?, r_resolver=? WHERE r_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, reim.getStatus_id());
			ps.setString(2, reim.getDate_resolved());
			ps.setInt(3, reim.getResolver());
			ps.setInt(4, reim.getR_id());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
		
	}
	
	
	public void ammendUser(User user) {
		String sql = "UPDATE r_user SET f_name = ?, l_name = ?, email = ?, passwd = ? WHERE user_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setInt(5, user.getUser_id());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	public void ammendPassword(User user) {
		String sql = "UPDATE r_user SET passwd = ? WHERE user_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, user.getPassword());
			ps.setInt(2, user.getUser_id());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}


	@Override
	public void voidRequest(Reimbursement reim) {
		String sql = "DELETE FROM reimbursement WHERE r_id = ?";
		try(Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			
			System.out.println("Voiding request. No receipt uploaded.");
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setInt(1, reim.getR_id());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
