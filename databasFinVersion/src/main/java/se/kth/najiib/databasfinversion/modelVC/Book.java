package se.kth.najiib.databasfinversion.modelVC;

import java.sql.Date;
import java.util.regex.Pattern;

/**
 * Representation of a book.
 *
 * @author anderslm@kth.se
 */
public class Book {

    private int bookId;
    private String isbn; // should check format
    private String title;
    private Date published;
    private String storyLine = "";
    private int rating;
 private Genre genre;
    // TODO:
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    public Book(String title,String isbn,Genre genre, int rating) {
        //this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;

       // this.published = published;
        this.genre=genre;
        this.rating=rating;
       this.published=published;
    }

    //public Book(String isbn, String title,Genre genre) {
      //  this( isbn, title, genre);
    //}

    private static final Pattern ISBN_PATTERN = Pattern.compile("^[0-9]{3}$");
    public static boolean isValidISBN(String isbn){
        return ISBN_PATTERN.matcher(isbn).matches();
    }


    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    //public Date getPublished() { return published; }
    public String getStoryLine() { return storyLine; }

    public int getRating() {
        return rating;
    }

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }

    @Override
    public String toString() {
        return title + ", " + isbn + ", " + genre+" ,"+ rating;
    }

    public Genre getGenre() {
        return genre;
    }
}
