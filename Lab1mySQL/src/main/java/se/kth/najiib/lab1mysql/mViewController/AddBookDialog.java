package se.kth.najiib.lab1mysql.mViewController;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.scene.control.Dialog;

import se.kth.najiib.lab1mysql.modelVC.Author;
import se.kth.najiib.lab1mysql.modelVC.Book;
import se.kth.najiib.lab1mysql.modelVC.Genre;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddBookDialog extends Dialog<Book>{

    private final TextField titleField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField ratingField = new TextField();
    private final TextField publishedField = new TextField();
    private final TextField mmField = new TextField();
    private final TextField yyyyField = new TextField();
    private String date1=null;



    private final ComboBox<Genre> genreChoice = new ComboBox(FXCollections.observableArrayList(Genre.values()));



    public AddBookDialog() {
        buildAddBookDialog();
    }

    private void buildAddBookDialog() {
        this.setTitle("Add a new Book");
        this.setResizable(false); // really?



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Title "), 1, 1);
        grid.add(titleField, 2, 1);
        grid.add(new Label("Isbn "), 1, 2);
        grid.add(isbnField, 2, 2);
        grid.add(new Label("Genre "), 1, 3);
        grid.add(genreChoice, 2, 3);
        grid.add(new Label("Rating "), 1, 4);
        grid.add(ratingField, 2, 4);
        grid.add(new Label("published dd"), 1, 5);
        grid.add(publishedField, 2, 5);
        grid.add(new Label("mm"), 3, 5);
        grid.add(mmField, 4, 5);
        grid.add(new Label("yyyy"), 5, 5);
        grid.add(yyyyField, 6, 5);




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
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                        // Kan g??ra Author author fixa 3 st field som rating och sedan l??gga in varsin ruta
                        int year= Integer.parseInt(yyyyField.getText())-1900;
                        int month=Integer.parseInt(mmField.getText())-1;
                        int dd= Integer.parseInt(publishedField.getText());

                        // Kan g??ra Author author fixa 3 st field som rating och sedan l??gga in varsin ruta
                        date1 =formatter.format(new Date(year,month,dd));
                        result = new Book(titleField.getText(), isbnField.getText(), genreChoice.getValue(),Integer.parseInt(ratingField.getText()), date1);
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
        if (genreChoice.getValue() == null) {
            return false;
        }
        if (!Book.isValidISBN(isbnField.getText())) {
            System.out.println(isbnField.getText());
            return false;
        }
        // if(...) - keep on validating user input...
        if(titleField.getText().isEmpty())
        {
            return false;
        }

        if (ratingField.getText().isEmpty())
        {
            return false;
        }


        if (publishedField.getText().isEmpty())
        {
            return false;
        }

       if (mmField.getText().isEmpty())
        {
            return false;
        }
        if (yyyyField.getText().isEmpty())
        {
            return false;
        }


        return true;
    }

    private void clearFormData() {
        titleField.setText("");
        isbnField.setText("");
        genreChoice.setValue(null);
        ratingField.setText("");
        publishedField.setText("");
        mmField.setText("");
        yyyyField.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}