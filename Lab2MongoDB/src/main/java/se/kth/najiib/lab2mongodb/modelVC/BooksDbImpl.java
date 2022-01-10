package se.kth.najiib.lab2mongodb.modelVC;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;


import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
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

    private MongoDatabase base;
    private MongoClient mongo;

    public BooksDbImpl() {
       
        books = new ArrayList<>();
        base = null;
        mongo=null;
    }


    @Override
    public boolean connect(String database) throws BooksDbException {
        String user = "NAD"; // user name
        String pwd = "123123asd"; // password
        String port = "@cluster-shard-00-00.l06kt.mongodb.net:27017,cluster-shard-00-01.l06kt.mongodb.net:27017,cluster-shard-00-02.l06kt.mongodb.net:27017";

        try {
            mongo = new MongoClient(new MongoClientURI("mongodb://" + user + ":" + pwd + port + "/" + database + "?ssl=true&replicaSet=atlas-qclrvl-shard-0&authSource=admin&retryWrites=true&w=majority"));
            base = mongo.getDatabase(database);
            return true;
        } catch (MongoException ex) {
            throw new BooksDbException(ex.getMessage(), ex);
        }
    }


    @Override
    public void disconnect() throws BooksDbException {
        // mock implementation
        try {
            mongo.close();
            System.out.println("Disconnected");
        } catch (MongoException ex) {
            throw new BooksDbException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Book> searchBooksByTitle(String searchTitle) throws BooksDbException {
        List<Book> result = new ArrayList<>();
        //searchTitle = searchTitle.toLowerCase();

        try {

            MongoCollection<Document> collection = base.getCollection("book");
            Document query = new Document();
            //  collection.createIndex(Indexes.ascending("Rating"));FUNKAR
            query.append("$regex", searchTitle);
            query.append("$options", "i");
            System.out.println(4);
            Document findQuery1 = new Document();
            findQuery1.append("title", query);

            FindIterable find = collection.find(findQuery1);

            for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext(); ) {
                Document doc = cursor.next();
                String title=doc.getString("title");
                String isbn=doc.getString("ISBN");
                Genre genre=Genre.valueOf(doc.getString("genre"));
                int rating=Integer.parseInt(doc.getString("Rating"));
                String name=doc.getString("authorName");
                String authId=doc.getString("authorID");
                String published=doc.getString("published");
                String dob=doc.getString("dob");

                result.add(new Book(title,isbn,genre,rating,published,new Author(authId,name,dob,isbn)));



//System.out.println(new Author(doc.getString("authorID"),doc.getString("authorName"),doc.getString("dob"),doc.getString("ISBN")));

                //new Book("qasf","123",Genre.DRAMA,4,"12-12-1212")

            }
        }catch (Exception e)
        {
            throw new BooksDbException(e.getMessage(),e);
        }






        return result;
    }




    public List<Book> searchBooksByISBN(String searchIsbn) throws BooksDbException{

        ArrayList<Book> books = new ArrayList<>();
        List<Book> result = new ArrayList<>();


        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query with to a database.

        try {



            MongoCollection<Document> collection = base.getCollection("book");
            Document query = new Document();
            //  collection.createIndex(Indexes.ascending("Rating"));FUNKAR
            query.append("$regex", searchIsbn);
            query.append("$options", "i");
            System.out.println(4);
            Document findQuery1 = new Document();
            findQuery1.append("ISBN", query);

            FindIterable find = collection.find(findQuery1);

            for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext(); ) {
                Document doc = cursor.next();
                String title = doc.getString("title");
                String isbn = doc.getString("ISBN");
                Genre genre = Genre.valueOf(doc.getString("genre"));
                int rating = Integer.parseInt(doc.getString("Rating"));
                String name = doc.getString("authorName");
                String authId = doc.getString("authorID");
                String published = doc.getString("published");
                String dob = doc.getString("dob");

                result.add(new Book(title, isbn, genre, rating, published, new Author(authId, name, dob, isbn)));

            }
        }catch(Exception ex){
            throw new BooksDbException(ex.getMessage(),ex);
        }

        return result;
    }

    @Override
    public List<Book> searchBookByAuthor(String searchAuthor) throws BooksDbException {
        List<Book> result = new ArrayList<>();
        try{
            MongoCollection<Document> collection = base.getCollection("book");
            Document query = new Document();
            //  collection.createIndex(Indexes.ascending("Rating"));FUNKAR
            query.append("$regex", searchAuthor);
            query.append("$options", "i");
            System.out.println(4);
            Document findQuery1 = new Document();
            findQuery1.append("authorName", query);

            FindIterable find = collection.find(findQuery1);

            for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext(); ) {
                Document doc = cursor.next();
                String title = doc.getString("title");
                String isbn = doc.getString("ISBN");
                Genre genre = Genre.valueOf(doc.getString("genre"));
                int rating = Integer.parseInt(doc.getString("Rating"));
                String name = doc.getString("authorName");
                String authId = doc.getString("authorID");
                String published = doc.getString("published");
                String dob = doc.getString("dob");

                result.add(new Book(title, isbn, genre, rating, published, new Author(authId, name, dob, isbn)));

            }

        }catch(Exception ex){
            throw new BooksDbException(ex.getMessage(),ex);
        }
        return result;
    }


    @Override
    public List<Book> searchBooksByGenre(String searchGenre) throws BooksDbException {
        List<Book> result= new ArrayList<>();
        searchGenre= searchGenre.toUpperCase();

        try
        {

            MongoCollection<Document> collection = base.getCollection("book");
            Document query = new Document();
            //  collection.createIndex(Indexes.ascending("Rating"));FUNKAR
            query.append("$regex", searchGenre);
            query.append("$options", "i");
            System.out.println(4);
            Document findQuery1 = new Document();
            findQuery1.append("genre", query);

            FindIterable find = collection.find(findQuery1);

            for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext(); ) {
                Document doc = cursor.next();
                String title = doc.getString("title");
                String isbn = doc.getString("ISBN");
                Genre genre = Genre.valueOf(doc.getString("genre"));
                int rating = Integer.parseInt(doc.getString("Rating"));
                String name = doc.getString("authorName");
                String authId = doc.getString("authorID");
                String published = doc.getString("published");
                String dob = doc.getString("dob");

                result.add(new Book(title, isbn, genre, rating, published, new Author(authId, name, dob, isbn)));

            }

        } catch(Exception ex){
            throw new BooksDbException(ex.getMessage(),ex);
        }


        return result;
    }

    @Override
    public List<Book> searchBooksByRating(String searchRating) throws BooksDbException,SQLException {
        List<Book> result = new ArrayList<>();


        try{
            MongoCollection<Document> collection = base.getCollection("book");
          //  collection.createIndex(Indexes.ascending("Rating"));FUNKAR
            Document query = new Document();
            //  collection.createIndex(Indexes.ascending("Rating"));FUNKAR
            query.append("$regex", searchRating);
            query.append("$options", "i");
            System.out.println(4);
            Document findQuery1 = new Document();
            findQuery1.append("Rating", query);

            FindIterable find = collection.find(findQuery1);

            for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext(); ) {
                Document doc = cursor.next();
                String title = doc.getString("title");
                String isbn = doc.getString("ISBN");
                Genre genre = Genre.valueOf(doc.getString("genre"));
                int rating = Integer.parseInt(doc.getString("Rating"));
                String name = doc.getString("authorName");
                String authId = doc.getString("authorID");
                String published = doc.getString("published");
                String dob = doc.getString("dob");

                result.add(new Book(title, isbn, genre, rating, published, new Author(authId, name, dob, isbn)));

            }
            } catch(Exception ex){
            throw new BooksDbException(ex.getMessage(),ex);
        }

        return result;
    }


    @Override
    public void addBookToDb(Book book) throws BooksDbException {
        if(book != null)
        {

            try {

                Document doc=new Document();
                doc.append("ISBN",book.getIsbn());
                doc.append("title",book.getTitle());
                doc.append("genre",book.getGenre().toString());
                doc.append("Rating",Integer.toString(book.getRating()));
                doc.append("authorID",book.getAuthor().getAuthorIDs());
                doc.append("authorName",book.getAuthor().getFullName());
                doc.append("published",book.getPublished());
                doc.append("dob",book.getAuthor().getDob());
                base.getCollection("book").insertOne(doc);

                Document doc1=new Document();

                doc1.append("authorID", book.getAuthor().getAuthorIDs());
                doc1.append("authorName",book.getAuthor().getFullName());
                doc1.append("dob",book.getAuthor().getDob());
                doc1.append("ISBN",book.getAuthor().getISBN());
                base.getCollection("author").insertOne(doc1);


            } catch(Exception ex){
                throw new BooksDbException();

            }

        }
    }

    @Override
    public void addAuthorToDb(Author author) throws BooksDbException {
        if(author == null) throw new BooksDbException("Cannot add"); {

            try {
                Document doc=new Document();

                doc.append("authorID",author.getAuthorIDs());
                doc.append("authorName",author.getFullName());
                doc.append("dob",author.getDob());
                doc.append("ISBN",author.getISBN());
                base.getCollection("author").insertOne(doc);
            } catch(Exception ex){
throw new BooksDbException(ex.getMessage(),ex);
            }
        }

    }

    @Override
    public List<Book> getBook(Document doc) throws BooksDbException {
        return null;
    }


}