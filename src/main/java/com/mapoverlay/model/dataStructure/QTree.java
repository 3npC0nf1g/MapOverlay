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
        setLeftTree(new QTree());
        setRightTree(new QTree());
    }

    @Override
    public void insert(Data data){
        Point p = (Point)data;
        if(isEmpty()){
            insertEmpty(p);
        }else{
            Point currentPoint = (Point)this.getData();
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
            minPoint = (Point)getData();
            AVLTree t = getRightTree();
            setData(t.getData());
            setLeftTree(t.getLeftTree());
            setRightTree(t.getRightTree());
        }else{
            minPoint = ((QTree)getLeftTree()).getNextPoint();
        }
        equilibrateAVL();
        return minPoint;
    }
}
