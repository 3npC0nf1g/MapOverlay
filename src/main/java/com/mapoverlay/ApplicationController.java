package com.mapoverlay;

import com.mapoverlay.controller.MapOverlayController;
import com.mapoverlay.model.MapOverlay;
import com.mapoverlay.model.data.InterserctionPoint;
import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
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
    public List<Point> computeMapOverlay(List<Segment> segments) {
        return MO.FindInterSections(segments);
    }
}
