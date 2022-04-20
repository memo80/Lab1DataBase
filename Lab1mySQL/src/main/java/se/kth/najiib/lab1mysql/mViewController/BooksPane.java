package se.kth.najiib.lab1mysql.mViewController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import se.kth.najiib.lab1mysql.modelVC.*;


/**
 * The main pane for the view, extending VBox and including the menus. An
 * internal BorderPane holds the TableView for books and a search utility.
 *
 * @author anderslm@kth.se
 */
public class BooksPane extends VBox {

    private TableView<Book> booksTable;
    private ObservableList<Book> booksInTable; // the data backing the table view

    private ComboBox<SearchMode> searchModeBox;
    private TextField searchField;
    protected Button searchButton;
    private Controller controller;
protected Menu manageMenu;
    private MenuBar menuBar;
    protected Menu searchMenu;

    public BooksPane(BooksDbImpl booksDb) {
        controller = new Controller(booksDb, this);
        this.init(controller);
    }

    /**
     * Display a new set of books, e.g. from a database select, in the
     * booksTable table view.
     *
     * @param books the books to display
     */
    public void displayBooks(List<Book> books) {
        booksInTable.clear();
        booksInTable.addAll(books);
    }

    /**
     * Notify user on input error or exceptions.
     *
     * @param msg the message
     * @param type types: INFORMATION, WARNING et c.
     */
    protected void showAlertAndWait(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }

    private void init(Controller controller) {

        booksInTable = FXCollections.observableArrayList();

        // init views and event handlers
        initBooksTable();
        initSearchView(controller);
        initMenus();

        FlowPane bottomPane = new FlowPane();
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.getChildren().addAll(searchModeBox, searchField, searchButton);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(booksTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }

    private void initBooksTable() {
        booksTable = new TableView<>();
        booksTable.setEditable(false); // don't allow user updates (yet)
        booksTable.setPlaceholder(new Label("No rows to display"));
//loopa igenom arraylisten


        // define columns
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> genreCol = new TableColumn<>("Genre");
        TableColumn<Book, Integer> ratingCol = new TableColumn<>("Rating");
        TableColumn<Book, String> dateCol = new TableColumn<>("Published");

        TableColumn<Book, ArrayList> nameCol = new TableColumn<>("Author");


        booksTable.getColumns().addAll(titleCol, isbnCol,genreCol,ratingCol,dateCol,nameCol);// lägger in i tabellen
        // give title column some extra space
        titleCol.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.20));
        isbnCol.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.20));
        nameCol.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.35));

        // define how to fill data for each cell,
        // get values from Book properties
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Published"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("authors"));

        // associate the table view with the data
        booksTable.setItems(booksInTable);
    }

    private void initSearchView(Controller controller) {
        searchField = new TextField();
        searchField.setPromptText("Search for...");
        searchModeBox = new ComboBox<>();
        searchModeBox.getItems().addAll(SearchMode.values());
        searchModeBox.setValue(SearchMode.Title);
        searchButton = new Button("Search");

        // event handling (dispatch to controller)
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String searchFor = searchField.getText();
                SearchMode mode = searchModeBox.getValue();
                controller.onSearchSelected(searchFor, mode);
            }
        });
        searchButton.setDisable(true);
    }

    private void initMenus() {
        AddBookDialog dialog = new AddBookDialog();
        AddAuthorDialog authorDialog=new AddAuthorDialog();

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        EventHandler<ActionEvent>exitHandler= new EventHandler<>(){
            @Override
            public void handle(ActionEvent event) {

                controller.handledisConnection();// KOLLA UPP VRF DET BLIR NULL!!!!!!
                System.out.println("Error");

                Platform.exit();// save data and exit
                // if(bol.connect()) throw new BooksDbException("Error");
            }
        };
        exitItem.addEventHandler(ActionEvent.ACTION,exitHandler);
        MenuItem connectItem = new MenuItem("Connect to Db");

        EventHandler<ActionEvent>connectHandler= new EventHandler<>(){
            @Override
            public void handle(ActionEvent event) {

                controller.handleConnection("dblibrary");// KOLLA UPP VRF DET BLIR NULL!!!!!!
             manageMenu.setDisable(false);
            searchButton.setDisable(false);
             searchMenu.setDisable(false);
                // if(bol.connect()) throw new BooksDbException("Error");
            }
        };
        connectItem.addEventHandler(ActionEvent.ACTION,connectHandler);
        MenuItem disconnectItem = new MenuItem("Disconnect");
        EventHandler<ActionEvent>disconnectHandler= new EventHandler<>(){
            @Override
            public void handle(ActionEvent event) {

                controller.handledisConnection();// KOLLA UPP VRF DET BLIR NULL!!!!!!
                manageMenu.setDisable(true);
               //searchButton.setDisable(true);
                searchMenu.setDisable(true);
                // if(bol.connect()) throw new BooksDbException("Error");
            }
        };
        disconnectItem.addEventHandler(ActionEvent.ACTION,disconnectHandler);
        fileMenu.getItems().addAll(exitItem, connectItem, disconnectItem);

        searchMenu = new Menu("Search");
        MenuItem titleItem = new MenuItem("Title");
        MenuItem isbnItem = new MenuItem("ISBN");
        MenuItem authorItem = new MenuItem("Author");
        searchMenu.getItems().addAll(titleItem, isbnItem, authorItem);
        searchMenu.setDisable(true);
         manageMenu = new Menu("Manage");
        MenuItem addItem = new MenuItem("Add book");
        EventHandler<ActionEvent>conHandler= new EventHandler<>(){
            @Override
            public void handle(ActionEvent event) {
                Optional<Book> result = dialog.showAndWait();
                if (result.isPresent()) {
                    Book book = result.get();
                    controller.handleAddBook(book);
                    System.out.println("Result: " + book.toString());
                } else {
                    System.out.println("Canceled");
                }
            }
        };
        addItem.addEventHandler(ActionEvent.ACTION,conHandler);

        MenuItem addAuthorItem = new MenuItem("Add Author");
        EventHandler<ActionEvent> addAuthorHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<Author> result = authorDialog.showAndWait();
                if (result.isPresent()) {
                    Author author = result.get();
                    controller.handleAddAuthor(author.getAuthorIDs(),author.getFullName(),author.getDob(),author.getISBN());
                    System.out.println("Result: " + author.toString());
                } else {
                    System.out.println("Canceled");
                }
            }
        };
        addAuthorItem.addEventHandler(ActionEvent.ACTION, addAuthorHandler);

       /* MenuItem AddRatingItem= new MenuItem("Add rating");
        EventHandler<ActionEvent> addRatingHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                AddRatingDialog dialog = new AddRatingDialog(booksInTable);
                Optional<Book> result = dialog.showAndWait();
                if (result.isPresent()) {
                    Book book = result.get();
                    System.out.println("Result: " + book);
                    controller.updateRating();
                } else {
                    System.out.println("Canceled");
                }
            }
        };
        AddRatingItem.addEventHandler(ActionEvent.ACTION, addRatingHandler);*/

        MenuItem removeItem = new MenuItem("Remove");
        MenuItem update1Item = new MenuItem("Update");
        manageMenu.getItems().addAll(addItem, removeItem, update1Item,addAuthorItem);
        manageMenu.setDisable(true);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu);
    }
}