package com.mapoverlay.model.data;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.point.StartPoint;

public class Segment extends Data {
    private final int mapId;
    private final Point sPoint;
    private final Point ePoint;

    // Constructor
    public Segment(Point p1,Point p2){
        this(p1,p2,-1);
    }

    public Segment(Point p1,Point p2,int id) {
        mapId = id;
        if(p1.isHigherThan(p2)){
            this.sPoint = new StartPoint(p1.getX(),p1.getY());
            this.ePoint = p2;
        }else{
            this.sPoint = new StartPoint(p2.getX(),p2.getY());
            this.ePoint = p1;
        }
        ((StartPoint)this.sPoint).addSegment(this);
    }

    // getter
    public Point getSPoint() {
        return sPoint;
    }

    public Point getEPoint() {
        return ePoint;
    }

    public Point getIntersectSweep(double currentY){
        double x1 = sPoint.getX();
        double y1 = sPoint.getY();
        double x2 = ePoint.getX();
        double y2 = ePoint.getY();
        if(currentY >= y2 && currentY <= y1){
            return new Point(Math.round((((currentY - y1) / (y2 - y1)) * (x2 - x1) + x1)*100)/100, currentY);
        }else {
            return new Point(0,0);
        }
    }

    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!(object instanceof Segment))
            return false;

        return ((Segment) object).getSPoint().equals(this.sPoint) && ((Segment) object).getEPoint().equals(this.ePoint);
    }


    public boolean contains(Point point){
        double ab = distance(sPoint.getX(), sPoint.getY(),ePoint.getX(), ePoint.getY());
        double ac = distance(sPoint.getX(), sPoint.getY(), point.getX(), point.getY());
        double cb = distance(ePoint.getX(), ePoint.getY(), point.getX(), point.getY());
        double ad = ac + cb;

        return Math.abs(ab - ad) < 0.001;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    public boolean isLeftOf(Segment segment,Point point){
        if(Math.abs(segment.sPoint.getY() - segment.ePoint.getY()) < 1 && this.contains(point) && Math.abs(this.sPoint.getY() - this.ePoint.getY()) < 1){
            return this.ePoint.isLeftOf(segment.ePoint);
        }else if(this.contains(point) && Math.abs(this.sPoint.getY() - this.ePoint.getY()) < 1){
            return point.isLeftOf(segment.getIntersectSweep(point.getY()));
        }else if(Math.abs(segment.sPoint.getY() - segment.ePoint.getY()) < 1){
            return this.getIntersectSweep(point.getY()).isLeftOf(point);
        }
        return this.getIntersectSweep(point.getY()).isLeftOf(segment.getIntersectSweep(point.getY()));
    }

    // USE FOR CALCULATE INTERSECTION
    public Point ComputeIntesectPoint(Segment s){
        double den = this.getA() * s.getB() - this.getB() * s.getA();
        double numX = this.getC() * s.getB() - this.getB() * s.getC();
        double numY = this.getA() * s.getC() - this.getC() * s.getA();

        if(den != 0){ // Normalement pas possible car on appelle la fonction car on sait qu'il y a une intersection mais on sait jamais :x
            double X = (double) Math.round((numX / den) * 100) /100;
            double Y = (double) Math.round((numY / den) * 100) /100;

            Point p = new Point(X,Y);

            if(this.contains(p) && s.contains(p)){
                return p;
            }else {
                return null;
            }


        } 
        return null;
    }

    private double getA(){
        return sPoint.getY() - ePoint.getY();
    }
    private double getB(){
        return ePoint.getX() - sPoint.getX();
    }
    private double getC(){
        return -((sPoint.getX() * ePoint.getY())-(ePoint.getX() * sPoint.getY()));
    }

    @Override
    public String toString() {
        return sPoint.getX() + " " + sPoint.getY() + " " + ePoint.getX() + " " + ePoint.getY();
    }

    public int getMapId() {
        return mapId;
    }
}
