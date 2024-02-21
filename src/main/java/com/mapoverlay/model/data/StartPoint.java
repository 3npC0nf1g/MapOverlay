package com.mapoverlay.model.data;

import java.util.List;

public class StartPoint extends Point{

    private List<Segment> segments;

    public StartPoint(float x, float y) {
        super(x, y);
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }
}
