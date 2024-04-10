package com.mapoverlay.model;

import com.mapoverlay.model.data.*;
import com.mapoverlay.model.dataStructure.QTree;
import com.mapoverlay.model.dataStructure.TTree;

import java.util.*;

public class MapOverlay {
    QTree q = new QTree();
    TTree t = new TTree();

    public List<Point> FindInterSections(List<Segment> segmentList) {
        List<Point> intersections = new ArrayList<>();
        InitQ(segmentList);

        while (!q.isEmpty()) {
            Point intersection = HandleEventPoint(q.getNextPoint());
            if(intersection != null){
                intersections.add(intersection);
            }
        }
        return intersections;
    }

    public Point FindInterSectionsStep(){
        return HandleEventPoint(q.getNextPoint());
    }

    public Point HandleEventPoint(Point point) {

        t.setCurrentY(point.getY());
        InterserctionPoint iPoint = null;

        Set<Segment> Up = new LinkedHashSet<>();

        if(point instanceof StartPoint){
            List<Segment> insertSegments = ((StartPoint) point).getSegments();
            insert(insertSegments);
            Up.addAll(insertSegments);
        }

        Set<Segment> Lp = new LinkedHashSet<>(t.getSegmentsWithLower(point));
        Set<Segment> Cp = new LinkedHashSet<>(t.getSegmentsContains(point));

        Set<Segment> ULC = new LinkedHashSet<>(Lp);
        ULC.addAll(Up);
        ULC.addAll(Cp);

        Set<Segment> LC = new LinkedHashSet<>(Lp);
        LC.addAll(Cp);

        Set<Segment> UC = new LinkedHashSet<>(Up);
        UC.addAll(Cp);

        List<Segment> UClist = new ArrayList<>(UC);

        if(ULC.size() > 1){
            iPoint = new InterserctionPoint(point);
            delete(LC);
            insert(UClist);
        }

        if (UC.isEmpty()) {
            // sl, sr voisin de gauche et droite de p
            Segment sl = t.findLeftNeighbor(point);
            Segment sr = t.findRightNeighbor(point);

            // Trouver un nouvel événement
            FindNewEvent(sl, sr, point);
        } else {
            // sPrime le segment le plus à gauche de U(p)uC(p)
            // sl segment à gauche de sPrime
            Segment sPrime  = UClist.get(UClist.size()-1);
            Segment sl =     t.findLeftAdjacentSegment(sPrime);
            FindNewEvent(sl,sPrime,point);

            // sSecond le segment le plus à droite de U(p)uC(p)
            // sr segment a droite de sSecond
            Segment sSecond = UClist.get(0);
            Segment sr =      t.findRightAdjacentSegment(sSecond);
            FindNewEvent(sSecond,sr,point);
        }

        for(Segment s : Lp){
            t.delete(s);
        }

        return iPoint != null ? iPoint : point;
    }

    public void delete(Set<Segment> UC) {
        for (Segment segment : UC) {
            t.delete(segment);
        }
    }

    public void insert(List<Segment> insertSegments){
        for(Segment s : insertSegments){
            t.insert(s);
        }
    }


    private void FindNewEvent(Segment sl, Segment sr, Point currentPoint) {
        // Vérifier si les segments sont non nuls
        if (sl != null && sr != null) {
            // Calculer le point d'intersection

            Point intersectionPoint = sl.ComputeIntesectPoint(sr);
            // Vérifier si le point est en dessous de la sweepline avec le dernier point sélectionné
            if (intersectionPoint != null && currentPoint.isHigherThan(intersectionPoint)) {
                // Insérer le point dans la file d'attente des événements
                if(!q.contains(intersectionPoint)){
                    q.insert(intersectionPoint);
                }
            }
        }
    }

    public void InitQ(List<Segment> segments) {
        q = new QTree();
        t = new TTree();
        for (Segment s : segments) {
            q.insert(s.getSPoint());
            q.insert(s.getEPoint());
        }
    }
}
