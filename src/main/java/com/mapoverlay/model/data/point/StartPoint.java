package com.mapoverlay.model.data.point;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.Segment;

import java.util.ArrayList;
import java.util.List;

public class StartPoint extends Point{
    private List<Segment> segments = new ArrayList<>();;

    public StartPoint(double x, double y) {
        super(x, y);
    }

    public StartPoint(Data d) {
        super((Point)d);
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void addSegment(Segment segment) {
        if(!segments.contains(segment)){
            segments.add(segment);
        }
    }

    @Override
    public StartPoint clone(){
        StartPoint sP = new StartPoint(this);
        for(Segment s : this.getSegments()){
            sP.addSegment(s);
        }
        return sP;
    }
}
