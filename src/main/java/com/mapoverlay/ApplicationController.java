package com.mapoverlay;

import com.mapoverlay.controller.MapOverlayController;
import com.mapoverlay.model.MapOverlay;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.dataStructure.QTree;
import com.mapoverlay.model.dataStructure.TTree;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class ApplicationController extends Application implements MapOverlayController.listener {

    private final MapOverlayController MOC = new MapOverlayController();
    private final MapOverlay MO = new MapOverlay();

    @Override
    public void start(Stage stage) throws Exception {
        MOC.setListener(this);
        MOC.show();
    }

    public static void main(String[] args){
        launch();
    }

    @Override
    public void InitQ(List<Segment> segments) {
        MO.InitQ(segments);
    }

    @Override
    public Point computeMapOverlayStep() {
        return MO.FindInterSectionsStep();
    }

    @Override
    public QTree getQTree() {
        return MO.getQTree();
    }

    @Override
    public TTree getTTree() {
        return MO.getTTree();
    }
}
