package com.mapoverlay.model.data;

public class StartPoint extends Point{

    private Segment segment;

    public StartPoint(float x, float y) {
        super(x, y);
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }
}
