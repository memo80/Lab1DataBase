package se.kth.najiib.databasfinversion.modelVC;
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
        addAuthor(author);

    }

    public void addAuthor(Author authors)
    {
        this.authors.add(authors);
    }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    private static final Pattern ISBN_PATTERN = Pattern.compile("^[0-9]{3}$");
    public static boolean isValidISBN(String isbn){
        return ISBN_PATTERN.matcher(isbn).matches();
    }
    public Author getAuthor() {
        return author;
    }
    public List<Author> getAuthors() {

        return List.copyOf(authors);
    }
    public int getRating() {
        return rating;
    }
    public Genre getGenre() {
        return genre;
    }

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
