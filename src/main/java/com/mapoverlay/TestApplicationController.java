package com.mapoverlay;

import com.mapoverlay.controller.MapOverlayController;
import com.mapoverlay.model.MapOverlay;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.datastructure.QTree;
import com.mapoverlay.model.datastructure.TTree;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class TestApplicationController extends Application {

    private final MapOverlayController MOC = new MapOverlayController();
    private final MapOverlay MO = new MapOverlay();

    @Override
    public void start(Stage stage) throws Exception {
        MOC.setListener(new MapOverlayController.listener() {
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

            @Override
            public void changeSelfInter() {
                MO.changeSelfInter();
            }
        });
        MOC.show();
    }

    public static void main(String[] args){
        launch();
    }
}
