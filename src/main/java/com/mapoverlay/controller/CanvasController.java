package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.view.graph.GraphicSegment;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
/**
 * Contrôleur responsable de la gestion de l'affichage des segments graphiques sur un canevas.
 * Il utilise un objet GraphicSegment pour représenter les segments graphiques et effectuer diverses opérations de dessin.
 */
public class CanvasController {
    /**
     * Instance de GraphicSegment utilisée pour dessiner les segments graphiques.
     */
    private GraphicSegment graphicSegment;

    /**
     * Initialise le contrôleur avec un conteneur pour afficher les segments graphiques.
     * @param container Le conteneur dans lequel afficher les segments graphiques.
     */
    public CanvasController(AnchorPane container){
        graphicSegment = new GraphicSegment(900,650);
        container.getChildren().add(graphicSegment);
    }

    /**
     * Définit la liste des cartes à afficher sur le canevas.
     * @param maps La liste des cartes à afficher.
     */
    public void setMap(List<Map> maps) {
        graphicSegment.setMapList(maps);
    }

    /**
     * Place la ligne de balayage sur le canevas à la position spécifiée.
     * @param x La coordonnée x de la position de la ligne de balayage.
     * @param y La coordonnée y de la position de la ligne de balayage.
     */
    public void MakeSweepLine(double x, double y) {
        graphicSegment.setSweepLine(x,y);
    }

    /**
     * Efface tous les segments graphiques et réinitialise le canevas.
     */
    public void clear() {
        graphicSegment.setMapList(new ArrayList<>());
        graphicSegment.clear();
    }

    /**
     * Définit la couleur de la ligne de balayage.
     * @param value La couleur de la ligne de balayage.
     */
    public void setSweepColor(Color value) {
        graphicSegment.setSweepLineColor(value);
    }

    /**
     * Efface la ligne de balayage du canevas.
     */
    public void clearSweep() {
        graphicSegment.clearSweep();
    }
}
