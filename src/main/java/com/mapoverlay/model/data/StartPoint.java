package com.mapoverlay.model.data;

import java.util.ArrayList;
import java.util.List;

public class StartPoint extends Point{
    private List<Segment> segments = new ArrayList<>();;

    public StartPoint(double x, double y) {
        super(x, y);
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }
}
