package com.mapoverlay.view;

import com.mapoverlay.model.data.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class MapOverlayViewController {

    @FXML
    private VBox MapList;

    @FXML
    private AnchorPane canvasContainer;

    @FXML
    private ColorPicker colorSweep;

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

    public AnchorPane getCanvasContainer() {
        return canvasContainer;
    }

    @FXML
    void SweepColor(ActionEvent event) {
        listener.setSweepColor(colorSweep.getValue());
    }

    @FXML
    void AutoStep(ActionEvent event) {
        listener.autoStep();
    }

    @FXML
    void ShowQ(ActionEvent event) {
        listener.showQ();
    }

    @FXML
    void ShowT(ActionEvent event) {
        listener.ShowT();
    }

    @FXML
    void resetQ(ActionEvent event) {
        listener.resetQ();
    }

    @FXML
    void showResult(ActionEvent event) {
        listener.showRésult();
    }

    @FXML
    void chooseSelfIntersection(ActionEvent event) {
        listener.changeSelfInter();
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
        void setSweepColor(Color value);
        void showQ();
        void ShowT();
        void resetQ();
        void autoStep();
        void changeSelfInter();
        void showRésult();
    }

}
