module com.mapoverlay.mapoverlay {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    exports com.mapoverlay.controller;
    exports com.mapoverlay.model;
    exports com.mapoverlay.view;
    opens com.mapoverlay.view to javafx.fxml;
    exports com.mapoverlay;
}