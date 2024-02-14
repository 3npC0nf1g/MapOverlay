package com.mapoverlay.model.dataStructure;

import com.mapoverlay.model.data.*;
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
        if(isEmpty()){
            insertEmpty(data);
            return;
        }

        StartPoint np = ((StartPoint)data);
        StartPoint cp = ((StartPoint)getData());

        if(isLeaf()){
            if(np.getX() <= cp.getX())
                insertSegment(np,cp);
            else
                insertSegment(cp,np);
        }else{
            if(np.getX() <= cp.getX())
                getLeftTree().insert(data);
            else
                getRightTree().insert(data);
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

    public Set<Segment> getSegmentWithUpper(Point point) {
        return null;
    }

    public Set<Segment> getAdjacentSegmentContains(Point point) {
        return null;
    }

    public Set<Segment> getAdjacentSegmentWithLower(Point point) {
        return null;
    }
}
