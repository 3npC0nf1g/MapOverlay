package com.mapoverlay.model.dataStructure;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.StartPoint;

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
        Point p = (Point)d;
        if(isEmpty()){
            insertEmpty(p);
        }else{
            Point currentPoint = this.getData();
            if(currentPoint.isHigherThan(p)){
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
                    this.getRightTree().insert(p);
                }
            }else {
                this.getLeftTree().insert(p);
            }
        }
        equilibrateAVL();
    }

    public Point getNextPoint(){
        Point minPoint;

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

   public boolean contains(Point point) {
        if(this.data.equals(point)){
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
