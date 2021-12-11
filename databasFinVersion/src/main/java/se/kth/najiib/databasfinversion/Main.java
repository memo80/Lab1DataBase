package se.kth.najiib.databasfinversion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import se.kth.najiib.databasfinversion.modelVC.BooksDbImpl;
import se.kth.najiib.databasfinversion.mViewController.BooksPane;

/**
 * Application start up.
 *
 * @author anderslm@kth.se
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BooksDbImpl booksDb = new BooksDbImpl(); // model
        // Don't forget to connect to the db, somewhere...

        BooksPane root = new BooksPane(booksDb);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Books Database Client");
        // add an exit handler to the stage (X) ?
        primaryStage.setOnCloseRequest(event -> {
            try {
                booksDb.disconnect();
            } catch (Exception e) {}
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}