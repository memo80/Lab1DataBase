module se.kth.najiib.databasfinversion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens se.kth.najiib.databasfinversion to javafx.fxml;
    exports se.kth.najiib.databasfinversion;
    exports se.kth.najiib.databasfinversion.modelVC;
    opens se.kth.najiib.databasfinversion.modelVC to javafx.fxml;
}