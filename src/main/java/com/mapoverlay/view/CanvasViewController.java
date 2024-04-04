package com.mapoverlay.view;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CanvasViewController {

    private double zoomFactor = 1;

    private double sweepLine = 0;
    private List<Map> maps = new ArrayList<>();

    double centerX,centerY;
    double currentCenterX,currentCenterY;

    private double x,y;
    private double offsetX = 0;
    private double offsetY = 0;

    @FXML
    private Canvas canvas;

   public void initialize(){
       centerX = canvas.getWidth() / 2;
       centerY = canvas.getHeight() / 2;
       updateCanvas();
   }

    @FXML
    void setOnScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        if(deltaY > 0 && zoomFactor < 5){
            zoomFactor += 0.05;
        } else if (deltaY < 0 && zoomFactor > 0.18) {
            zoomFactor -= 0.05;
        }
        updateCanvas();
    }

    @FXML
    void mouseDrag(MouseEvent event) {
        offsetX = (event.getX() - x);
        offsetY = (event.getY() - y);
        updateCanvas();
    }

    @FXML
    void mouseClick(MouseEvent event) {
        x = event.getX();
        y = event.getY();
    }

    @FXML
    void mouseRelease(MouseEvent event) {
        centerX = currentCenterX;
        centerY = currentCenterY;
        offsetX = 0;
        offsetY = 0;
    }

    public void setMapList(List<Map> maps) {
       this.maps = maps;
       updateCanvas();
    }

    private void updateCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        currentCenterX = centerX + offsetX;
        currentCenterY = centerY + offsetY;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeLine(0, currentCenterY-(sweepLine*zoomFactor), canvas.getWidth(), currentCenterY-(sweepLine*zoomFactor));
        drawAxes(gc, currentCenterX, currentCenterY);

        for(Map m : maps){
            for(Segment s: m.getSegments()){
                Point SPoint = s.getSPoint();
                Point EPoint = s.getEPoint();

                double startX = currentCenterX + (SPoint.getX() * zoomFactor);
                double startY = currentCenterY + (SPoint.getY() * zoomFactor) * -1;
                double endX = currentCenterX + (EPoint.getX() * zoomFactor);
                double endY = currentCenterY + (EPoint.getY() * zoomFactor) * -1;

                gc.setStroke(m.getColor());
                gc.strokeLine(startX, startY, endX, endY);
            }
        }
    }

    private void drawAxes(GraphicsContext gc, double centerX, double centerY) {
        int interval = setInterval();

        int numberOfInterval = 100;

        for(int i = -1000; i <= 1000; i++){
            gc.setStroke(Color.LIGHTGRAY);
            double x = centerX + (zoomFactor*i*interval);
            double y = centerY + (zoomFactor*i*interval);
            gc.strokeLine(0, y, canvas.getWidth(), y);
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }

        // Dessiner l'axe x
        gc.setStroke(Color.GRAY);
        gc.strokeLine(0, centerY, canvas.getWidth(), centerY);

        // Dessiner l'axe y
        gc.strokeLine(centerX, 0, centerX, canvas.getHeight());

        // Dessiner les graduations sur l'axe x
        gc.setStroke(Color.GRAY);
        for (int i = -(numberOfInterval/2); i <= (numberOfInterval/2); i++) {
            double x = centerX + (zoomFactor*i*interval);
            gc.strokeLine(x, centerY - 5, x, centerY + 5);
            gc.strokeText(Integer.toString(i * interval), x - 10, centerY + 20);
        }
        // Dessiner les graduations sur l'axe y
        for (int i = -(numberOfInterval/2); i <= (numberOfInterval/2); i++) {
            double y = centerY + (zoomFactor*i*interval);
            gc.strokeLine(centerX - 5, y, centerX + 5, y);
            gc.strokeText(Integer.toString(-i * interval), centerX + 10, y + 5);
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
        }else {
            return 200;
        }
    }

    public void addPoint(Point p) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);

        gc.strokeRect(p.getX()+0.05, p.getY()+0.05,p.getX()-0.05, p.getY()-0.05);
    }

    public void setSweepLine(double y) {
       this.sweepLine = y;
       updateCanvas();
    }
}
