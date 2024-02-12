package com.mapoverlay.model;

import java.util.ArrayList;
import java.util.List;

public class MapOverlay {

    /**
     * Structure de données T
     * Structure de données Q
     */

    QTree q = new QTree();
    TTree t = new TTree();


    public MapOverlay(){
        // LOAD FICHIER
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(5,5,3,3));
        segmentList.add(new Segment(1,6,3,4));
        segmentList.add(new Segment(4,5,5,2));
        segmentList.add(new Segment(1,1,2,0.5F));
        segmentList.add(new Segment(7,2,6,1));

        FindInterSections(segmentList);
    }

    public Point testSortInOrder(){
        if(!q.isEmpty()){
            return q.getNextPoint();
        }else {
            return null;
        }
    }

    public Point testIntersection(){
        return findPoint(new Segment(5,6,3,3),new Segment(4,5,5,2));
    }

    public void FindInterSections(List<Segment> segmentList){
        for(Segment s: segmentList){
            q.insert(s.getP1());
            q.insert(s.getP2());
        }

        while (!q.isEmpty()){
            HandleEventPoint(q.getNextPoint());
        }
    }

    public void HandleEventPoint(Point point){

    }

    public void FindNewEvent(Segment sl,Segment sr,Point currentPoint){
        // Créer le point avec ces coord
        Point iPoint = findPoint(sl,sr);
        // Check si le point est en dessous de la sweepline avec le dernier point selectionner
        if(currentPoint.getY() > iPoint.getY() || (currentPoint.getY() == iPoint.getY() && currentPoint.getX() < iPoint.getX())){
            // l'insert ( l'insertion n'ajoute pas de point si y1 = y2 et x1 = x2 donc pas de doublons )
            q.insert(iPoint);
        }
    }

    public Point findPoint(Segment S1,Segment S2){
        float den = S1.getA() * S2.getB() - S1.getB() * S2.getA();
        float numX = S1.getC() * S2.getB() - S1.getB() * S2.getC();
        float numY = S1.getA() * S2.getC() - S1.getC() * S2.getA();

        if(den != 0){ // Normalement pas possible car on appelle la fonction car on sait qu'il y a une intersection mais on sait jamais :x
            float X = numX / den;
            float Y = numY / den;

            return new Point(X,Y);
        }
        return null;
    }
}
