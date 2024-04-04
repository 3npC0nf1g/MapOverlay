package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.Point;
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

    public void setMap(List<Map> maps) {
        CVC.setMapList(maps);
    }

    public void addPoint(List<Point> intersectionPoint) {
        for (Point p : intersectionPoint){
            CVC.addPoint(p);
        }
    }

    public void MakeSweepLine(double y) {
        CVC.setSweepLine(y);
    }
}
