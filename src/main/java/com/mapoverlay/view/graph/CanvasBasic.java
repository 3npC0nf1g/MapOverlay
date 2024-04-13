package com.mapoverlay.view.graph;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
/**
 * Une classe abstraite pour représenter un canevas de base avec des fonctionnalités de zoom et de défilement.
 * Cette classe étend AnchorPane et contient un objet Canvas pour le dessin.
 */
public abstract class CanvasBasic extends AnchorPane {
    Canvas canvas;
    double zoomFactor = 1.0;
    double offsetX = 0,offsetY = 0;
    double x,y;
    double centerX,centerY;
    double currentCenterX,currentCenterY;
    /**
     * Constructeur de la classe CanvasBasic.
     *
     * @param width  La largeur du canevas.
     * @param height La hauteur du canevas.
     */
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
    /**
     * Méthode abstraite à implémenter pour mettre à jour le canevas après les changements de zoom et de défilement.
     */
    public abstract void update();

}
