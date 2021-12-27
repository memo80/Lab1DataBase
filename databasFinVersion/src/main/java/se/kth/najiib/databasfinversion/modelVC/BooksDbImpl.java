/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.najiib.databasfinversion.modelVC;

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

   private List<Book> books;
    private Connection con;
    private PreparedStatement preStmt;
    private ResultSet rts;

    public BooksDbImpl() {
        //books = Arrays.asList(DATA);
        books=new ArrayList<>();
        con=null;
        preStmt=null;
        rts=null;
    }


    @Override
    public boolean connect(String database) throws BooksDbException {
        String user = "root"; // user name
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

    @Override
    public List<Book> searchBooksByTitle(String searchTitle) throws BooksDbException {
        List<Book> result = new ArrayList<>();
        searchTitle = searchTitle.toLowerCase();
        try{
            String query = "SELECT * "+
                    "FROM book " +
                    "NATURAL JOIN writtenby " +
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
                                rts.getString("authorName"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));

            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try{
                rts.close();

            }
            catch (SQLException e)
            {
                throw new BooksDbException(e.getMessage(),e);
            }

        }

        return result;
    }

    public List<Book> searchBooksByISBN(String searchIsbn) throws BooksDbException{

        ArrayList<Book> books = new ArrayList<>();
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
            PreparedStatement stmt = con.prepareStatement(query);
            rts = stmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorId"),
                                rts.getString("authorName"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));
            }
        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                rts.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<Book> searchBookByAuthor(String searchAuthor) throws BooksDbException {
        List<Book> result = new ArrayList<>();
        try{
            String query = "SELECT * "+
                    "FROM book " +
                    "NATURAL JOIN writtenby " +
                    "NATURAL JOIN author "+
                    "WHERE book.authorName LIKE '%"+searchAuthor+"%'";
            preStmt = con.prepareStatement(query);
            rts = preStmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorId"),
                                rts.getString("authorName"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));
            }

        }catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                rts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


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
                        new Author(rts.getString("authorId"),
                                rts.getString("authorName"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));

            }

        } catch(SQLException ex){
            throw new BooksDbException(ex.getMessage(),ex);
        } finally {
            try {
                rts.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<Book> searchBooksByRating(int searchRating) throws BooksDbException,SQLException {
        List<Book> result = new ArrayList<>();
        

        try{
            String query = "SELECT * "+ "FROM book " + "NATURAL JOIN writtenby " + "NATURAL JOIN author "+ "WHERE book.Rating LIKE '%"+searchRating+"%'";

            PreparedStatement stmt = con.prepareStatement(query);
            rts = stmt.executeQuery();
            while(rts.next()){
                result.add(new Book(rts.getString("title"),
                        rts.getString("ISBN"),
                        Genre.valueOf( rts.getString("genre"))
                        ,rts.getInt("Rating"),
                        rts.getString("published"),
                        new Author(rts.getString("authorId"),
                                rts.getString("authorName"),
                                rts.getString("dob"),
                                rts.getString("ISBN"))));


                }
        }catch(SQLException ex){
           throw new BooksDbException(ex.getMessage(),ex);
        } finally {

                rts.close();

        }

        return result;
    }


    @Override
    public void addBookToDb(Book book) throws BooksDbException,SQLException {
        if(book != null)
        {
            String sql = "";
            String query1 = "";

            try {

                sql = "INSERT INTO book(ISBN, title, genre, Rating,authorID ,authorName, published,dob)" + "VALUES(?,?,?,?,?,?,?,?)";
                con.setAutoCommit(false);
                preStmt = con.prepareStatement(sql);
                preStmt.setString(1, book.getIsbn());
                preStmt.setString(2, book.getTitle());
                preStmt.setString(3, book.getGenre().toString());
                preStmt.setString(4, Integer.toString(book.getRating()));
                preStmt.setString(5, book.getAuthor().getAuthorIDs());
                preStmt.setString(6, book.getAuthor().getFullName());
                preStmt.setString(7, book.getPublished());
                preStmt.setString(8, book.getAuthor().getDob());
                preStmt.executeUpdate();

                  sql = "INSERT INTO author(authorID, name,dob) VALUES(?,?,?);";
                  preStmt = con.prepareStatement(sql);
                  preStmt.setString(1, book.getAuthor().getAuthorIDs());
                  preStmt.setString(2, book.getAuthor().getFullName());
                    preStmt.setString(3, book.getAuthor().getDob());
                  preStmt.executeUpdate();

                  query1 = "INSERT INTO writtenby(authorID, ISBN) VALUES(?,?);";
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

            } finally {

                    con.setAutoCommit(true);
            }
        }
    }

    @Override
    public void addAuthorToDb(Author author) throws BooksDbException, SQLException {
        if(author == null) throw new BooksDbException("Cannot add"); {
            String query = "INSERT INTO author (authorID, name,dob)" + "VALUES(?, ?,?)";
            try {
                con.setAutoCommit(false);
                preStmt = con.prepareStatement(query);
                preStmt.setString(1, author.getAuthorIDs());
                preStmt.setString(2, author.getFullName());
                preStmt.setString(3, author.getDob());
                preStmt.executeUpdate();
                query = "INSERT INTO writtenby(authorID, ISBN) VALUES(?,?);";
                preStmt = con.prepareStatement(query);
                preStmt.setString(1, author.getAuthorIDs());
                preStmt.setString(2, author.getISBN());
                preStmt.executeUpdate();
                con.commit();
            } catch(SQLException ex){
               if(con==null)throw new BooksDbException(ex.getMessage(),ex);
               {
                   con.rollback();
               }
            } finally {
                con.setAutoCommit(true);

            }

        }

    }


    }



