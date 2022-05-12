module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;

    opens client.controllers to javafx.fxml;
    exports client.controllers;

    opens client to javafx.graphics;
    exports client;
}
