package se.kth.najiib.databasdb.modelVC;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A mock implementation of the BooksDBInterface interface to demonstrate how to
 * use it together with the user interface.
 * <p>
 * Your implementation must access a real database.
 *
 * @author anderslm@kth.se
 */

public class BooksDbImpl implements BooksDbInterface {

    private final List<Book> books;

    public BooksDbImpl() {
        books = Arrays.asList(DATA);
    }

    @Override
    public boolean connect(String database) throws BooksDbException, SQLException {
        // mock implementation
        /*String user = "root";// user name
        String pwd = "123123asd"; // password
        System.out.println(user + ", *********");


        String server
                = "jdbc:mysql://localhost:3306/" + database
                + "?UseClientEnc=UTF8";
        Connection con = null;
        try {
           // Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pwd);
            System.out.println("Connected!");
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                    return false;
                }
            } catch (SQLException e) {
            }
        }*/
        return true;
    }

    @Override
    public void disconnect() throws BooksDbException {
        // mock implementation
    }

    @Override
    public List<Book> searchBooksByTitle(String searchTitle)
            throws BooksDbException {
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query to a database (not load all books from db)
        List<Book> result = new ArrayList<>();
        searchTitle = searchTitle.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                result.add(book);
            }
        }
        return result;
    }
    @Override
    public List<Book> searchBooksByISBN(String searchISBN)
            throws BooksDbException {
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query to a database (not load all books from db)
        List<Book> result = new ArrayList<>();
        searchISBN = searchISBN.toLowerCase();
        for (Book book : books) {
            if (book.getIsbn().toLowerCase().contains(searchISBN)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> searchBooksByAuthor(String searchAuthor)
            throws BooksDbException {
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query to a database (not load all books from db)
        List<Book> result = new ArrayList<>();
        searchAuthor = searchAuthor.toLowerCase();
        for (Book book : books) {
            if (book.getAuthors().toString().toLowerCase().contains(searchAuthor)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBookByRating(int searchRating) throws BooksDbException
    {
        if(searchRating<=5)
        {
            System.out.println("Input Number too high (Only 1-5)");
        }

        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getRating()==searchRating) {
                result.add(book);
            }
        }
        return result;

    }
    public List<Book> searchBookByGenre(Genre genre) throws BooksDbException
    {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equals(genre)) {
                result.add(book);
            }
        }
        return result;
    }


   /* @Override
    public List<Book> addBookToDatabase(Book book) {

    }

    @Override
    public List<Book> addAuthorToDatabase(Author author) {

    }*/





   private static final Book[] DATA = {
            new Book(1, "123456789", "Databases Illuminated", new Date(2018, 1, 1),Genre.COMEDY,3),
            new Book(2, "123456789", "Dark Databases", new Date(1990, 1, 1),Genre.FICTION,2),
            new Book(3, "456789012", "The buried giant", new Date(2000, 1, 1),Genre.ROMANCE,5),
            new Book(4, "567890123", "Never let me go", new Date(2000, 1, 1),Genre.BIOGRAPHY,1),
            new Book(5, "678901234", "The remains of the day", new Date(2000, 1, 1),Genre.CRIME,3),
            new Book(6, "234567890", "Alias Grace", new Date(2000, 1, 1),Genre.CRIME,1),
            new Book(7, "345678901", "The handmaids tale", new Date(2010, 1, 1),Genre.HORROR,1),
            new Book(8, "345678901", "Shuggie Bain", new Date(2020, 1, 1),Genre.DRAMA,5),
            new Book(9, "123456789", "Microserfs", new Date(2000, 1, 1),Genre.HORROR,1),
    };



}