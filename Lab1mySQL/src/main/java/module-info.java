module se.kth.najiib.lab1mysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens se.kth.najiib.lab1mysql to javafx.fxml;
    exports se.kth.najiib.lab1mysql;
    exports se.kth.najiib.lab1mysql.modelVC;
    opens se.kth.najiib.lab1mysql.modelVC to javafx.fxml;
    exports se.kth.najiib.lab1mysql.mViewController;
    opens se.kth.najiib.lab1mysql.mViewController to javafx.fxml;
}