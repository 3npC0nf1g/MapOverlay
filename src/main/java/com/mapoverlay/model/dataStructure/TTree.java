package com.mapoverlay.model.dataStructure;

import com.mapoverlay.model.data.*;

import java.util.HashSet;
import java.util.Set;

public class TTree extends AVLTree{
    private AVLTree root;


    public TTree(){
        super();
        this.root = null; // La racine est initialement nulle car l'arbre est vide
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
        if (root == null || point == null) {
            return; // Arrêtez si l'arbre est vide ou le point est null
        }

        // Appel de la méthode pour supprimer le segment associé au point
        root = deleteSegment(root, point);
    }

    private AVLTree deleteSegment(AVLTree tree, Point point) {
        if (tree == null) {
            return null;
        }

        Segment segment = (Segment) tree.getData();

        // Si le segment contient le point spécifié, supprimez-le de l'arbre
        if (segment.contains(point)) {
            // Si le nœud est une feuille, retournez null pour indiquer qu'il doit être supprimé
            if (tree.isLeaf()) {
                return null;
            }

            // Si le nœud a un seul sous-arbre, retournez le sous-arbre non vide
            if (tree.getLeftTree().isEmpty()) {
                return tree.getRightTree();
            }
            if (tree.getRightTree().isEmpty()) {
                return tree.getLeftTree();
            }

            // Si le nœud a deux sous-arbres, trouvez le nœud successeur (le plus petit nœud dans le sous-arbre droit)
            AVLTree successor = findSuccessor(tree.getRightTree());

            // Remplacez le nœud actuel par le nœud successeur
            tree.setData(successor.getData());

            // Supprimez le nœud successeur du sous-arbre droit
            tree.setRightTree(deleteSegment(tree.getRightTree(), ((Segment) successor.getData()).getEPoint()));
        } else if (segment.getEPoint().isHigherThan(point)) {
            // Si le point est inférieur à l'ePoint du segment, continuez la recherche dans le sous-arbre gauche
            tree.setLeftTree(deleteSegment(tree.getLeftTree(), point));
        } else {
            // Si le point est supérieur à l'ePoint du segment, continuez la recherche dans le sous-arbre droit
            tree.setRightTree(deleteSegment(tree.getRightTree(), point));
        }

        return tree;
    }

    private AVLTree findSuccessor(AVLTree tree) {
        AVLTree current = tree;
        while (!current.getLeftTree().isEmpty()) {
            current = current.getLeftTree();
        }
        return current;
    }


    // Logique pour L(p)

    public Set<Segment> getSegmentsWithLower(Point point) {

            Set<Segment> result = new HashSet<>();
            searchSegmentsWithLower(root, point, result);
            return result;

    }

    private void searchSegmentsWithLower(AVLTree tree, Point point, Set<Segment> result) {
        if (tree == null || point == null) {
            return; // Arrêtez la recherche si l'arbre est nul ou le point est null
        }

        Segment treeNodeSegment = (Segment) tree.getData(); // On récupère un segment dans T

        // On vérifie si le point inférieur du segment est égal au point spécifié
        if (treeNodeSegment.getEPoint().equals(point)) {
            // Si c'est le cas, on ajoute ce segment au résultat
            result.add(treeNodeSegment);
        }

        // Si le point est inférieur au point du nœud de l'arbre, on continue la recherche dans le sous-arbre gauche
        if (point.isLeftOf(treeNodeSegment.getEPoint())) {
            searchSegmentsWithLower(tree.getLeftTree(), point, result);
        } else {
            searchSegmentsWithLower(tree.getRightTree(), point, result);
        }
    }



    // Logique pour C(p)
    public Set<Segment> getSegmentsContains(Point point) {
        Set<Segment> result = new HashSet<>();
        searchSegmentsContains(root, point, result);
        return result;
    }

    private void searchSegmentsContains(AVLTree tree, Point point, Set<Segment> result) {
        if (tree == null || point == null) {
            return;
        }

        Point treeNodePoint = (Point)tree.getData();

        //   Segment segment = (Segment)tree.getData();
        //  if (segment.contains(point)) {
           //  result.add(segment);
           //   }


        // Si le point est égal au point du nœud de l'arbre, on récupère les segments associés au point et on les ajoute au résultat
       if (treeNodePoint.equals(point)) {
            result.addAll(((StartPoint) point).getSegments());
        }

        // Si le point est inférieur au point du nœud de l'arbre, on continue la recherche dans le sous-arbre gauche
        if (point.isLeftOf(treeNodePoint)) {
            searchSegmentsContains(tree.getLeftTree(), point, result);
        } else {
            searchSegmentsContains(tree.getRightTree(), point, result);
        }
    }





}
