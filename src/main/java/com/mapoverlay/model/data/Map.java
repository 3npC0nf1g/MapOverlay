package com.mapoverlay.model.data;

import javafx.scene.paint.Color;

import java.util.List;

public class Map {
    private List<Segment> segments;
    private Color color;

    private boolean extended = true;

    public Map(List<Segment> segments, Color color){
        this.segments = segments;
        this.color = color;
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
