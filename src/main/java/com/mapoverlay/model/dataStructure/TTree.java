package com.mapoverlay.model.dataStructure;

import com.mapoverlay.model.data.*;

import java.util.List;
import java.util.Set;

public class TTree extends AVLTree{

    public TTree(){
        super();
    }

    @Override
    protected void insertEmpty(Data d) {
        super.insertEmpty(d);
        setLeftTree(new TTree());
        setRightTree(new TTree());
    }

    @Override
    public void insert(Data data) {
        Segment insertedSegment = (Segment)data;

        if(isEmpty()){
            insertEmpty(insertedSegment);
        }else{
            Segment currentSegment = (Segment)this.getData();
            if(isLeaf()){
                if(insertedSegment.isLeftOf(currentSegment)){
                    insertSegment(insertedSegment,currentSegment);
                }else{
                    insertSegment(currentSegment,insertedSegment);
                }
            }else{
                AVLTree subtree = insertedSegment.isLeftOf(currentSegment) ? getLeftTree() : getRightTree();
                subtree.insert(insertedSegment);
            }
        }
        equilibrateAVL();
        setData(getMaxOfTree(getLeftTree()));
    }

    private void insertSegment(Data ls,Data rs){
        getLeftTree().insertEmpty(ls);
        getRightTree().insertEmpty(rs);
    }

    // Mettre à jour la node intern avec le max du sous-arbre gauche
    private Data getMaxOfTree(AVLTree t){
        if(t.getRightTree().isEmpty()){
            return getData();
        }else{
            return getMaxOfTree(t.getRightTree());
        }
    }

    public void delete(Point point) {

    }

    public Set<Segment> getSegmentsWithLower(Point point,Set<Segment> result) {






        Segment currentSegment = ((Segment)getData());
        Point sPoint = currentSegment.getSPoint();
        Point ePoint = currentSegment.getEPoint();

        if(!isLeaf()){
            if(sPoint.isLeftOf(point)){

            }
        }

        //if(isLeaf()){
        //  if(point.equals(ePoint)){
        //    result.add(currentSegment);
        //  }
        //}else {
        //    if(sPoint.getX() <= point.getX()){
        //        ((TTree)getRightTree()).getSegmentWithLower(point,result);
        //        if(sPoint.getX() == point.getX()){
        //            ((TTree)getLeftTree()).getSegmentWithLower(point,result);
        //        }
        //    }else{
        //        ((TTree)getLeftTree()).getSegmentWithLower(point,result);
        //    }
        //}
        return result;
    }

    public

    public Set<Segment> getAdjacentSegmentContains(Point point) {
        return null;
    }
}
