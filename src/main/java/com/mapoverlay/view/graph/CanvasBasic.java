package com.mapoverlay.view.graph;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public abstract class CanvasBasic extends AnchorPane {
    Canvas canvas;
    double zoomFactor = 1.0;
    double offsetX = 0,offsetY = 0;
    double x,y;
    double centerX,centerY;
    double currentCenterX,currentCenterY;

    public CanvasBasic(int width, int height){
        canvas = new Canvas(width,height);
        getChildren().add(canvas);

        centerX = (double) width /2;
        centerY = (double) height /2;

        setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();
            if(deltaY > 0 && zoomFactor < 10){
                zoomFactor += 0.05;
            } else if (deltaY < 0 && zoomFactor > 0.1) {
                zoomFactor -= 0.05;
            }
            update();
        });

        setOnMouseDragged(mouseEvent -> {
            offsetX = (mouseEvent.getX() - x);
            offsetY = (mouseEvent.getY() - y);
            update();
        });

        setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
        });

        setOnMouseReleased(mouseEvent -> {
            centerX = currentCenterX;
            centerY = currentCenterY;
            offsetX = 0;
            offsetY = 0;
        });
    }

    public abstract void update();

}
