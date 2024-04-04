package com.mapoverlay.view;

import com.mapoverlay.model.data.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MapOverlayViewController {

    private List<Segment> map;

    @FXML
    private VBox MapList;

    @FXML
    private AnchorPane canvasContainer;

    // FXML Function
    @FXML
    void addMap(ActionEvent event) {
        listener.addMap();
    }

    @FXML
    void clearGraph(ActionEvent event) {
        listener.clearGraph();
    }

    @FXML
    void importGraph(ActionEvent event) {
        listener.importMap();
    }

    @FXML
    void launchMapOverlay(ActionEvent event) {
        listener.launchMapOverlay();
    }

    @FXML
    void launchMapOverlayStep(ActionEvent event) {
        listener.launchMapOverlayStep();
    }

    @FXML
    void InitQ(ActionEvent event) {
        listener.InitQ();
    }

    public AnchorPane getCanvasContainer() {
        return canvasContainer;
    }

    // Listener implementation
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public VBox getList() {
        return MapList;
    }

    public interface Listener {
        void importMap();
        void addMap();
        void clearGraph();

        void launchMapOverlay();

        void launchMapOverlayStep();

        void InitQ();
    }

}
