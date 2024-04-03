package com.mapoverlay.model.data;

public class Point extends Data {
    private final double x,y;

    public Point(double x,double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isHigherThan(Point point){
        if(point == null)
            return false;
        return this.getY() > point.getY() || (this.getY() == point.getY() && this.getX() < point.getX());
    }

    public boolean isLeftOf(Point p){
        return this.getX() <= p.getX();
    }

    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!(object instanceof Point))
            return false;

        return this.x == ((Point) object).x && this.y == ((Point) object).y;
    }


    @Override
    public String toString() {
        return "(" + getX()+","+getY()+")";
    }
}
