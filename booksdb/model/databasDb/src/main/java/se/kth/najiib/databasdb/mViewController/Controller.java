package se.kth.najiib.databasdb.mViewController;

import se.kth.najiib.databasdb.modelVC.Book;
import se.kth.najiib.databasdb.modelVC.BooksDbInterface;
import se.kth.najiib.databasdb.modelVC.Genre;
import se.kth.najiib.databasdb.modelVC.SearchMode;

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

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        try {
            if (searchFor != null && searchFor.length() > 1) {
                List<Book> result = null;
                switch (mode) {
                    case Title:
                        result = booksDb.searchBooksByTitle(searchFor);
                        break;
                    case ISBN:
                        result=booksDb.searchBooksByISBN(searchFor);
                        break;
                    case Author:
                         result=booksDb.searchBooksByAuthor(searchFor);
                        break;
                    case Rating:
                      //  int ratingSearch=Integer.parseInt(searchFor);// Kolla upp denna
                        //result=booksDb.searchBookByRating(ratingSearch);
                        break;
                    case Genre:
                       // Genre g = Genre.valueOf(searchFor);
                       // result=booksDb.searchBookByGenre(g);
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

    // TODO:
    // Add methods for all types of user interaction (e.g. via  menus).
}
