module org.example.myeventsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.myeventsapp to javafx.fxml;
    exports org.example.myeventsapp;
}