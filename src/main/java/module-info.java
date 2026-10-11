module com.alperenakademi.yks {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.net.http;
    requires java.xml;
    requires java.desktop;

    opens com.alperenakademi.yks.controller to javafx.fxml;

    exports com.alperenakademi.yks;
}
