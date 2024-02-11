package com.mapoverlay.model;

import javafx.util.Pair;

public class Segment {
    private final Point p1,p2;

    Segment(float x1,float y1,float x2,float y2) {
        if(y1 > y2 || (y1 == y2 && x1 < x2)){
            this.p1 = new Point(x1,y1);
            this.p2 = new Point(x2,y2);
        }else{
            this.p1 = new Point(x2,y2);
            this.p2 = new Point(x1,y1);
        }
        this.p1.setSegment(this);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    // USE FOR CALCULATE INTERSECTION
    public float getA(){
        return p1.getY() - p2.getY();
    }
    public float getB(){
        return p2.getX() - p1.getX();
    }

    public float getC(){
        return -((p1.getX() * p2.getY())-(p2.getX() * p1.getY()));
    }
}
