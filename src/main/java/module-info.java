module com.mapoverlay.mapoverlay {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.mapoverlay to javafx.fxml;
    exports com.mapoverlay;
}