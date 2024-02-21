package com.mapoverlay.model.data;

public class Point extends Data {
    private final float x,y;

    public Point(float x,float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
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
}
