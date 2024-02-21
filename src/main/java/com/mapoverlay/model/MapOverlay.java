package com.mapoverlay.model;

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
        segmentList.add(new Segment(new Point(5,5),new Point(3,4)));
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
        Set<Segment> U = new HashSet<>();
        if(point instanceof StartPoint){
            U.addAll(((StartPoint) point).getSegments());
        }
        Set<Segment> L = t.getSegmentWithLower(point);
        Set<Segment> C = t.getSegmentContains(point);

        Set<Segment> UC = UnionSet(U, C);

        // equivalent de regarder si ULC > 1
        if(UnionSet(UC,L).size() > 1){

        }

        if(UnionSet(U, C).isEmpty()){
            // sl, sr voisin de gauche et droite de pµ
            Segment sl;
            Segment sr;
            FindNewEvent(sl,sr,point);
        }else{
            // s' le segment le plus à gauche de U,C
            // sl segment à gauche de p
            Segment sl;
            Segment sprime;
            FindNewEvent(sl,sprime,point);
            // s'' le segment le plus à droite de U,C
            // sr segment a droite de p
            Segment sdprime;
            Segment sr;
            FindNewEvent(sdprime,sr,point);
        }
    }

    private Set<Segment> UnionSet(Set<Segment> a,Set<Segment> b){
        Set<Segment> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    private void FindNewEvent(Segment sl,Segment sr,Point currentPoint){
        // Créer le point avec ces coord
        Point Point = sl.ComputeIntesectPoint(sr);
        // Check si le point est en dessous de la sweepline avec le dernier point selectionner
        if(currentPoint.isHigherThan(Point)){
            q.insert(Point);
        }
    }
}
