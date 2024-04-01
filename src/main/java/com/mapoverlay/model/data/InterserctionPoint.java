package com.mapoverlay.model.data;

import java.util.ArrayList;
import java.util.List;

public class InterserctionPoint extends Point{

    private List<Segment> intersectSegmentsLists;
    public InterserctionPoint(double x, double y) {
        super(x, y);
        intersectSegmentsLists = new ArrayList<>();
    }



}
