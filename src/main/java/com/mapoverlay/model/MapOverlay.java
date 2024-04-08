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
        Point nPoint = point;

        Set<Segment> Up = new LinkedHashSet<>();
        if (point instanceof StartPoint) {
            Up.addAll(((StartPoint) point).getSegments());
            for (Segment segment : Up) { // O(n)
                if (segment != null){
                    t.insert(segment);
                }
            }
        }

        Set<Segment> Lp = t.getSegmentsWithLower(point); // O(n)
        Set<Segment> Cp = t.getSegmentsContains(point); // O(n)

        Set<Segment> ULC = new LinkedHashSet<>(Lp);
        ULC.addAll(Up);
        ULC.addAll(Cp);

        Set<Segment> LC = new LinkedHashSet<>(Lp);
        LC.addAll(Cp);

        Set<Segment> UC = new LinkedHashSet<>(Up);
        UC.addAll(Cp);
        List<Segment> UClist = new ArrayList<>(UC);

        if (ULC.size() > 1) {
            nPoint = new InterserctionPoint(point);
            t.delete(LC);

            for(int i = 0;i < UC.size();i++){
                Segment newSegment = new Segment(point,UClist.get(i).getEPoint());
                UClist.set(i,newSegment);
                t.insert(newSegment);
            }


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
            Segment sr =      t.findRightAdjacentSegment(sSecond);;
            FindNewEvent(sSecond,sr,point);
        }

        for(Segment s : Lp){
            t.delete(s);
        }

        return nPoint;
    }


    private void FindNewEvent(Segment sl, Segment sr, Point currentPoint) {
        // Vérifier si les segments sont non nuls
        if (sl != null && sr != null) {
            // Calculer le point d'intersection
            Point intersectionPoint = sl.ComputeIntesectPoint(sr);
            // Vérifier si le point est en dessous de la sweepline avec le dernier point sélectionné
            if (intersectionPoint != null && currentPoint.isHigherThan(intersectionPoint)) {
                // Insérer le point dans la file d'attente des événements
                q.insert(intersectionPoint);
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
