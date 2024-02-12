package com.mapoverlay.model;

public class TTree extends AVLTree {
    TTree(){
        super();
    }

    @Override
    protected void insertEmpty(Data d) {
        super.insertEmpty(d);
        setLeftTree(new TTree());
        setRightTree(new TTree());
    }

    @Override
    protected void insert(Data data) {
        Segment s = (Segment)data;
        if(isEmpty()){
            insertEmpty(data);
        }else{

        }
    }


}
