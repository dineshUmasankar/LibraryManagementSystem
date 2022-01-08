package org.psu.dUmasankar.LMS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.psu.dUmasankar.LMSModel.BookModel;

public class LMSBookManager {
	
	private String connectionStr = "jdbc:mysql://localhost:3306/lmsdb";
	private String connectionUserStr = "LMSBookManager";
	private String connectionPassStr = "KhhCUWu3t!=B8%C=";
	
	public boolean checkHoldStatus(String book_ID) {
		boolean holdStatus = false;
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, heldBy FROM books INNER JOIN authors ON books.author = authors.author_ID INNER JOIN bookcategories ON books.category = bookcategories.category_ID WHERE book_id = ?");
    		pstmt.setString(1, book_ID);
    		ResultSet rs = pstmt.executeQuery();
    		
    		if (rs.next())
    		{
    			int userID = rs.getInt("heldBy");
    			holdStatus = (userID == 0) ? false : true;
    		}
    		
    		rs.close();
    		pstmt.close();
    		con.close();
    		
    		return holdStatus;
		} catch (Exception e)
		{
			e.printStackTrace();
			return holdStatus;
		}
	}
	
	public boolean checkBorrowedStatus(String book_ID) {
		boolean borrowStatus = false;
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, borrowedBy FROM books INNER JOIN authors ON books.author = authors.author_ID INNER JOIN bookcategories ON books.category = bookcategories.category_ID WHERE book_id = ?");
    		pstmt.setString(1, book_ID);
    		ResultSet rs = pstmt.executeQuery();
    		
    		if (rs.next())
    		{
    			int userID = rs.getInt("borrowedBy");
    			borrowStatus = (userID == 0) ? false : true;
    		}
    		
    		rs.close();
    		pstmt.close();
    		con.close();
    		
    		return borrowStatus;
		} catch (Exception e)
		{
			e.printStackTrace();
			return borrowStatus;
		}
	}
	
	public void holdBook(String username, String book_ID, boolean borrowedStatus)
	{
		LMSAuth auth = new LMSAuth();
		String account_ID = auth.getUserID(username);
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			if (borrowedStatus == false)
			{
				PreparedStatement pstmt = con.prepareStatement("UPDATE books SET heldBy = ?, heldUntil = DATE_ADD(NOW(), INTERVAL 1 WEEK) WHERE (book_ID = ?)");
				pstmt.setString(1, account_ID);
				pstmt.setString(2, book_ID);
				int rowsAffected = pstmt.executeUpdate();
				
	    		pstmt.close();
			}
			else
			{
				PreparedStatement pstmt = con.prepareStatement("UPDATE books SET heldBy = ?, heldUntil = DATE_ADD(borrowedUntil, INTERVAL 1 WEEK) WHERE (book_ID = ?)");
				pstmt.setString(1, account_ID);
				pstmt.setString(2, book_ID);
				int rowsAffected = pstmt.executeUpdate();
				
	    		pstmt.close();
			}
    		
			con.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void borrowBook(String username, String book_ID, boolean holdStatus)
	{
		LMSAuth auth = new LMSAuth();
		String account_ID = auth.getUserID(username);
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			if (holdStatus == false)
			{
				PreparedStatement pstmt = con.prepareStatement("UPDATE books SET borrowedBy = ?, borrowedUntil = DATE_ADD(NOW(), INTERVAL 1 WEEK) WHERE (book_ID = ?)");
				pstmt.setString(1, account_ID);
				pstmt.setString(2, book_ID);
				int rowsAffected = pstmt.executeUpdate();
				
	    		pstmt.close();
			}
			else
			{
				PreparedStatement pstmt = con.prepareStatement("UPDATE books SET borrowedBy = ?, borrowedUntil = DATE_ADD(heldUntil, INTERVAL 1 WEEK) WHERE (book_ID = ?)");
				pstmt.setString(1, account_ID);
				pstmt.setString(2, book_ID);
				int rowsAffected = pstmt.executeUpdate();
				
	    		pstmt.close();
			}
    		
    		con.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public List<BookModel> getHeldBooks(String username)
	{
		List<BookModel> userHeldBooks = new ArrayList<BookModel>();
		
		LMSAuth auth = new LMSAuth();
		String account_ID = auth.getUserID(username);
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, heldBy, heldUntil, borrowedUntil FROM books INNER JOIN authors ON books.author = authors.author_ID INNER JOIN bookcategories ON books.category = bookcategories.category_ID WHERE heldBy = ?");
    		pstmt.setString(1, account_ID);
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("name");
				String category = rs.getString("categoryName");
				Date heldUntil = rs.getDate("heldUntil");
				Date returnBy = rs.getDate("borrowedUntil");
				
				BookModel heldBook = new BookModel(id, title, author, category, heldUntil, returnBy);
				userHeldBooks.add(heldBook);				
			}
    		
    		rs.close();
    		pstmt.close();
    		con.close();
    		
    		return userHeldBooks;
		} catch (Exception e)
		{
			e.printStackTrace();
			return userHeldBooks;
		}
	}
	
	public List<BookModel> getBorrowedBooks(String username)
	{
		List<BookModel> userBorrowedBooks = new ArrayList<BookModel>();
		
		LMSAuth auth = new LMSAuth();
		String account_ID = auth.getUserID(username);
		try
		{
			Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
			
			PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, borrowedBy, heldUntil, borrowedUntil FROM books INNER JOIN authors ON books.author = authors.author_ID INNER JOIN bookcategories ON books.category = bookcategories.category_ID WHERE borrowedBy = ?");
    		pstmt.setString(1, account_ID);
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("name");
				String category = rs.getString("categoryName");
				Date heldUntil = rs.getDate("heldUntil");
				Date returnBy = rs.getDate("borrowedUntil");
				
				
				BookModel borrowedBook = new BookModel(id, title, author, category, heldUntil, returnBy);
				userBorrowedBooks.add(borrowedBook);				
			}
    		
    		rs.close();
    		pstmt.close();
    		con.close();
    		
    		return userBorrowedBooks;
		} catch (Exception e)
		{
			e.printStackTrace();
			return userBorrowedBooks;
		}
	}
}
