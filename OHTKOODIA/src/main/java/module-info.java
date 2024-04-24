module org.example.ohtkoodia {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ohtkoodia to javafx.fxml;
    exports org.example.ohtkoodia;
}