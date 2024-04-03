package com.mapoverlay.model;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.StartPoint;
import com.mapoverlay.model.dataStructure.QTree;
import com.mapoverlay.model.dataStructure.TTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapOverlay {
    QTree q = new QTree();
    TTree t = new TTree();

    public void FindInterSections(List<Segment> segmentList) {
        for (Segment s : segmentList) {
            q.insert(s.getSPoint());
            q.insert(s.getEPoint());
        }

        while (!q.isEmpty()) {
            HandleEventPoint(q.getNextPoint());
        }
    }

    public void HandleEventPoint(Point point) {

        Set<Segment> Up = new HashSet<>();
        if (point instanceof StartPoint) {
            Up.addAll(((StartPoint) point).getSegments());
            for (Segment segment : Up) { // O(n)
                t.insert(segment);
            }
        }

        Set<Segment> Lp = t.getSegmentsWithLower(point); // O(n)
        Set<Segment> Cp = t.getSegmentsContains(point); // O(n)

        Set<Segment> ULC = new HashSet<>(Lp);
        ULC.addAll(Up);
        ULC.addAll(Cp);

        Set<Segment> LC = new HashSet<>(Lp);
        ULC.addAll(Cp);

        Set<Segment> UC = new HashSet<>(Up);
        ULC.addAll(Cp);

        // Si L(p)∪U(p)∪C(p) contient plus d'un segment
        if (ULC.size() > 1) {

            // renvoyer le point comme point d'intersection
            // return du point comme point d'intersection
            t.delete(LC); // suppression des segments de L(p)UC(p) dans T
            for (Segment segment : UC) { // insertion des segments de U(p)UC(p) dans T
                t.insert(segment);
            }
        }

        if (UC.isEmpty()) {

            // sl, sr voisin de gauche et droite de p
            Segment sl = t.getLeftNeighbor(point);
            Segment sr = t.getRightNeighbor(point);

            // Trouver un nouvel événement
            FindNewEvent(sl, sr, point);
        } else {
            // sPrime le segment le plus à gauche de U(p)uC(p)
            // sl segment à gauche de sPrime
            if (UC.contains(t.getLeftmostSegment(point))){
                Segment sPrime = t.getLeftmostSegment(point);
                Segment sl =     t.getLeftNeighborSegment(sPrime);
                FindNewEvent(sl,sPrime,point);
            }

            // sSecond le segment le plus à droite de U(p)uC(p)
            // sr segment a droite de sSecond
              Segment sSecond = t.getRightmostSegment(point) ;
              Segment sr =      t.getRightNeighborSegment(sSecond);;
              FindNewEvent(sSecond,sr,point);
        }
    }


    private void FindNewEvent(Segment sl, Segment sr, Point currentPoint) {
        // Vérifier si les segments sont non nuls
        if (sl != null && sr != null) {
            // Calculer le point d'intersection
            Point intersectionPoint = sl.ComputeIntesectPoint(sr);
            // Vérifier si le point est en dessous de la sweepline avec le dernier point sélectionné
            if (currentPoint.isHigherThan(intersectionPoint)) {
                // Insérer le point dans la file d'attente des événements
                q.insert(intersectionPoint);
            }
        }
    }

}
