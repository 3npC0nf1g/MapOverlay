package com.mapoverlay.model;

import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.StartPoint;
import com.mapoverlay.model.dataStructure.QTree;
import com.mapoverlay.model.dataStructure.TTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        segmentList.add(new Segment(new Point(5,5),new Point(3,3)));
        segmentList.add(new Segment(new Point(1,6),new Point(3,4)));
        segmentList.add(new Segment(new Point(4,5),new Point(5,2)));
        segmentList.add(new Segment(new Point(1,1),new Point(2,0.5F)));
        segmentList.add(new Segment(new Point(7,2),new Point(6,1)));

        FindInterSections(segmentList);
    }

    public void FindInterSections(List<Segment> segmentList){
        for(Segment s: segmentList){
            q.insert(s.getSPoint());
            q.insert(s.getEPoint());
        }

        while (!q.isEmpty()){
            HandleEventPoint(q.getNextPoint());
        }
    }

    public void HandleEventPoint(Point point){
        UpdateT(point);

        Set<Segment> U = t.getSegmentWithUpper(point);
        Set<Segment> L = t.getAdjacentSegmentWithLower(point);
        Set<Segment> C = t.getAdjacentSegmentContains(point);

    }

    private void UpdateT(Point point){
        if (point instanceof StartPoint){
            t.insert(point);
        }
    }

    public void FindNewEvent(Segment sl,Segment sr,Point currentPoint){
        // Créer le point avec ces coord
        Point Point = sl.ComputeIntesectPoint(sr);
        // Check si le point est en dessous de la sweepline avec le dernier point selectionner
        if(currentPoint.isHigherThan(Point)){
            q.insert(Point);
        }
    }
}
