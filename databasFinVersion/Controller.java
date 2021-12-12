package se.kth.peter.databasdb.mViewController;

import javafx.application.Platform;
import se.kth.peter.databasdb.modelVC.Book;
import se.kth.peter.databasdb.modelVC.BooksDbException;
import se.kth.peter.databasdb.modelVC.BooksDbInterface;
import se.kth.peter.databasdb.modelVC.SearchMode;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.*;

/**
 * The controller is responsible for handling user requests and update the view
 * (and in some cases the model).
 *
 * @author anderslm@kth.se
 */
public class Controller {

    private final BooksPane booksView; // view
    private final BooksDbInterface booksDb; // model



    public Controller(BooksDbInterface booksDb, BooksPane booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }
    public Controller() {
        this.booksDb = null;
        this.booksView = null;
    }

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        try {
            if (searchFor != null && searchFor.length() != 0) {
                List<Book> result = null;
                switch (mode) {
                    case Title:
                        result = booksDb.searchBooksByTitle(searchFor);
                        break;
                    case ISBN:
                        result= booksDb.searchBooksByISBN(searchFor);
                        break;
                    case Genre:
                        result=booksDb.searchBooksByGenre(searchFor);
                        break;
                    case Author:
                        // ...
                        break;
                    case Rating:
                        int rating=Integer.parseInt(searchFor);
                        result=booksDb.searchBooksByRating(rating);
                        break;
                    default:
                        result= new ArrayList<>();
                }
                if (result == null || result.isEmpty()) {
                    booksView.showAlertAndWait(
                            "No results found.", INFORMATION);
                } else {
                    booksView.displayBooks(result);
                }
            } else {
                booksView.showAlertAndWait(
                        "Enter a search string!", WARNING);
            }
        } catch (Exception e) {
            booksView.showAlertAndWait("Database error.",ERROR);
        }
    }

    public void handleConnection(String database) throws BooksDbException {
        booksDb.connect(database);
    }

    public void handledisConnection() throws BooksDbException {
        booksDb.disconnect();
    }

    public void handleAddBook(Book book) throws BooksDbException {

         Thread t = new Thread(() -> {

            List<Book> empResult = booksDb.addBook(book);

            Platform.runLater(
                    new Runnable() {
                        public void run() {
                            try {
                                updateUI((ArrayList<Book>) empResult);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                System.out.println("error");
                            }
                        }
                    });
        });
        t.start();
        t.currentThread().getName();
        //System.out.println(t);

    }

    private void updateUI(ArrayList<Book> empResult) throws InterruptedException {

        if(empResult != null){

            while (true) {
                try{
                    Thread.sleep(1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    // TODO:
    // Add methods for all types of user interaction (e.g. via  menus).
}
