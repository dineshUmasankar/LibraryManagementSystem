package org.psu.dUmasankar.LMS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.psu.dUmasankar.LMSModel.BookModel;
import org.psu.dUmasankar.LMSModel.StaffModel;

public class LMSSearch {
	private String connectionStr = "jdbc:mysql://localhost:3306/lmsdb";
	private String connectionUserStr = "LMSSearch";
	private String connectionPassStr = "KhhCUWu3t!=B8%C=";
	
	public List<BookModel> searchBookByTitle(String query)
	{
		List<BookModel> queryResult = new ArrayList<BookModel>();
		
		try
		{
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, heldUntil, borrowedUntil FROM books INNER JOIN authors ON books.author = authors.author_ID INNER JOIN bookcategories ON books.category = bookcategories.category_ID WHERE title LIKE ?");
    		query = "%" + query + "%";
    		pstmt.setString(1, query);
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("name");
				String category = rs.getString("categoryName");
				Date heldUntil = rs.getDate("heldUntil");
				Date returnBy = rs.getDate("borrowedUntil");
				
				BookModel newBook = new BookModel(id, title, author, category, heldUntil, returnBy);
				queryResult.add(newBook);				
			}
    		
			con.close();
    		pstmt.close();
    		rs.close();
    		
			return queryResult;    		
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BookModel> searchBookByAuthor(String query)
	{
		List<BookModel> queryResult = new ArrayList<BookModel>();
		
		try
		{
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, heldUntil, borrowedUntil FROM lmsdb.books INNER JOIN lmsdb.authors ON books.author = authors.author_ID INNER JOIN lmsdb.bookcategories ON books.category = bookcategories.category_ID WHERE name LIKE ?");
    		query = "%" + query + "%";
    		pstmt.setString(1, query);
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("name");
				String category = rs.getString("categoryName");
				Date heldUntil = rs.getDate("heldUntil");
				Date returnBy = rs.getDate("borrowedUntil");
				
				BookModel newBook = new BookModel(id, title, author, category, heldUntil, returnBy);
				queryResult.add(newBook);				
			}
    		
    		con.close();
    		pstmt.close();
    		rs.close();
    		
    		return queryResult;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BookModel> searchBookByCategory(String query)
	{
		List<BookModel> queryResult = new ArrayList<BookModel>();
		
		try
		{
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("SELECT book_id, title, name, categoryName, heldUntil, borrowedUntil FROM lmsdb.books INNER JOIN lmsdb.authors ON books.author = authors.author_ID INNER JOIN lmsdb.bookcategories ON books.category = bookcategories.category_ID WHERE categoryName LIKE ?");
    		query = "%" + query + "%";
    		pstmt.setString(1, query);
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("name");
				String category = rs.getString("categoryName");
				Date heldUntil = rs.getDate("heldUntil");
				Date returnBy = rs.getDate("borrowedUntil");
				
				BookModel newBook = new BookModel(id, title, author, category, heldUntil, returnBy);
				newBook.toString();
				
				queryResult.add(newBook);				
			}
    		
    		con.close();
    		pstmt.close();
    		rs.close();
    		
    		return queryResult;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<StaffModel> searchEmployeeByID(String query)
	{
		List<StaffModel> queryResult = new ArrayList<StaffModel>();
		
		try
		{
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("SELECT account_ID, username, SunStart, MonStart, TueStart, WedStart, ThuStart, FriStart, SatStart, SunEnd, MonEnd, TueEnd, WedEnd, ThuEnd, FriEnd, SatEnd FROM employeeschedule INNER JOIN accounts ON schedule_ID = accounts.account_ID WHERE account_ID = ?");
    		pstmt.setInt(1, Integer.parseInt(query));
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int account_ID = rs.getInt("account_ID");
				String username = rs.getString("username");
				
				StaffModel queryStaff = new StaffModel(account_ID, username);
				queryResult.add(queryStaff);
			}
    		
    		con.close();
    		pstmt.close();
    		rs.close();
    		
    		return queryResult;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<StaffModel> searchEmployeeByUserName(String query)
	{
		List<StaffModel> queryResult = new ArrayList<StaffModel>();
		
		try
		{
    		Connection con = DriverManager.getConnection(connectionStr, connectionUserStr, connectionPassStr);
    		
    		PreparedStatement pstmt = con.prepareStatement("SELECT account_ID, username, SunStart, MonStart, TueStart, WedStart, ThuStart, FriStart, SatStart, SunEnd, MonEnd, TueEnd, WedEnd, ThuEnd, FriEnd, SatEnd FROM employeeschedule INNER JOIN accounts ON schedule_ID = accounts.account_ID WHERE username LIKE ?");
    		query = "%" + query + "%";
    		pstmt.setString(1, query);
    		ResultSet rs = pstmt.executeQuery();
    		
			while (rs.next()) {
				int account_ID = rs.getInt("account_ID");
				String username = rs.getString("username");
				
				StaffModel queryStaff = new StaffModel(account_ID, username);
				queryResult.add(queryStaff);
			}
    		
    		con.close();
    		pstmt.close();
    		rs.close();
    		
    		return queryResult;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
