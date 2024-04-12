module com.mapoverlay.mapoverlay {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    exports com.mapoverlay.controller;
    exports com.mapoverlay.model;
    exports com.mapoverlay.model.datastructure;
    exports com.mapoverlay.model.data;
    exports com.mapoverlay.view;
    opens com.mapoverlay.view to javafx.fxml;
    exports com.mapoverlay;
    exports com.mapoverlay.view.graph;
    opens com.mapoverlay.view.graph to javafx.fxml;
}