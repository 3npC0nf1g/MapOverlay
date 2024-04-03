package com.mapoverlay.model.dataStructure;

import com.mapoverlay.model.data.*;

import java.util.HashSet;
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

    // Logique pour L(p)

    private Node root;

    public Set<Segment> getSegmentsWithLower(Point point) {
        Set<Segment> result = new HashSet<>();
        searchSegmentsWithLower(root, point, result);
        return result;
    }

    private void searchSegmentsWithLower(Node node, Point point, Set<Segment> result) {
        if (node == null) {
            return; // Arrêtez la recherche si le nœud est nul
        }

        Segment segment = node.getData();
        Point ePoint = segment.getEPoint();

        // Si le point inférieur du segment est égal au point spécifié, ajoutez le segment au résultat
        if (ePoint.equals(point)) {
            result.add(segment);
        }

        // Si le point est inférieur à l'ePoint du segment, recherchez dans le sous-arbre gauche
        if (point.isLeftOf(ePoint)) {
            searchSegmentsWithLower(node.getLeft(), point, result);
        }
    }


    // Logique pour C(p)
    public Set<Segment> getSegmentsContains(Point point) {
        Set<Segment> result = new HashSet<>();
        searchSegmentsContains(root, point, result);
        return result;
    }

    private void searchSegmentsContains(Node node, Point point, Set<Segment> result) {
        if (node == null) {
            return;
        }

        Segment segment = node.getData();

        // Vérifie si le segment contient le point spécifié
        if (segment.contains(point)) {
            result.add(segment);
        }

        // Détermine la direction orientée en fonction de la position du point
        boolean searchLeft = point.getY() <= segment.getEPoint().getY();

        // Recherche dans le sous-arbre approprié
        if (searchLeft) {
            searchSegmentsContains(node.getLeft(), point, result);
        } else {
            searchSegmentsContains(node.getRight(), point, result);
        }
    }




    public Set<Segment> getAdjacentSegmentContains(Point point) {
        return null;
    }
}
