package com.mapoverlay.view;

import com.mapoverlay.model.data.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class MapOverlayViewController {

    @FXML
    private VBox SegmentList;

    @FXML
    private AnchorPane canvasContainer;

    // FXML Function
    @FXML
    void addSegment(ActionEvent event) {
        try {
            listener.openAddSegment();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void importGraph(ActionEvent event) {
        listener.importGraph();
    }

    @FXML
    void launchMapOverlay(ActionEvent event) {

    }

    @FXML
    void saveGraph(ActionEvent event) {
        listener.saveSegmentList();
    }
    public void updateSegmentList(List<Segment> segments){
        SegmentList.getChildren().clear();
        for(Segment s : segments){
            FXMLLoader loader = new FXMLLoader(SegmentItemViewController.class.getResource("segment-view.fxml"));
            try {
                AnchorPane pane = loader.load();
                SegmentItemViewController controller = loader.getController();
                controller.setListener((segment) -> {
                    SegmentList.getChildren().remove(pane);
                    listener.deleteSegment(segment);
                });
                controller.setSegment(s);
                SegmentList.getChildren().add(pane);
            } catch (IOException e) {
                // TODO gérer l'execption
            }
        }
    }

    public AnchorPane getCanvasContainer() {
        return canvasContainer;
    }

    // Listener implementation
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void importGraph();
        void openAddSegment() throws IOException;

        void deleteSegment(Segment segment);

        void saveSegmentList();
    }

}
