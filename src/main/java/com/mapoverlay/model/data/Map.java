package com.mapoverlay.model.data;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Map {
    private List<Segment> segments;
    private Color color;
    private final int id;
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private boolean extended = true;

    public Map(List<Segment> segments, Color color){
        this.id = idCounter.incrementAndGet();
        this.segments = segments;
        this.color = color;
    }

    public int getId(){
        return id;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void addSegment(Segment segment){
        segments.add(segment);
    }

    public void deleteSegment(Segment segment){
        segments.remove(segment);
    }

    public void clearMap(){
        segments.clear();
    }

    public void setExtended(boolean extended){
        this.extended = extended;
    }
    public boolean getExtended() {
        return extended;
    }
}
