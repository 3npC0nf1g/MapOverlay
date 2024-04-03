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

    public void delete(Set<Segment> UC) {
        if (root == null || UC == null || UC.isEmpty()) {
            return; // Arrêtez si l'arbre est vide ou l'ensemble UC est null ou vide
        }

        // Parcourir tous les segments dans l'ensemble UC et les supprimer de l'arbre
        for (Segment segment : UC) {
            root = deleteSegment(root, segment);
        }
    }

    private AVLTree deleteSegment(AVLTree tree, Segment segment) {
        if (tree == null) {
            return null;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Si le segment actuel est celui que nous voulons supprimer, procédez à la suppression
        if (currentSegment.equals(segment)) {
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
            tree.setRightTree(deleteSegment(tree.getRightTree(), (Segment) successor.getData()));
        } else if (segment.getEPoint().isHigherThan(currentSegment.getEPoint())) {
            // Si le segment actuel est inférieur au segment à supprimer, continuez la recherche dans le sous-arbre gauche
            tree.setLeftTree(deleteSegment(tree.getLeftTree(), segment));
        } else {
            // Si le segment actuel est supérieur au segment à supprimer, continuez la recherche dans le sous-arbre droit
            tree.setRightTree(deleteSegment(tree.getRightTree(), segment));
        }

        // Équilibrez l'arbre AVL après la suppression
        tree.equilibrateAVL();

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

        searchSegmentsWithLower(tree.getLeftTree(), point, result);
        searchSegmentsWithLower(tree.getRightTree(), point, result);
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

        Segment segment = (Segment) tree.getData();

        // Vérifie si le segment contient le point spécifié
        if (segment.contains(point)) {
            result.add(segment);
        }

        // Continue la recherche dans les sous-arbres gauche et droit
        searchSegmentsContains(tree.getLeftTree(), point, result);
        searchSegmentsContains(tree.getRightTree(), point, result);
    }

    public Segment getLeftNeighbor(Point point) {
        return findLeftNeighbor(root, point);
    }

    private Segment findLeftNeighbor(AVLTree tree, Point point) {
        if (tree == null || point == null) {
            return null;
        }

        // Chercher dans le sous-arbre gauche
        Segment leftNeighbor = findLeftNeighbor(tree.getLeftTree(), point);

        // Si un voisin gauche a été trouvé dans le sous-arbre gauche, retournez-le
        if (leftNeighbor != null) {
            return leftNeighbor;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Si le segment actuel est inférieur au point spécifié, continuez la recherche dans le sous-arbre droit
        if (currentSegment.getEPoint().isLeftOf(point)) {
            return findLeftNeighbor(tree.getRightTree(), point);
        }

        // Sinon, retournez le segment associé au nœud actuel, qui sera le voisin gauche le plus proche
        return currentSegment;
    }


    public Segment getRightNeighbor(Point point) {
        return findRightNeighbor(root, point);
    }

    private Segment findRightNeighbor(AVLTree tree, Point point) {
        if (tree == null || point == null) {
            return null;
        }

        // Chercher dans le sous-arbre droit
        Segment rightNeighbor = findRightNeighbor(tree.getRightTree(), point);

        // Si un voisin droit a été trouvé dans le sous-arbre droit, retournez-le
        if (rightNeighbor != null) {
            return rightNeighbor;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Si le segment actuel est inférieur au point spécifié, continuez la recherche dans le sous-arbre droit
        if (currentSegment.getEPoint().isLeftOf(point)) {
            return findRightNeighbor(tree.getRightTree(), point);
        }

        // Sinon, retournez le segment associé au nœud actuel, qui sera le voisin droit le plus proche
        return currentSegment;
    }

    //--------------------------------------------
    public Segment getLeftmostSegment(Point p) {
        return findLeftmostSegment(root, p);
    }

    private Segment findLeftmostSegment(AVLTree tree, Point p) {
        if (tree == null) {
            return null;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Vérifiez si le segment actuel est à gauche du point spécifié
        if (currentSegment.getEPoint().isLeftOf(p)) {
            // Si oui, continuez la recherche dans le sous-arbre gauche
            return findLeftmostSegment(tree.getLeftTree(), p);
        }

        // Sinon, le segment actuel est le plus à gauche dans le sous-arbre gauche
        // Retournez ce segment
        return currentSegment;
    }

//--------------------------------
public Segment getRightmostSegment(Point p) {
    return findRightmostSegment(root, p);
}
    private Segment findRightmostSegment(AVLTree tree, Point p) {
        if (tree == null) {
            return null;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Vérifiez si le segment actuel est à droite du point spécifié
        if (!currentSegment.getEPoint().isLeftOf(p)) {
            // Si oui, continuez la recherche dans le sous-arbre droit
            return findRightmostSegment(tree.getRightTree(), p);
        }

        // Sinon, le segment actuel est le plus à droite dans le sous-arbre droit
        // Retournez ce segment
        return currentSegment;
    }

    //----------------------

    public Segment getRightNeighborSegment(Segment segment) {
        return findRightNeighbor(root, segment);
    }

    private Segment findRightNeighbor(AVLTree tree, Segment segment) {
        if (tree == null || segment == null) {
            return null;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Si le segment donné est plus petit que le segment actuel, recherchez dans le sous-arbre gauche
        if (segment.isLeftOf(currentSegment)) {
            return findRightNeighbor(tree.getLeftTree(), segment);
        }

        // Sinon, si le segment donné est plus grand que le segment actuel, recherchez dans le sous-arbre droit
        return findRightNeighbor(tree.getRightTree(), segment);
    }

    //-----------------------

    public Segment getLeftNeighborSegment(Segment segment) {
        return findLeftNeighbor(root, segment);
    }

    private Segment findLeftNeighbor(AVLTree tree, Segment segment) {
        if (tree == null || segment == null) {
            return null;
        }

        Segment currentSegment = (Segment) tree.getData();

        // Si le segment donné est plus grand que le segment actuel, recherchez dans le sous-arbre droit
        if (segment.isLeftOf(currentSegment)) {
            return findLeftNeighbor(tree.getRightTree(), segment);
        }

        // Sinon, si le segment donné est plus petit que le segment actuel, recherchez dans le sous-arbre gauche
        return findLeftNeighbor(tree.getLeftTree(), segment);
    }





}
