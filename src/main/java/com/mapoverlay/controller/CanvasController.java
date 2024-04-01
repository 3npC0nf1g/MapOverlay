package com.mapoverlay.controller;

import com.mapoverlay.model.data.Segment;
import com.mapoverlay.view.CanvasViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class CanvasController {
    private CanvasViewController CVC;
    public CanvasController(AnchorPane container){
        FXMLLoader fxmlLoader = new FXMLLoader(CanvasViewController.class.getResource("canvas-view.fxml"));
        try{
            Canvas canvas = fxmlLoader.load();
            CVC = fxmlLoader.getController();
            CVC.initialize();
            container.getChildren().add(canvas);
        }catch (Exception e){
            // TODO gérer execption
        }

    }

    public void setSegments(List<Segment> segments){
        CVC.setSegmentList(segments);
    }
}
