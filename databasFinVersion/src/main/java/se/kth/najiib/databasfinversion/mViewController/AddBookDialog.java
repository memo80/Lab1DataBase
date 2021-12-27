package se.kth.najiib.databasfinversion.mViewController;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.scene.control.Dialog;
//import se.kth.najiib.databasfinversion.modelVC.Author;
import se.kth.najiib.databasfinversion.modelVC.Author;
import se.kth.najiib.databasfinversion.modelVC.Book;
import se.kth.najiib.databasfinversion.modelVC.Genre;
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
    private final TextField nameField = new TextField();
    private final TextField dobField = new TextField();
    private final TextField dob1Field = new TextField();
    private final TextField dob2Field = new TextField();
    private final TextField IDField = new TextField();
    private final TextField publishedField = new TextField();
    private final TextField mmField = new TextField();
    private final TextField yyyyField = new TextField();
    private String date1=null;
    private String date2=null;


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
        grid.add(new Label("AuthorID"), 1, 5);
        grid.add(IDField, 2, 5);
        grid.add(new Label("Author Name"), 1, 6);
        grid.add(nameField, 2, 6);
        grid.add(new Label("dob"), 1, 7);
        grid.add(dobField, 2, 7);
        grid.add(new Label("mm"), 3, 7);
        grid.add(dob1Field, 4, 7);
        grid.add(new Label("yyyy"), 5, 7);
        grid.add(dob2Field, 6, 7);
        grid.add(new Label("published dd"), 1, 8);
        grid.add(publishedField, 2, 8);
        grid.add(new Label("mm"), 3, 8);
        grid.add(mmField, 4, 8);
        grid.add(new Label("yyyy"), 5, 8);
        grid.add(yyyyField, 6, 8);




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
                        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

                        int year1= Integer.parseInt(dob2Field.getText())-1900;
                        int month1=Integer.parseInt(dob1Field.getText())-1;
                        int dd1= Integer.parseInt(dobField.getText());

                        // Kan göra Author author fixa 3 st field som rating och sedan lägga in varsin ruta

                        date2 =formatter.format(new Date(year1,month1,dd1));

                        Author at=new Author(IDField.getText(),nameField.getText(),date2,isbnField.getText());

                        int year= Integer.parseInt(yyyyField.getText())-1900;
                        int month=Integer.parseInt(mmField.getText())-1;
                        int dd= Integer.parseInt(publishedField.getText());

                        // Kan göra Author author fixa 3 st field som rating och sedan lägga in varsin ruta

                        date1 =formatter.format(new Date(year,month,dd));

                        result = new Book(titleField.getText(), isbnField.getText(), genreChoice.getValue(),Integer.parseInt(ratingField.getText()), date1, at);
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

   /* private void buildAddAuthorDialog(){

        this.setTitle("Add a new Author");
        this.setResizable(false);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("AuthorID "), 1, 1);
        grid.add(authorIDField, 2, 1);
        grid.add(new Label("Name "), 1, 2);
        grid.add(nameField, 2, 2);
        //grid.add(new Label("Book ISBN"), 1, 3);
       // grid.add(ISBNField, 2, 3);


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
                        result = new Author(
                                authorIDField.getText(),
                                nameField.getText(),
                                //ISBNField.get
                        );
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

    }*/

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

        return true;
    }

    private void clearFormData() {
        titleField.setText("");
        isbnField.setText("");
        genreChoice.setValue(null);
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}