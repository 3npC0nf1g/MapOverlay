package com.mapoverlay.model.data.point;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.Segment;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un point d'événement supérieur
 */
public class StartPoint extends Point{
    private List<Segment> segments = new ArrayList<>();

    /**
     * Crée un nouvel objet StartPoint avec les coordonnées spécifiées.
     *
     * @param x La coordonnée x du point de départ.
     * @param y La coordonnée y du point de départ.
     */
    public StartPoint(double x, double y) {
        super(x, y);
    }


    /**
     * Crée un nouvel objet StartPoint à partir des données spécifiées.
     *
     * @param d Les données à utiliser pour créer le point de départ.
     */
    public StartPoint(Data d) {
        super((Point)d);
    }


    /**
     * Renvoie la liste des segments connectés à ce point de départ.
     *
     * @return La liste des segments connectés à ce point de départ.
     */
    public List<Segment> getSegments() {
        return segments;
    }



    /**
     * Ajoute un segment à la liste des segments connectés à ce point de départ,
     * s'il n'est pas déjà présent dans la liste.
     *
     * @param segment Le segment à ajouter.
     */
    public void addSegment(Segment segment) {
        if(!segments.contains(segment)){
            segments.add(segment);
        }
    }


    /**
     * Crée une copie profonde de ce point de départ, y compris sa liste de segments.
     *
     * @return Une copie profonde de ce point de départ.
     */
    @Override
    public StartPoint clone(){
        StartPoint sP = new StartPoint(this);
        for(Segment s : this.getSegments()){
            sP.addSegment(s);
        }
        return sP;
    }
}
