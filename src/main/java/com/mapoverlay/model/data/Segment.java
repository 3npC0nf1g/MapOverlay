package com.mapoverlay.model.data;
import static java.lang.Math.sqrt;

public class Segment extends Data {
    private final StartPoint sPoint;
    private final Point ePoint;

    // Constructor
    public Segment(Point p1,Point p2) {
        if(p1.isHigherThan(p2)){
            this.sPoint = new StartPoint(p1.getX(),p1.getY());
            this.ePoint = p2;
        }else{
            this.sPoint = new StartPoint(p2.getX(),p2.getY());
            this.ePoint = p1;
        }
        this.sPoint.addSegment(this);
    }

    // getter
    public StartPoint getSPoint() {
        return sPoint;
    }

    public Point getEPoint() {
        return ePoint;
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
        return false;
    }

    public boolean isLeftOf(Segment segment){
        return this.sPoint.isLeftOf(segment.sPoint);
    }

    // USE FOR CALCULATE INTERSECTION
    public Point ComputeIntesectPoint(Segment s){
        double den = this.getA() * s.getB() - this.getB() * s.getA();
        double numX = this.getC() * s.getB() - this.getB() * s.getC();
        double numY = this.getA() * s.getC() - this.getC() * s.getA();

        if(den != 0){ // Normalement pas possible car on appelle la fonction car on sait qu'il y a une intersection mais on sait jamais :x
            double X = numX / den;
            double Y = numY / den;

            return new Point(X,Y);
        } 
        return null;
    }

    @Override
    public String toString() {
        return sPoint.getX() + " " + sPoint.getY() + " " + ePoint.getX() + " " + ePoint.getY();
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
}
