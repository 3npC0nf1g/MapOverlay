package com.mapoverlay.model;

import javafx.util.Pair;

public class Segment {
    private Point p1,p2;

    Segment(float x1,float y1,float x2,float y2) {
        this.p1 = new Point(x1,y1);
        this.p2 = new Point(x2,y2);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }
}
