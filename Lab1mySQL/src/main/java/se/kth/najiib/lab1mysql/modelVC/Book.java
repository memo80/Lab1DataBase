package se.kth.najiib.lab1mysql.modelVC;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Representation of a book.
 *
 * @author anderslm@kth.se
 */
public class Book {
    private String isbn; // should check format
    private String title;
    private String published;

    private int rating;
    private List<Author> authors;
    private Author author;

    private Genre genre;
    // TODO:
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    public Book(String title, String isbn, Genre genre, int rating, String published,Author author)

    {

        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.published = published;
        this.genre=genre;
        this.rating=rating;
        this.authors =new ArrayList<>();
        this.addAuthor(author);

    }
    /**this method adds author
     * @param authors
     */
    public void addAuthor(Author authors)
    {
        this.authors.add(authors);
    }
    /**this method obtains ISBN of a book
     *
     * @return ISBN of a book
     */
    public String getIsbn() { return isbn; }
    /**this method obtains title of a book
     *
     * @return title of a book
     */
    public String getTitle() { return title; }
    private static final Pattern ISBN_PATTERN = Pattern.compile("^[0-9]{3}$");
    public static boolean isValidISBN(String isbn){
        return ISBN_PATTERN.matcher(isbn).matches();
    }
    /** this method obtains a copy of a list of authors
     *
     * @return list of authors (copy)
     */
    public Author getAuthor() {
        return author;
    }
    public List<Author> getAuthors() {

        return List.copyOf(authors);
    }
    /**this method obtains rating of a book
     *
     * @return rating of a book
     */
    public int getRating() {
        return rating;
    }
    /**this method obtains genre of a book
     *
     * @return genre of a book
     */
    public Genre getGenre() {
        return genre;
    }
    /**this method obtains published date of a book
     *
     * @return published date of a book
     */
    public String getPublished() {
        return published;
    }

    @Override
    public String toString() {
        return "Book{" +

                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", published=" + published +
                ", rating=" + rating +
                ", author=" + authors +
                ", genre=" + genre +
                '}';
    }






}
