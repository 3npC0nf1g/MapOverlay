package com.mapoverlay.view.graph;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GraphicSegment extends CanvasBasic{

    private boolean haveSweepLine = false;

    private List<Map> maps;

    private double sweepLineY = 0;
    private double sweepLineX = 0;

    private Color sweepLineColor = Color.BLACK;

    public GraphicSegment(int width, int height) {
        super(width, height);
        maps = new ArrayList<>();
        update();
    }

    public void setSweepLine(double x, double y) {
        this.haveSweepLine = true;
        this.sweepLineX = x;
        this.sweepLineY = y;
        update();
    }

    @Override
    public void update(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        currentCenterX = centerX + offsetX;
        currentCenterY = centerY + offsetY;

        drawAxes(gc);

        if(haveSweepLine){
            gc.setStroke(sweepLineColor);
            gc.strokeLine(0, currentCenterY-(sweepLineY *zoomFactor), canvas.getWidth(), currentCenterY-(sweepLineY *zoomFactor));
            gc.strokeOval((currentCenterX+(sweepLineX *zoomFactor))-5,(currentCenterY-(sweepLineY *zoomFactor))-5,10,10);
            gc.setStroke(Color.LIGHTGRAY);
        }

        for(Map m : maps){
            for(Segment s: m.getSegments()){
                drawLine(gc,m.getColor(),s);
            }
        }
    }

    public void drawLine(GraphicsContext gc,Color color,Segment s){
        Point SPoint = s.getSPoint();
        Point EPoint = s.getEPoint();

        double startX = currentCenterX + (SPoint.getX() * zoomFactor);
        double startY = currentCenterY + (SPoint.getY() * zoomFactor) * -1;
        double endX = currentCenterX + (EPoint.getX() * zoomFactor);
        double endY = currentCenterY + (EPoint.getY() * zoomFactor) * -1;

        gc.setStroke(color);
        gc.strokeLine(startX, startY, endX, endY);
    }

    public void setMapList(List<Map> maps) {
        this.maps = maps;
        update();
    }

    public void drawAxes(GraphicsContext gc){
        int interval = setInterval();

        int numberOfInterval = 100;

        for(int i = -1000; i <= 1000; i++){
            gc.setStroke(Color.LIGHTGRAY);
            double x = currentCenterX + (zoomFactor*i*interval);
            double y = currentCenterY + (zoomFactor*i*interval);
            gc.strokeLine(0, y, canvas.getWidth(), y);
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }

        gc.setStroke(Color.GRAY);
        gc.strokeLine(0, currentCenterY, canvas.getWidth(), currentCenterY);

        // Dessiner l'axe y
        gc.strokeLine(currentCenterX, 0, currentCenterX, canvas.getHeight());

        gc.setStroke(Color.GRAY);
        for (int i = -numberOfInterval; i <= numberOfInterval; i++) {
            double x = currentCenterX + (zoomFactor*i*interval);
            gc.strokeLine(x, currentCenterY - 5, x, currentCenterY + 5);
            gc.strokeText(Integer.toString(i * interval), x - 10, currentCenterY + 20);
        }
        // Dessiner les graduations sur l'axe y
        for (int i = -numberOfInterval; i <= numberOfInterval; i++) {
            double y = currentCenterY + (zoomFactor*i*interval);
            gc.strokeLine(currentCenterX - 5, y, currentCenterX + 5, y);
            gc.strokeText(Integer.toString(-i * interval), currentCenterX + 10, y + 5);
        }
    }

    private int setInterval(){
        if(zoomFactor >= 2.25){
            return 10;
        }else if(zoomFactor >= 1.5){
            return 25;
        }else if(zoomFactor >= 0.75){
            return 50;
        }else if(zoomFactor >= 0.5){
            return 100;
        }else if(zoomFactor >= 0.3){
            return 200;
        }else {
            return 500;
        }
    }

    public void clear() {
        this.haveSweepLine = false;
        this.sweepLineX = 0;
        this.sweepLineY = 0;
        update();
    }

    public void setSweepLineColor(Color value) {
        sweepLineColor = value;
        update();
    }

    public void clearSweep() {
        this.haveSweepLine = false;
        update();
    }
}
