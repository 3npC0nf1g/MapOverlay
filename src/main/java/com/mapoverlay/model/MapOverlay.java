package com.mapoverlay.model;

import java.util.ArrayList;
import java.util.List;

public class MapOverlay {

    /**
     * Structure de données T
     * Structure de données Q
     */

    QTree q = new QTree();


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

    public void FindInterSections(List<Segment> segmentList){
        for(Segment s: segmentList){
            q.insert(s.getP1());
            q.insert(s.getP2());
        }
    }

    public void HandleEventPoint(){

    }

    public void FindNewEvent(){

    }
}
