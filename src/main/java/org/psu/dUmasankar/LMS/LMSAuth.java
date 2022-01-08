package org.psu.dUmasankar.LMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LMSAuth {
	
	private String connectionStr = "jdbc:mysql://localhost:3306/lmsdb";
	private String connectionUserStr = "LMSAuth";
	private String connectionPassStr = "KhhCUWu3t!=B8%C=";
	
	public boolean login(String username, String password)
	{
		try 
		{
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		
    		PreparedStatement pstmt = con.prepareStatement("SELECT pwd FROM accounts WHERE username = ?");
    		pstmt.setString(1, username);
    		ResultSet rs = pstmt.executeQuery();
    		
    		
    		Boolean validLogin = false;
    		PasswordEncoder encoder = new BCryptPasswordEncoder();
        	
    		if (rs.next()) 
    		{
    			String dbHashPass = rs.getString("pwd");
    			validLogin = encoder.matches(password, dbHashPass) ? true : false;
    		}
    		
    		
    		rs.close();
    		pstmt.close();
    		con.close();
    		return validLogin;
		} catch (Exception error) 
		{
    		error.printStackTrace();
    		return false;
    	}
		
	}
	
	public String getRole(String username)
	{
		String userRole = null;
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			PreparedStatement pstmt = con.prepareStatement("SELECT role FROM accounts WHERE username = ?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
	    	
			if (rs.next()) 
			{
				userRole = rs.getString("role");
			}
			
			
			rs.close();
			pstmt.close();
			con.close();
			return userRole;
		} catch (Exception error) 
		{
			System.out.println(error);
			return userRole;
		}
	}
	
	public String getUserID(String username)
	{
		String userID = null;
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			PreparedStatement pstmt = con.prepareStatement("SELECT account_ID FROM accounts WHERE username = ?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
	    	
			if (rs.next()) 
			{
				userID = Integer.toString(rs.getInt("account_ID"));
			}
			
			
			rs.close();
			pstmt.close();
			con.close();
			return userID;
		} catch (Exception error) 
		{
			System.out.println(error);
			return userID;
		}
	}
	
	public int createAccount(String username, String rawPass, String email, String phoneNumber, String role)
	{
		int rowsAffected = 0;
		
		try 
		{
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashedPass = encoder.encode(rawPass);
			
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("INSERT INTO accounts (username, pwd, email, phoneNum, role) VALUES(?,?,?,?,?)");
    		pstmt.setString(1, username);
    		pstmt.setString(2, hashedPass);
    		pstmt.setString(3, email);
    		pstmt.setString(4, phoneNumber);
    		pstmt.setString(5, role);
    		rowsAffected = pstmt.executeUpdate();
    		
    		pstmt.close();
    		con.close();
    		return rowsAffected;
		} catch (Exception error) 
		{
    		error.printStackTrace();
    		return rowsAffected;
    	}
	}
	
	public int createAccount(String username, String rawPass, String email, String phoneNumber, String role, Time[] weeklyStartSchedule, Time[] weeklyEndSchedule)
	{
		LMSAuth auth = new LMSAuth();
		int rowsAffected = 0;
		int scheduleRowsAffected = 0;
		try 
		{
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashedPass = encoder.encode(rawPass);
			
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("INSERT INTO accounts (username, pwd, email, phoneNum, role) VALUES(?,?,?,?,?)");
    		pstmt.setString(1, username);
    		pstmt.setString(2, hashedPass);
    		pstmt.setString(3, email);
    		pstmt.setString(4, phoneNumber);
    		pstmt.setString(5, role);
    		rowsAffected = pstmt.executeUpdate();
    		
    		PreparedStatement pstmtSchedule = con.prepareStatement("INSERT INTO employeeschedule (`schedule_ID`, `SunStart`, `MonStart`, `TueStart`, `WedStart`, `ThuStart`, `FriStart`, `SatStart`, `SunEnd`, `MonEnd`, `TueEnd`, `WedEnd`, `ThuEnd`, `FriEnd`, `SatEnd`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    		pstmtSchedule.setInt(1, Integer.parseInt(auth.getUserID(username)));
    		for (int daysInWeek = 0; daysInWeek < 7; daysInWeek++)
    		{
    			pstmtSchedule.setTime(daysInWeek+2, weeklyStartSchedule[daysInWeek]);
    			pstmtSchedule.setTime(daysInWeek+9, weeklyEndSchedule[daysInWeek]);
    		}
    		scheduleRowsAffected = pstmtSchedule.executeUpdate();
    		pstmtSchedule.close();
    		
    		pstmt.close();
    		con.close();
    		return scheduleRowsAffected;
		} catch (Exception error) 
		{
    		error.printStackTrace();
    		return scheduleRowsAffected;
    	}
	}
}
