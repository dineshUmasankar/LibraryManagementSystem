package org.psu.dUmasankar.LMSModel;

import java.sql.Date;

public class BookModel {
	private int id = -1;
	private String title = null;
	private String author = null;
	private String category = null;
	
	private Date holdUntil = null;
	private Date borrowUntil = null;
	
	public BookModel(int id, String title, String author, String category, Date holdUntil, Date borrowUntil)
	{
		this.setId(id);
		this.setTitle(title);
		this.setAuthor(author);
		this.setCategory(category);
		this.setHoldUntil(holdUntil);
		this.setBorrowUntil(borrowUntil);
	}
	
	public BookModel() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public Date getHoldUntil() {
		return holdUntil;
	}

	public void setHoldUntil(Date holdUntil) {
		this.holdUntil = holdUntil;
	}

	public Date getBorrowUntil() {
		return borrowUntil;
	}

	public void setBorrowUntil(Date borrowUntil) {
		this.borrowUntil = borrowUntil;
	}

	public String toString()
	{
		return "Title: " + title + " Author: " + author + " Category: " + category;
	}
}
