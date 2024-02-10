package com.mapoverlay.model;

public class QTree extends AVLTree{

    public QTree(){
        super();
    }
    private void insertEmpty(Point p){
        setPoint(p);
        setLeftTree(new QTree());
        setRightTree(new QTree());
        setHeight(1);
    }

    @Override
    public void insert(Point p){
        if(isEmpty()){
            insertEmpty(p);
        }else{
            if(this.getPoint().getY() < p.getY() || (this.getPoint().getY() == p.getY() && this.getPoint().getX() > p.getX())){
                this.getLeftTree().insert(p);
            }else {
                if (!(this.getPoint().getY() == p.getY() && this.getPoint().getX() == p.getX())) {
                    this.getRightTree().insert(p);
                }
            }
        }
        equilibrateAVL();
    }

    public Point getNextPoint(){
        Point minPoint;

        if(getLeftTree().isEmpty()){
            minPoint = getPoint();
            AVLTree t = getRightTree();
            setPoint(t.getPoint());
            setLeftTree(t.getLeftTree());
            setRightTree(t.getRightTree());
        }else{
            minPoint = ((QTree)getLeftTree()).getNextPoint();
        }
        equilibrateAVL();
        return minPoint;
    }
}
