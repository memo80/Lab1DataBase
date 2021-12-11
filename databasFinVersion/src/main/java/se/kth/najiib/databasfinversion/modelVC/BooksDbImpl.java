/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.najiib.databasfinversion.modelVC;

import java.sql.*;
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
    private Connection con;

    public BooksDbImpl() {
       // books = Arrays.asList(DATA);
        books=new ArrayList<>();
        con=null;
    }


    @Override
    public boolean connect(String database) throws BooksDbException {
        String user = "root";// user name
        String pwd = "123123asd"; // password
       // String database = "dblibrary"; // the name of the specific database
        String server = "jdbc:mysql://localhost:3306/" + database;


        try {
            con = DriverManager.getConnection(server, user, pwd);
            System.out.println("Connection Established!");


            //executeQuery(con, "SELECT * FROM dblibrary.book");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR "+ e.getMessage());
        }
        return false;
    }


    @Override
    public void disconnect() throws BooksDbException {
        // mock implementation
        try{
            con.close();
            System.out.println("Disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Rere");
        }
    }

    @Override
    public List<Book> searchBooksByTitle(String searchTitle)
            throws BooksDbException {
        List<Book> result = new ArrayList<>();
        searchTitle = searchTitle.toLowerCase();
        ResultSet rs;
        try{
            String query = "SELECT * FROM Book " + "WHERE title LIKE '%"+searchTitle+"%'";
            PreparedStatement stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                /*System.out.println("IN WHILELOOP");
                System.out.println(""+ rs.getString("title"));
                System.out.println(""+ rs.getString("ISBN"));
                System.out.println(""+ rs.getString("genre")); TA BORT SENARE!!!*/
                String title1 = rs.getString("title");
                String isbn = rs.getString("ISBN");
                String genre = rs.getString("genre");
                int rating=rs.getInt("rating");


                result.add(new Book(title1, isbn, Genre.valueOf(genre),rating));
            }
            //System.out.println("BYE");TA BORT SENARE!!!
        }catch(SQLException e){
            System.out.println("ERROR " + e.getMessage() + e.getErrorCode());

        }
        //System.out.println("BYE2"); TA BORT SENARE!!!

        return result;

    }

    public List<Book> searchBooksByISBN(String searchIsbn)
            throws BooksDbException{
        List<Book> result = new ArrayList<>();
        searchIsbn = searchIsbn.toLowerCase();
        ResultSet rs;
        try{
            String query = "SELECT * FROM Book " + "WHERE ISBN LIKE '%"+searchIsbn+"%'";
            PreparedStatement stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                /*System.out.println("IN WHILELOOP");
                System.out.println(""+ rs.getString("title"));
                System.out.println(""+ rs.getString("ISBN"));
                System.out.println(""+ rs.getString("genre")); TA BORT SENARE!!!*/
                String title1 = rs.getString("title");
                String isbn = rs.getString("ISBN");
                String genre = rs.getString("genre");
                int rating=rs.getInt("rating");


                result.add(new Book(title1, isbn, Genre.valueOf(genre),rating));
            }
            //System.out.println("BYE");TA BORT SENARE!!!
        }catch(SQLException e){
            System.out.println("ERROR " + e.getMessage() + e.getErrorCode());

        }
        return result;
    }

    @Override
    public List<Book> searchBooksByGenre(String searchGenre) throws BooksDbException {
        List<Book> result= new ArrayList<>();
        searchGenre= searchGenre.toUpperCase();

        try
        {
            String sql= "SELECT * FROM Book " + "WHERE Genre LIKE '%"+searchGenre+"%'";
            PreparedStatement prestmt=con.prepareStatement(sql);
            ResultSet rs= prestmt.executeQuery();

            while(rs.next())
            {
                String title=rs.getString("title");
                String isbnq=rs.getString("ISBN");
                String genree=rs.getString("Genre");
                int betyg= rs.getInt("rating");
                Book book=new Book(title,isbnq,Genre.valueOf(genree),betyg);
                result.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("WRONG"+e.getMessage());
        }
        return result;
    }

    @Override
    public List<Book> searchBooksByRating(int searchRating) throws BooksDbException {
        List<Book> result = new ArrayList<>();

        ResultSet rs;
        try{
            String query = "SELECT * FROM Book " + "WHERE rating LIKE '%"+searchRating+"%'";
            PreparedStatement stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                /*System.out.println("IN WHILELOOP");
                System.out.println(""+ rs.getString("title"));
                System.out.println(""+ rs.getString("ISBN"));
                System.out.println(""+ rs.getString("genre")); TA BORT SENARE!!!*/
                String title1 = rs.getString("title");
                String isbn = rs.getString("ISBN");
                String genre = rs.getString("genre");
                int rate=rs.getInt("rating");


                result.add(new Book(title1, isbn, Genre.valueOf(genre),rate));
            }
            //System.out.println("BYE");TA BORT SENARE!!!
        }catch(SQLException e){
            System.out.println("ERROR " + e.getMessage() + e.getErrorCode());

        }
        return result;
    }


    public void addBook(Book book)
    {
        try
        {
            String sql="INSERT INTO book(ISBN,Title,Genre,rating)"+ "VALUES(?,?,?,?)";
            PreparedStatement preStmt=con.prepareStatement(sql);
            preStmt.setString(1,book.getIsbn());
            preStmt.setString(2,book.getTitle());
            preStmt.setString(3,book.getGenre().toString());
            preStmt.setString(4,Integer.toString(book.getRating()));

            preStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("WRONG "+ e.getMessage());
        }
    }
}