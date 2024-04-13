package com.mapoverlay.model;

import com.mapoverlay.model.data.*;
import com.mapoverlay.model.data.point.InterserctionPoint;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.point.StartPoint;
import com.mapoverlay.model.datastructure.QTree;
import com.mapoverlay.model.datastructure.TTree;

import java.util.*;


/**
 * Représente une superposition de carte utilisée pour détecter les intersections entre les segments et effectuer d'autres opérations spatiales.
 */
public class MapOverlay {
    boolean selfIntersection = false;
    QTree q = new QTree();
    TTree t = new TTree();

    /**
     * Trouve le prochain point lors de la détection d'un point d'intersection.
     * @return Le point actuel si trouvé, sinon null.
     */
    public Point FindInterSectionsStep(){
        Point p = q.getNextPoint();
        if(p != null){
            return HandleEventPoint(p);
        }else {
            return null;
        }
    }

    /**
     * Gère un point d'événement dans l'algorithme de sweepline.
     *
     * @param point Le point d'événement actuel de la sweepline.
     * @return Retourne le point d'événement actuel et l'intensité sous forme de point d'intersection s'il s'agit d'une intersection.
     */
    public Point HandleEventPoint(Point point) {

        t.setCurrentPoint(point);
        InterserctionPoint iPoint = null;

        Set<Segment> Up = new LinkedHashSet<>();

        if(point instanceof StartPoint){
            List<Segment> insertSegments = ((StartPoint) point).getSegments();
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

        if(ULC.size() > 1){

            if(selfIntersection){
                iPoint = new InterserctionPoint(point);
            }else{
                List<Segment> ULCList = new ArrayList<>(ULC);

                for (Segment s1 : ULCList){
                    boolean hasBreak = false;
                    for (Segment s2 : ULCList){
                        if(s1.getMapId() != s2.getMapId()){
                            iPoint = new InterserctionPoint(point);
                            hasBreak = true;
                            break;
                        }
                    }
                    if (hasBreak){
                        break;
                    }
                }
            }
            delete(LC);
            insert(UC);
        }else{
            insert(Up);
        }

        if (UC.isEmpty()) {

            Segment sl = t.findLeftNeighbor(point);
            Segment sr = t.findRightNeighbor(point);


            FindNewEvent(sl, sr, point);
        } else {
            List<Segment> UClist = new ArrayList<>(UC);
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

    public void insert(Set<Segment> insertSegments){
        for(Segment s : insertSegments){
            t.insert(s);
        }
    }


    private void FindNewEvent(Segment sl, Segment sr, Point currentPoint) {
        if (sl != null && sr != null) {
            Point intersectionPoint = sl.ComputeIntesectPoint(sr);
            if (intersectionPoint != null && currentPoint.isHigherThan(intersectionPoint)) {
                if(!q.contains(intersectionPoint)){
                    q.insert(intersectionPoint);
                }
            }
        }
    }

    /**
     * Initialise la structure de données QTree avec les points de début et de fin de segments donnés.
     * Initialise également la structure de données TTree.
     *
     * @param segments Une liste de segments pour initialiser la structure de données QTree.
     */
    public void InitQ(List<Segment> segments) {
        q = new QTree();
        t = new TTree();
        for (Segment s : segments) {
            Point sP = s.getSPoint();
            Point eP = s.getEPoint();
            q.insert(sP);
            q.insert(eP);
        }
    }
    /**
     * Renvoie l'instance actuelle de la structure de données QTree.
     * @return L'instance de la structure de données QTree.
     */
    public QTree getQTree() {
        return this.q;
    }

    /**
     * Renvoie l'instance actuelle de la structure de données TTree.
     * @return L'instance de la structure de données TTree.
     */
    public TTree getTTree() {
        return this.t;
    }

    /**
     * Inverse l'état du drapeau selfIntersection.
     * Si le drapeau est vrai, le système considérera les auto-intersections, sinon il les ignorera.
     */
    public void changeSelfInter() {
        selfIntersection = !selfIntersection;
    }
}
