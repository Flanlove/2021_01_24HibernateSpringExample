package model;
// Generated 2021�~1��24�� �W��11:34:10 by Hibernate Tools 5.4.21.Final

/**
 * Book generated by hbm2java
 */
public class Book implements java.io.Serializable {

	private int bookId;
	private String title;
	private String author;
	private float price;

	public Book() {
	}

	public Book(int bookId, String title, String author, float price) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.price = price;
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
