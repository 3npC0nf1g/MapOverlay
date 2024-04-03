package com.mapoverlay.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

public class MapItemListViewController {

    @FXML
    private AnchorPane SegmentListContainer;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TitledPane tiltedMap;

    @FXML
    void addSegment(ActionEvent event) {
        listener.addSegment();
    }

    @FXML
    void chooseColor(ActionEvent event) {
        Color c = colorPicker.getValue();
        listener.updateColor(c);
    }

    @FXML
    void deleteMap(ActionEvent event) {
        listener.deleteMap();
    }

    @FXML
    void saveMap(ActionEvent event) {
        listener.saveMap();
    }

    @FXML
    void colapseClick(MouseEvent event) {
        listener.colapse();
    }

    public void setColor(Color c){
        colorPicker.setValue(c);
    }

// Listener implementation
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public TitledPane getList() {
        return tiltedMap;
    }

    public interface Listener {
        void saveMap();

        void deleteMap();

        void updateColor(Color c);

        void addSegment();

        void colapse();
    }
}
