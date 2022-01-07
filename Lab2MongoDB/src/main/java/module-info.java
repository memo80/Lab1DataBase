module se.kth.najiib.lab2mongodb {
   /* requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;
    requires java.sql;


    opens se.kth.najiib.lab2mongodb to javafx.fxml;
    exports se.kth.najiib.lab2mongodb;


    */
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mongo.java.driver;


    opens se.kth.najiib.lab2mongodb to javafx.fxml;
    exports se.kth.najiib.lab2mongodb;
    exports se.kth.najiib.lab2mongodb.modelVC;
    opens se.kth.najiib.lab2mongodb.modelVC to javafx.fxml;
    exports se.kth.najiib.lab2mongodb.mViewController;
    opens se.kth.najiib.lab2mongodb.mViewController to javafx.fxml;
}