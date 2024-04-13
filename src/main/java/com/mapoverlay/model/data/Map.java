package com.mapoverlay.model.data;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Représente une carte
 */
public class Map {
    private List<Segment> segments;
    private Color color;
    private final int id;
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private boolean extended = true;

    /**
     * Crée une nouvelle carte avec les segments spécifiés et la couleur donnée.
     *
     * @param segments La liste des segments de la carte.
     * @param color    La couleur de la carte.
     */
    public Map(List<Segment> segments, Color color){
        this.id = idCounter.incrementAndGet();
        this.segments = segments;
        this.color = color;
    }

    /**
     * Renvoie l'ID de la carte.
     *
     * @return L'ID de la carte.
     */
    public int getId(){
        return id;
    }

    /**
     * Renvoie la liste des segments de la carte.
     *
     * @return La liste des segments de la carte.
     */
    public List<Segment> getSegments() {
        return segments;
    }

    /**
     * Définit la couleur de la carte.
     *
     * @param color La nouvelle couleur de la carte.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Renvoie la couleur de la carte.
     *
     * @return La couleur de la carte.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Ajoute un segment à la carte.
     *
     * @param segment Le segment à ajouter.
     */
    public void addSegment(Segment segment){
        segments.add(segment);
    }


    /**
     * Supprime un segment de la carte.
     *
     * @param segment Le segment à supprimer.
     */
    public void deleteSegment(Segment segment){
        segments.remove(segment);
    }

    /**
     * Efface tous les segments de la carte.
     */
    public void clearMap(){
        segments.clear();
    }
    /**
     * Définit si la carte est étendue.
     *
     * @param extended true si la carte est étendue, sinon false.
     */
    public void setExtended(boolean extended){
        this.extended = extended;
    }

    /**
     * Renvoie true si la carte est étendue, sinon false.
     *
     * @return true si la carte est étendue, sinon false.
     */
    public boolean getExtended() {
        return extended;
    }
}
