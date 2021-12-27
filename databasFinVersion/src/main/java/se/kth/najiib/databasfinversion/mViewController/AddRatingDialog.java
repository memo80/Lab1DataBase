package se.kth.najiib.databasfinversion.mViewController;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import se.kth.najiib.databasfinversion.modelVC.Book;

/**
 * A simplified example of a form, using JavaFX Dialog and DialogPane. Type
 * parameterized for FooBook.
 *
 * @author Anders Lindström, anderslm@kth.se
 */
public class AddRatingDialog extends Dialog<Book> {

    //private final TextField ratingField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField ratingChoice=new TextField();
    private List <Book> books;


    public AddRatingDialog(List books) {
        buildAddRatingDialog();
        this.books = books;
    }

    private void buildAddRatingDialog() {

        this.setTitle("Enter valid ISBN and new rating");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Isbn "), 1, 1);
        grid.add(isbnField, 2, 1);
        grid.add(new Label("New Rating "), 1, 2);
        grid.add(ratingChoice , 2, 2);


        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        // this callback returns the result from our dialog, via
        // Optional<FooBook> result = dialog.showAndWait();
        // FooBook book = result.get();
        // see DialogExample, line 31-34
        this.setResultConverter(new Callback<ButtonType, Book>() {
            @Override
            public Book call(ButtonType b) {
                Book result = null;
                if (b == buttonTypeOk) {
                    if (isValidData()) {
                       result= new Book(null,isbnField.getText(),null,Integer.parseInt(ratingChoice.getText()),null,null);

                    }
                }

                clearFormData();
                return result;
            }
        });

        // add an event filter to keep the dialog active if validation fails
        // (yes, this is ugly in FX)
        Button okButton
                = (Button) this.getDialogPane().lookupButton(buttonTypeOk);
        okButton.addEventFilter(ActionEvent.ACTION, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!isValidData()) {
                    event.consume();
                    showErrorAlert("Form error", "Invalid input");
                }
            }
        });
    }

    // TODO for the student: check each input separately, to give better
    // feedback to the user

    private boolean isValidData() {
        int count = 0;

        if (!Book.isValidISBN(isbnField.getText())) {
            System.out.println(isbnField.getText());
            return false;
        }
        // if(...) - keep on validating user input...
        //System.out.println(books);
        //System.out.println(books.size());

        for(int i = 0; i<books.size();i++){

            if(!books.get(i).getIsbn().equals(isbnField.getText())){
                count++;
            }
            if(count == books.size()){
                return false;
            }
        }

        return true;
    }

    private void clearFormData() {
        //ratingField.setText("");
        isbnField.setText("");

    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
