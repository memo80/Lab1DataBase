package se.kth.najiib.lab1mysql.mViewController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import se.kth.najiib.lab1mysql.modelVC.Author;
import se.kth.najiib.lab1mysql.modelVC.Book;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAuthorDialog extends Dialog<Author>{

    private final TextField authorIDField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField ISBNField = new TextField();
    private final TextField dobField = new TextField();
    private final TextField dobField1 = new TextField();
    private final TextField dobField2 = new TextField();

    public AddAuthorDialog(){
        buildAddAuthorDialog();
    }

    private void buildAddAuthorDialog(){

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
        grid.add(new Label("dob (dd)"), 1, 3);
        grid.add(dobField, 2, 3);
        grid.add(new Label("mm"), 3,3 );
        grid.add(dobField1, 4, 3);
        grid.add(new Label("dob(dd-mm-yyyy)"), 5, 3);
        grid.add(dobField2, 6, 3);
        grid.add(new Label("Book ISBN"), 1, 4);
        grid.add(ISBNField, 2, 4);


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
        this.setResultConverter(new Callback<ButtonType, Author>() {
            @Override
            public Author call(ButtonType b) {
                Author result = null;
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                int year1= Integer.parseInt(dobField2.getText())-1900;
                int month1=Integer.parseInt(dobField1.getText())-1;
                int dd1= Integer.parseInt(dobField.getText());

                // Kan göra Author author fixa 3 st field som rating och sedan lägga in varsin ruta

                String date2 =formatter.format(new Date(year1,month1,dd1));


                if (b == buttonTypeOk) {
                    if (isValidData()) {
                        result = new Author(
                                authorIDField.getText(),
                                nameField.getText(),
                                date2,
                                ISBNField.getText());
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

    private boolean isValidData() {
        if (authorIDField.getText() == null) {
            return false;
        }
        if (nameField.getText().isEmpty()) {
            System.out.println(nameField.getText());
            return false;
        }
        // if(...) - keep on validating user input...

        return true;
    }

    private void clearFormData() {
        authorIDField.setText("");
        nameField.setText("");

    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}