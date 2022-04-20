package se.kth.najiib.lab1mysql.modelVC;

import java.sql.*;
import java.util.ArrayList;
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
    private Connection con;
    private PreparedStatement preStmt;//ska vara inne i metoderna
    private ResultSet rts; // ska vara inne i metoderna

    public BooksDbImpl() {
        con=null;
        preStmt=null;
        rts=null;
    }

    /**
     * This method connects to database
     * @return returns true or false if a connection is etablished or not
     * @throws BooksDbException
     */
    @Override
    public boolean connect(String database) throws BooksDbException {
        String user = "Clerk"; // user name
        String pwd = "123123asd"; // password
        String server = "jdbc:mysql://localhost:3306/" + database;

        try {
            con = DriverManager.getConnection(server, user, pwd);
            System.out.println("Connection Established!");
            return true;
        } catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        }
    }
    /**
     * this method disconnect the database
     * @throws BooksDbException
     */
    @Override
    public void disconnect() throws BooksDbException {
        // mock implementation
        try{
            con.close();
            System.out.println("Disconnected");
        } catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        }
    }
    /**
     * search after book by title
     * @param searchTitle which title to search
     * @return list of books
     * @throws BooksDbException
     */
    @Override
    public List<Book> searchBooksByTitle(String searchTitle) throws BooksDbException {
        List<Book> result = new ArrayList<>();
        searchTitle = searchTitle.toLowerCase();

        try{
            String query = "SELECT * "+ "FROM book " + "WHERE book.title LIKE '%"+searchTitle+"%'";

            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){

                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published")));
            }
            result= AuthorsInformation(result);
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
               // preStmt.close();
                rts.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return result;
    }

    /**
     * search after book by ISBN
     * @param searchIsbn which ISBN to search
     * @return list of books
     * @throws BooksDbException
     */
    public List<Book> searchBooksByISBN(String searchIsbn) throws BooksDbException{
        List<Book> result = new ArrayList<>();
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query with to a database.
        try{

            String query = "SELECT * "+ "FROM book " + "WHERE book.ISBN LIKE '%"+searchIsbn+"%'";
            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published")));
            }
            result= AuthorsInformation(result);
            return result;
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
           try {
                preStmt.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
    }

    /**
     * search after book by author
     * @param searchAuthor which author to search
     * @return list of books
     * @throws BooksDbException
     */
    public List<Book> searchBooksByAuthor(String searchAuthor) throws BooksDbException, SQLException {
        List<Book> result = new ArrayList<>();
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query with to a database.
        try{
            String query = "SELECT * "+ "FROM book " +
                    "NATURAL JOIN writtenby " +
                    "NATURAL JOIN author "+
                    "WHERE author.name LIKE '%"+searchAuthor+"%'";

           /* String sql="SELECT * " + " FROM writtenby,book,author " +
                    " WHERE writtenby.authorID IN (SELECT authorID FROM author WHERE name LIKE '%"+searchAuthor+"%') " + " AND writtenby.authorID = author.authorID" +
                   " AND writtenby.ISBN = book.ISBN";

            */

            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published")));
            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {

                preStmt.close();

        }
        result= AuthorsInformation(result);
        return result;
    }

    /**
     * search after book by genre
     * @param searchGenre which title to search
     * @return list of books
     * @throws BooksDbException
     */
    @Override
    public List<Book> searchBooksByGenre(String searchGenre) throws BooksDbException {
        List<Book> result= new ArrayList<>();
        searchGenre= searchGenre.toUpperCase();
        try
        {
            String query = "SELECT * "+ "FROM book " + "WHERE book.Genre LIKE '%"+searchGenre+"%'";
            preStmt=con.prepareStatement(query);
            rts= preStmt.executeQuery();

            while(rts.next())
            {
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre")),
                        rts.getInt("Rating"),
                        rts.getString("published")));

            }
        } catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                preStmt.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
        result= AuthorsInformation(result);
        return result;
    }

    /**
     * search after book by rating
     * @param searchRating which rating to search
     * @return list of books
     * @throws BooksDbException
     */
    @Override
    public List<Book> searchBooksByRating(int searchRating) throws BooksDbException {
        List<Book> result = new ArrayList<>();

        try{
            String query = "SELECT * "+ "FROM book "+ "WHERE book.Rating LIKE '%"+searchRating+"%'";

            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published")));
            }
            result= AuthorsInformation(result);
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                preStmt.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return result;
    }

    /**
     * this method adds a book to database
     * @param book the book that will be added
     * @throws BooksDbException
     */
    @Override
    public void addBookToDb(Book book) throws BooksDbException {
        if(book != null)
        {
            try {
                String sql = "INSERT INTO book(ISBN, title, genre, Rating,published)" + "VALUES(?,?,?,?,?)";
                con.setAutoCommit(false);
                preStmt = con.prepareStatement(sql);
                preStmt.setString(1, book.getIsbn());
                preStmt.setString(2, book.getTitle());
                preStmt.setString(3, book.getGenre().toString());
                preStmt.setString(4, Integer.toString(book.getRating()));
                preStmt.setString(5, book.getPublished());
                preStmt.executeUpdate();
                con.commit();
            } catch(SQLException ex){
                if(con==null)throw new BooksDbException(ex.getMessage(),ex);
                {
                    try {
                        con.rollback();
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                }
            } finally {
                try {
                    preStmt.close();
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        }
    }
    /**
     * this method adds an author to a book in database
     //* @param author the author we add
     * @throws BooksDbException
     */
    @Override
    public void addAuthorToDb(String authorIDs, String fullName ,String dob,String ISBN) throws BooksDbException {
        try {
                con.setAutoCommit(false);
                String query = "INSERT INTO author (authorID, name,dob)" + "VALUES(?, ?,?)";
                preStmt = con.prepareStatement(query);
                preStmt.setString(1, authorIDs);
                preStmt.setString(2, fullName);
                preStmt.setString(3, dob);
                preStmt.executeUpdate();
                String query1 = "INSERT INTO writtenby(authorID, ISBN) VALUES(?,?);";
                preStmt = con.prepareStatement(query1);
                preStmt.setString(1, authorIDs);
                preStmt.setString(2, ISBN);
                preStmt.executeUpdate();
                con.commit();
            } catch(SQLException ex){
                if(con==null)throw new BooksDbException(ex.getMessage(),ex);
                {
                    try {
                        con.rollback();
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                }
            } finally {
           try {
                    preStmt.close();
                    con.setAutoCommit(true);
            } catch (SQLException e) {
                    e.getMessage();
                }
            }
    }

    /**
     * this method gets information about an author
     //* @param List<Book> books which author/authors connected to a book
     * @throws BooksDbException
     */

    private List<Book> AuthorsInformation(List<Book> books) throws BooksDbException { //Latest added *
        try {
            for(Book b:books)
            {
                con.setAutoCommit(false);
                ArrayList<Author> author=new ArrayList<>();
                String query="SELECT * FROM author WHERE authorID IN(SELECT authorID FROM writtenBy WHERE ISBN = '" + b.getIsbn().toString()+ "')";
                preStmt=con.prepareStatement(query);
                rts=preStmt.executeQuery();
                while (rts.next())
                {
                    author.add(new Author(rts.getString("authorID"),rts.getString("name"),rts.getString("dob")));
                }
                b.setAuthors(author);
            }
            con.commit();
        } catch(SQLException ex){
            if(con==null)throw new BooksDbException(ex.getMessage(),ex);
            {
                try {
                    con.rollback();
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        } finally {
            try {
                con.setAutoCommit(true);
                preStmt.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return books;
    }
}