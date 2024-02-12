package com.mapoverlay.model;

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
            if(currentPoint.getY() < p.getY() || (currentPoint.getY() == p.getY() && currentPoint.getX() > p.getX())){
                this.getLeftTree().insert(p);
            }else {
                if (!(currentPoint.getY() == p.getY() && currentPoint.getX() == p.getX())) {
                    this.getRightTree().insert(p);
                }
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
