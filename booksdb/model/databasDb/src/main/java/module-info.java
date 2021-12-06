module se.kth.najiib.databasdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens se.kth.najiib.databasdb to javafx.fxml;
    exports se.kth.najiib.databasdb;
}