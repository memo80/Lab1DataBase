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
    private PreparedStatement preStmt;
    private ResultSet rts;

    public BooksDbImpl() {
        //books = Arrays.asList(DATA);
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
            String query = "SELECT * "+
                    "FROM book " +
                    "NATURAL JOIN writtenby " + // relation i databasen
                    "NATURAL JOIN author "+
                    "WHERE book.title LIKE '%"+searchTitle+"%'";

            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){

                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorId"),
                                rts.getString("name"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));

            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try{
                preStmt.close();
              //  rts.close();

            }
            catch (SQLException e) {
                e.printStackTrace();
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

            String query = "SELECT * "+
                    "FROM book " +
                    "NATURAL JOIN writtenby " +
                    "NATURAL JOIN author "+
                    "WHERE book.ISBN LIKE '%"+searchIsbn+"%'";
            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorId"),
                                rts.getString("name"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));
            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                preStmt.close();
            //rts.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    /**
     * search after book by author
     * @param searchAuthor which author to search
     * @return list of books
     * @throws BooksDbException
     */
    public List<Book> searchBooksByAuthor(String searchAuthor) throws BooksDbException{
        List<Book> result = new ArrayList<>();
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query with to a database.
        try{

            String query = "SELECT * "+
                    "FROM book " +
                    "NATURAL JOIN writtenby " +
                    "NATURAL JOIN author "+
                    "WHERE author.name LIKE '%"+searchAuthor+"%'";
             preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorId"),
                                rts.getString("name"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));
            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                preStmt.close();
                //rts.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

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
            String query = "SELECT * "+
                    "FROM book " +
                    "NATURAL JOIN writtenby " +
                    "NATURAL JOIN author "+
                    "WHERE book.Genre LIKE '%"+searchGenre+"%'";
            preStmt=con.prepareStatement(query);
            rts= preStmt.executeQuery();

            while(rts.next())
            {
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre")),
                        rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorID"),
                                rts.getString("name"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));

            }

        } catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                preStmt.close();
               // rts.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    /**
     * search after book by rating
     * @param searchRating which rating to search
     * @return list of books
     * @throws BooksDbException
     */
    @Override
    public List<Book> searchBooksByRating(int searchRating) throws BooksDbException,SQLException {
        List<Book> result = new ArrayList<>();


        try{
            String query = "SELECT * "+ "FROM book " + "NATURAL JOIN writtenby " + "NATURAL JOIN author "+ "WHERE book.Rating LIKE '%"+searchRating+"%'";

            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorID"),
                                rts.getString("name"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));


            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            preStmt.close();
           // rts.close();
        }
        return result;
    }

    /**
     * this method adds a book to database
     * @param book the book that will be added
     * @throws BooksDbException
     */
    @Override
    public void addBookToDb(Book book) throws BooksDbException,SQLException {
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

                String sql1 = "INSERT INTO author(authorID, name,dob) VALUES(?,?,?);";
                preStmt = con.prepareStatement(sql1);
                preStmt.setString(1, book.getAuthor().getAuthorIDs());
                preStmt.setString(2, book.getAuthor().getFullName());
                preStmt.setString(3, book.getAuthor().getDob());
                preStmt.executeUpdate();

                String query1 = "INSERT INTO writtenby(authorID, ISBN) VALUES(?,?);";
                preStmt = con.prepareStatement(query1);
                preStmt.setString(1, book.getAuthor().getAuthorIDs());
                preStmt.setString(2, book.getIsbn());
                preStmt.executeUpdate();
                con.commit();
            } catch(SQLException ex){
                if(con==null)throw new BooksDbException(ex.getMessage(),ex);
                {
                    con.rollback();
                }
                preStmt.close();

            } finally {

                con.setAutoCommit(true);
            }
        }
    }


    /**
     * this method adds an author to a book in database
     * @param author the author we add
     * @throws BooksDbException
     */
    @Override
    public void addAuthorToDb(Author author) throws BooksDbException, SQLException {
        if(author == null) throw new BooksDbException("Cannot add"); {

            try {
                con.setAutoCommit(false);
                String query = "INSERT INTO author (authorID, name,dob)" + "VALUES(?, ?,?)";
                preStmt = con.prepareStatement(query);
                preStmt.setString(1, author.getAuthorIDs());
                preStmt.setString(2, author.getFullName());
                preStmt.setString(3, author.getDob());
                preStmt.executeUpdate();
                String query1 = "INSERT INTO writtenby(authorID, ISBN) VALUES(?,?);";
                preStmt = con.prepareStatement(query1);
                preStmt.setString(1, author.getAuthorIDs());
                preStmt.setString(2, author.getISBN());
                preStmt.executeUpdate();
                con.commit();
            } catch(SQLException ex){
                if(con==null)throw new BooksDbException(ex.getMessage(),ex);
                {
                    con.rollback();
                }
                preStmt.close();
            } finally {
                con.setAutoCommit(true);
            }

        }

    }


}
