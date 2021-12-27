package se.kth.najiib.databasfinversion.mViewController;

import javafx.application.Platform;
import se.kth.najiib.databasfinversion.modelVC.*;

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

    protected void onSearchSelected(String searchFor, SearchMode mode) {

        try {
            if (searchFor != null && searchFor.length() != 0) {
                result1=new ArrayList<>();
                switch (mode) {
                    case Title:
                        new Thread(() -> {
                            try {
                                result1 = booksDb.searchBooksByTitle(searchFor);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateResult(result1);
                                    }
                                });

                            } catch (BooksDbException e) {
                                booksView.showAlertAndWait("Database error.", ERROR);
                            }
                        }).start();
                        break;
                    case ISBN:

                        new Thread(() -> {
                            try {
                                result1 = booksDb.searchBooksByISBN(searchFor);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateResult(result1);
                                    }
                                });

                            } catch (BooksDbException e) {
                                booksView.showAlertAndWait("Database error.", ERROR);
                            }
                        }).start();
                        break;
                    case Genre:
                        new Thread(() -> {
                            try {
                               result1 =booksDb.searchBooksByGenre(searchFor);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateResult(result1);
                                    }
                                });

                            } catch (BooksDbException e) {
                                booksView.showAlertAndWait("Database error.", ERROR);
                            }
                        }).start();
                        break;
                    case Author:
                        new Thread(() -> {
                            try {
                                 result1 = booksDb.searchBookByAuthor(searchFor);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateResult(result1);
                                    }
                                });

                            } catch (BooksDbException e) {
                                booksView.showAlertAndWait("Database error.", ERROR);
                            }
                        }).start();
                        break;
                    case Rating:
                        new Thread(() -> {
                            try {
                                int rating=Integer.parseInt(searchFor);
                                result1 =booksDb.searchBooksByRating(rating);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateResult(result1);
                                    }
                                });

                            } catch (BooksDbException | SQLException e) {
                                booksView.showAlertAndWait("Database error.", ERROR);
                            }

                        }).start();

                        break;
                    default: result1=new ArrayList<>();
                }

            } else {
                booksView.showAlertAndWait(
                        "Enter a search string!", WARNING);
            }
        } catch (Exception e) {
            booksView.showAlertAndWait("Database error.",ERROR);
        }
    }

    protected void updateResult(List<Book> result) {
        if (result == null || result.isEmpty()) {
            booksView.showAlertAndWait(
                    "No results found.", INFORMATION);
        } else {
            booksView.displayBooks(result);
        }
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
             } catch (BooksDbException e) {
                 Platform.runLater(new Runnable() {
                     @Override
                     public void run() {
                         booksView.showAlertAndWait("Cannot add book", ERROR);
                     }
                 });
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }).start();
     }

    public void handleAddAuthor(Author author) {

        new Thread(() -> {
            try {
                booksDb.addAuthorToDb(author);
            } catch (BooksDbException e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        booksView.showAlertAndWait("Cannot add author", ERROR);
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();

    }



    // TODO:
    // Add methods for all types of user interaction (e.g. via  menus).
}