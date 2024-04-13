package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.point.StartPoint;

public class QTree extends AVLTree{

    public QTree(){
        super();
    }

    @Override
    protected void insertEmpty(Data d) {
        super.insertEmpty(d);
        System.out.println("NEW POINT :" + d.toString());
        setLeftTree(new QTree());
        setRightTree(new QTree());
    }

    @Override
    public void insert(Data d) {
        Point p = ((Point)d).clone();
        if(isEmpty()){
            insertEmpty(p);
        }else{
            Point currentPoint = this.getData();
            if(currentPoint.isHigherThan(p)){

                this.getRightTree().insert(p);
            }else {
                if (currentPoint.equals(p)) {
                    if(p instanceof StartPoint){
                        if(currentPoint instanceof StartPoint){
                            for(Segment s : ((StartPoint) p).getSegments()){
                                ((StartPoint) currentPoint).addSegment(s);
                            }
                        }else{
                            this.setData(p);
                        }
                    }
                }else{
                    this.getLeftTree().insert(p);
                }

            }
        }
        equilibrateAVL();
    }

    public Point getNextPoint(){
        Point minPoint;

        if(isEmpty()){
            return null;
        }

        if(getLeftTree().isEmpty()){
            minPoint = getData();
            AVLTree t = getRightTree();
            setData(t.getData());
            setLeftTree(t.getLeftTree());
            setRightTree(t.getRightTree());
        }else{
            minPoint = getLeftTree().getNextPoint();
        }
        equilibrateAVL();
        return minPoint;
    }

    @Override
    public QTree getLeftTree() {
        return (QTree) this.leftTree;
    }

    @Override
    public QTree getRightTree() {
        return (QTree) this.rightTree;
    }

    @Override
    public Point getData() {
        return (Point) this.data;
    }

   public Boolean contains(Point point) {
        Point cPoint = (Point)this.data;
        if(cPoint == null){
            return false;
        }

        double x = Math.abs(cPoint.getX() - point.getX());
        double y = Math.abs(cPoint.getY() - point.getY());
        if( x < 0.15 && y < 0.15){
            return true;
        }

        if(this.getLeftTree().getData() != null && this.getRightTree().getData() != null){
            return this.getLeftTree().contains(point) || this.getRightTree().contains(point);
        }

        if(this.getLeftTree().getData() != null){
            return this.getLeftTree().contains(point) ;
        }else if(this.getRightTree().getData() != null) {
            return this.getRightTree().contains(point);
        }else {
            return false;
        }
   }
}
