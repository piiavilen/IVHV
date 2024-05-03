module org.example.ohtkoodia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.ohtkoodia to javafx.fxml;
    exports org.example.ohtkoodia;
}