package com.mapoverlay.model.dataStructure;

import com.mapoverlay.model.data.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class TTree extends AVLTree{
    private TTree parent = null;

    @Override
    protected void insertEmpty(Data d) {
        super.insertEmpty(d);
        setLeftTree(new TTree());
        setRightTree(new TTree());
    }
    @Override
    public TTree getLeftTree() {
        return (TTree) this.leftTree;
    }

    @Override
    public TTree getRightTree() {
        return (TTree) this.rightTree;
    }

    @Override
    public Segment getData() {
        return (Segment) this.data;
    }

    @Override
    public void insert(Data data) {
        Segment insertedSegment = (Segment)data;

        if(isEmpty()){
            insertEmpty(insertedSegment);
            equilibrateAVL();
        }else{
            Segment currentSegment = this.getData();
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
            equilibrateAVL();
            setData(getMaxOfTree(getLeftTree()));
        }

    }









    public void printTree() {
        printTreeHelper(this, 0, "ROOT");
    }

    private void printTreeHelper(TTree node, int depth, String prefix) {
        if (node == null) {
            return;
        }

        // Imprimer le nœud actuel avec une indentation en fonction de la profondeur
        String indentation = "";
        for (int i = 0; i < depth; i++) {
            indentation += "    "; // Quatre espaces pour chaque niveau de profondeur
        }
        System.out.println(indentation + prefix + " " + node.getData());

        // Appeler récursivement la méthode pour imprimer les sous-arbres gauche et droit
        printTreeHelper(node.getLeftTree(), depth + 1, "L:");
        printTreeHelper(node.getRightTree(), depth + 1, "R:");
    }








    private void insertSegment(Data ls,Data rs){
        getLeftTree().insertEmpty(ls);
        getRightTree().insertEmpty(rs);
    }

    // Mettre à jour la node intern avec le max du sous-arbre gauche
    private Segment getMaxOfTree(TTree t){
        if(t.getRightTree().isEmpty()){
            return t.getData();
        }else{
            return getMaxOfTree(t.getRightTree());
        }
    }

    private Segment getMinOfTree(TTree t){
        if(t.getLeftTree().isEmpty()){
            return t.getData();
        }else{
            return getMinOfTree(t.getLeftTree());
        }
    }

    public void delete(Set<Segment> UC) {
        if (UC == null || UC.isEmpty()) {
            return; // Arrêtez si l'arbre est vide ou l'ensemble UC est null ou vide
        }

        // Parcourir tous les segments dans l'ensemble UC et les supprimer de l'arbre
        for (Segment segment : UC) {
            delete(segment);
        }
    }

    public void delete(Segment segment){
        Segment currentSegment = this.getData();
        if(this.isLeaf() && currentSegment.equals(segment)){
            OverwriteTTree(null,null,null);
        }else{
            TTree leftTree = this.getLeftTree();
            TTree rightTree = this.getRightTree();

            if(leftTree.isLeaf() && rightTree.isLeaf()){
                if(leftTree.getData().equals(segment)){
                    OverwriteTTree(rightTree.getData(),new TTree(),new TTree());
                } else if (rightTree.getData().equals(segment)) {
                    OverwriteTTree(leftTree.getData(),new TTree(),new TTree());
                }
            } else if (leftTree.isLeaf()) {
                if(leftTree.getData().equals(segment)){
                    OverwriteTTree(rightTree.getData(), rightTree.getLeftTree(), rightTree.getRightTree(),true);
                }else{
                    rightTree.delete(segment);
                }
            } else if (rightTree.isLeaf()) {
                if(rightTree.getData().equals(segment)){
                    OverwriteTTree(leftTree.getData(), leftTree.getLeftTree(), leftTree.getRightTree(),false);
                }else{
                    leftTree.delete(segment);
                }
            }else {
                if(currentSegment.isLeftOf(segment)){
                    if(currentSegment.equals(segment)){
                        leftTree.delete(segment);
                    }else {
                        rightTree.delete(segment);
                    }
                }else {
                    leftTree.delete(segment);
                }
            }
        }
        equilibrateAVL();
    }

    private void OverwriteTTree(Data data,TTree leftTree,TTree rightTree){
        OverwriteTTree(data,leftTree,rightTree,true);
    }
    private void OverwriteTTree(Data data,TTree leftTree,TTree rightTree,boolean leftBefore){
        this.setData(data);
        if(leftBefore){
            this.setLeftTree(leftTree);
            this.setRightTree(rightTree);
        }else {
            this.setRightTree(rightTree);
            this.setLeftTree(leftTree);
        }
        this.computeHeight();
    }

    // Logique pour L(p)
    public Set<Segment> getSegmentsWithLower(Point point) {

        Set<Segment> result = new LinkedHashSet<>();
        searchSegmentsWithLower(this, point, result);
        return result;

    }

    private void searchSegmentsWithLower(AVLTree tree, Point point, Set<Segment> result) {
        if (tree.getData() == null || point == null) {
            return; // Arrêtez la recherche si l'arbre est nul ou le point est null
        }

        Segment treeNodeSegment = (Segment) tree.getData(); // On récupère un segment dans T

        // On vérifie si le point inférieur du segment est égal au point spécifié
        if (treeNodeSegment.getEPoint().equals(point)) {
            // Si c'est le cas, on ajoute ce segment au résultat
            result.add(treeNodeSegment);
        }

        searchSegmentsWithLower(tree.getLeftTree(), point, result);
        searchSegmentsWithLower(tree.getRightTree(), point, result);
    }

    // Logique pour C(p)
    public Set<Segment> getSegmentsContains(Point point) {
        Set<Segment> result = new LinkedHashSet<>();
        searchSegmentsContains(point, result);
        return result;
    }

    private void searchSegmentsContains(Point point, Set<Segment> result) {
        if (this.getData() == null || point == null) {
            return;
        }

        Segment segment =  this.getData();

        if(isLeaf()){
            if(segment.contains(point)){
                if(!segment.getSPoint().equals(point) && !segment.getEPoint().equals(point)){
                    result.add(segment);
                }
            }
        }else {
            if(segment.getSPoint().isLeftOf(point)){
                if(segment.contains(point)){
                    getLeftTree().searchSegmentsContains(point,result);
                    getRightTree().searchSegmentsContains(point,result);
                }else {
                    getRightTree().searchSegmentsContains(point,result);
                }
            }else {

                getLeftTree().searchSegmentsContains(point,result);
            }
        }
    }

    public Segment findLeftNeighbor(Point point) {
        Segment currentSegment = getData();
        Point currentPoint = currentSegment.getSPoint();

        if(isLeaf()){
            return getData();
        }else {
            if(currentPoint.isLeftOf(point)){
                if(currentPoint.equals(point)){
                    Segment rightSegment = getRightTree().getData();
                    if(rightSegment.getSPoint().equals(point)){
                        return getLeftTree().findLeftNeighbor(point);
                    }else {
                        return getData();
                    }
                }else {
                    return  getData();
                }
            }else {
                return getLeftTree().findLeftNeighbor(point);
            }
        }
    }

   public Segment findRightNeighbor(Point point) {
        Segment currentSegment = getData();
        Point currentPoint = currentSegment.getSPoint();

        if (isLeaf()) {
            if (currentPoint.isLeftOf(point)) {
                // Recherche du voisin droit dans le sous-arbre droit
                if (getRightTree() != null) {
                    return getRightTree().findRightMost();
                }
                return null; // Aucun voisin droit trouvé
            } else {
                return getData();
            }
        } else {
            if (currentPoint.isLeftOf(point)) {
                return getRightTree().findRightNeighbor(point);
            } else {
                 return getLeftTree().findRightNeighbor(point);
            }
        }
    }

    private Segment findRightMost() {
        if (getRightTree() == null) {
            return getData();
        }
        return getRightTree().findRightMost();
    }



    public Segment findLeftAdjacentSegment(Segment segment){
       TTree leaf = findLeave(segment);
       if (leaf != null && leaf.getData() != null) {
           return findLeft(leaf);
       }
       return null;
   }

   public Segment findRightAdjacentSegment(Segment segment){
       TTree leaf = findLeave(segment);
       if (leaf != null && leaf.getData() != null) {
           return findRight(leaf);
       }
       return null;
   }

    private Segment findRight(TTree current) {
        TTree parent = current.parent;
        current.parent = null;
        if(parent != null){
            if(parent.rightTree.equals(current)){
                return findRight(parent);
            }else{
                return getMinOfTree(parent.getRightTree());
            }
        }else {
            return null;
        }
    }

    public Segment findLeft(TTree current){
       TTree parent = current.parent;
       current.parent = null;
       if(parent != null){
           if(parent.leftTree.equals(current)){
               return findLeft(parent);
           }else{
               return getMaxOfTree(parent.getLeftTree());
           }
       }else {
           return null;
       }
   }

    public TTree findLeave(Segment segment) {
        // Vérification initiale pour s'assurer que le segment donné n'est pas null
        if (segment == null) {
            return null; // Retourne null si le segment donné est null
        }

        Segment currentSegment = getData();
        if (currentSegment == null) {
            return null; // Retourne null si les données du nœud actuel sont null
        }

        if (currentSegment.equals(segment) && isLeaf()) {
            return this;
        }

        if (currentSegment.isLeftOf(segment)) {
            if (currentSegment.equals(segment)) {
                TTree leftTree = getLeftTree();
                if (leftTree == null) {
                    leftTree = new TTree(); // Crée un nouvel enfant gauche si non initialisé
                    setLeftTree(leftTree); // Définit le parent de l'enfant
                }
                leftTree.setParent(this);
                if (leftTree.isLeaf()) {
                    return leftTree;
                } else {
                    return getMaxOfTreeWithParent(leftTree);
                }
            } else {
                TTree rightTree = getRightTree();
                if (rightTree == null) {
                    rightTree = new TTree(); // Crée un nouvel enfant droit si non initialisé
                    setRightTree(rightTree); // Définit le parent de l'enfant
                }
                rightTree.setParent(this);
                if (rightTree.getData() == null) {
                    return null; // Retourne null si les données du nœud enfant sont null
                }
                if (rightTree.getData().isLeftOf(segment)) {
                    return rightTree.findLeave(segment);
                } else {
                    TTree leftTree = getLeftTree();
                    if (leftTree == null) {
                        leftTree = new TTree(); // Crée un nouvel enfant gauche si non initialisé
                        setLeftTree(leftTree); // Définit le parent de l'enfant
                    }
                    leftTree.setParent(this);
                    return leftTree.findLeave(segment);
                }
            }
        } else {
            TTree leftTree = getLeftTree();
            if (leftTree == null) {
                leftTree = new TTree(); // Crée un nouvel enfant gauche si non initialisé
                setLeftTree(leftTree); // Définit le parent de l'enfant
            }
            leftTree.setParent(this);
            return leftTree.findLeave(segment);
        }
    }



    private TTree getMaxOfTreeWithParent(TTree t){
        if(t.getRightTree().isEmpty()){
            return t;
        }else{
            TTree right = t.getRightTree();
            right.parent = this;
            return getMaxOfTreeWithParent(right);
        }
    }

    private void setParent(TTree tTree) {
        parent = tTree;
    }
}
