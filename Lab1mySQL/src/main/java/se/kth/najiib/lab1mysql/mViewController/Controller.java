package se.kth.najiib.lab1mysql.mViewController;

import javafx.application.Platform;
import se.kth.najiib.lab1mysql.modelVC.*;

import java.sql.SQLException;
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
    private List<Book> result1;

    public Controller(BooksDbInterface booksDb, BooksPane booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;

    }



    public void handleConnection(String database) {

        new Thread(() -> {
            try {
                booksDb.connect(database);
            } catch (BooksDbException e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        booksView.showAlertAndWait("Cannot connect to database", ERROR);
                    }
                });
            }
        }).start();

    }

    public void handledisConnection()  {

        new Thread(() -> {
            try {
                booksDb.disconnect();
            } catch (BooksDbException e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        booksView.showAlertAndWait("Cannot disconnect database", ERROR);
                    }
                });
            }
        }).start();
    }

    public void handleAddBook(Book book)  {

        new Thread(() -> {
            try {
                booksDb.addBookToDb(book);
            } catch (BooksDbException | SQLException e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        booksView.showAlertAndWait("Cannot add book", ERROR);
                    }
                });
            }
        }).start();
    }

    public void handleAddAuthor(String authorIDs, String fullName ,String dob,String ISBN) {

        new Thread(() -> {
            try {
                booksDb.addAuthorToDb(authorIDs, fullName, dob, ISBN);
            } catch (BooksDbException e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        booksView.showAlertAndWait("Cannot add author", ERROR);
                    }
                });
            }
        }).start();

    }


    protected void onSearchSelected(String searchFor, SearchMode mode){ //Latest added
        new Thread() {
            @Override
            public void run() {
                try {
                    if (searchFor != null && searchFor.length() >= 1) {
                        List<Book> result = null;
                        switch (mode) {
                            case Title:
                                result = booksDb.searchBooksByTitle(searchFor);
                                updateResult(result);
                                break;
                            case ISBN:
                                result = booksDb.searchBooksByISBN(searchFor);
                                updateResult(result);
                                break;
                            case Genre:
                                result = booksDb.searchBooksByGenre(searchFor);
                                updateResult(result);
                                break;
                            case Rating:
                                int ray=Integer.parseInt(searchFor);
                                result = booksDb.searchBooksByRating(ray);
                                updateResult(result);
                                break;
                            case Author:
                                result = booksDb.searchBooksByAuthor(searchFor);
                                updateResult(result);
                                break;
                        }
                    } else {
                        javafx.application.Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                booksView.showAlertAndWait("Enter a search string!", WARNING);
                            }
                        });
                    }
                } catch (BooksDbException | SQLException e) {
                    javafx.application.Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            booksView.showAlertAndWait("Database error: " + e.getMessage(), ERROR);
                        }
                    });
                }
            }
        }.start();
    }

    protected void updateResult(List<Book> result) {
        if (result.isEmpty()||result == null) {
            javafx.application.Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    booksView.showAlertAndWait("No result was found.", INFORMATION);
                }
            });
        } else {
            final List<Book> resFinal = result;
            javafx.application.Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    booksView.displayBooks(resFinal);
                }
            });
        }
    }




    // TODO:
    // Add methods for all types of user interaction (e.g. via  menus).
}