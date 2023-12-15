module com.beginsecure.javafxapp.javaapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.net.http;

    opens com.beginsecure.javafxapp.javaapp to javafx.fxml;
    exports com.beginsecure.javafxapp.javaapp;
}