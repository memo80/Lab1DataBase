package se.kth.najiib.databasdb.modelVC;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Representation of a book.
 *
 * @author anderslm@kth.se
 */
public class Book {

    private final Genre genre;
    private final int rating;
    private int bookId;
    private String isbn; // should check format
    private String title;
    private Date published; // Datum när den kom ut
    private ArrayList<Author> authors;
    private Author author;

    // private String storyLine = "";
    // TODO:
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    public Book(int bookId, String isbn, String title, Date published, Genre genre,int rating){//,Author author) {
        this.bookId = bookId; // inte NÖDVÄNDIGT
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.genre=genre;
        this.rating=rating;
       // this.author=author;
        //this.authors = new ArrayList<>();
    }

   /* public Book(String isbn, String title, Date published,Author author)
    {
        this(-1, isbn, title, published,author);
    }*/

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public Author getAuthor() {
        return author;
    }

    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Date getPublished() { return published; }
    //public String getStoryLine() { return storyLine; }

    /*public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }*/

    public Genre getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return title + ", " + isbn + ", " + published.toString();
    }
}