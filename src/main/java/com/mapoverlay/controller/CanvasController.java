package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.view.graph.GraphicSegment;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CanvasController {
    private GraphicSegment graphicSegment;
    public CanvasController(AnchorPane container){
        graphicSegment = new GraphicSegment(900,650);
        container.getChildren().add(graphicSegment);
    }

    public void setMap(List<Map> maps) {
        graphicSegment.setMapList(maps);
    }

    public void MakeSweepLine(double x, double y) {
        graphicSegment.setSweepLine(x,y);
    }

    public void clear() {
        graphicSegment.setMapList(new ArrayList<>());
        graphicSegment.clear();
    }

    public void setSweepColor(Color value) {
        graphicSegment.setSweepLineColor(value);
    }

    public void clearSweep() {
        graphicSegment.clearSweep();
    }
}
