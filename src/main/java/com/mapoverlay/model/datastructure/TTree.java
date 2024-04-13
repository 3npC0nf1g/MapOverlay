package com.mapoverlay.model.datastructure;
import com.mapoverlay.model.data.*;
import com.mapoverlay.model.data.point.Point;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Cette classe représente la structure de donnée T.
 */
public class TTree extends AVLTree{
    private TTree parent = null;
    private Point point;


    /**
     * Insère des données dans le noeud T lorsque celui-ci est vide.
     * Cette méthode est utilisée pour insérer des données dans un noeud vide.
     * Elle définit les données du noeud, met à jour sa hauteur et crée de nouveaux sous-arbres gauche et droit vides.
     *
     * @param d Les données à insérer dans ce noeud.
     */
    @Override
    protected void insertEmpty(Data d) {
        super.insertEmpty(d);
        setLeftTree(new TTree());
        setRightTree(new TTree());
    }

    /**
     * Définit le point actuel pour cet arbre TTree.
     * @param point Le point actuel à définir.
     */
    public void setCurrentPoint(Point point) {
        this.point = point;
    }

    /**
     * Définit le parent de cet arbre TTree.
     * @param tTree Le parent à définir.
     */
    private void setParent(TTree tTree) {
        parent = tTree;
    }

    /**
     * Renvoie le sous-arbre gauche de cet arbre TTree.
     * @return Le sous-arbre gauche de cet arbre TTree.
     */
    @Override
    public TTree getLeftTree() {
        return (TTree) this.leftTree;
    }

    /**
     * Renvoie le sous-arbre droit de cet arbre TTree.
     * @return Le sous-arbre droit de cet arbre TTree.
     */
    @Override
    public TTree getRightTree() {
        return (TTree) this.rightTree;
    }

    /**
     * Renvoie les données stockées dans cet arbre TTree.
     * @return Les données stockées dans cet arbre TTree.
     */
    @Override
    public Segment getData() {
        return (Segment) this.data;
    }

    /**
     * Insère un segment dans l'arbre TTree.
     * Si l'arbre est vide, insère le segment directement.
     * Sinon, insère le segment dans le sous-arbre approprié en fonction de la position du point actuel.
     * Après l'insertion, l'arbre est rééquilibré.
     *
     * @param data Le segment à insérer dans cet arbre TTree.
     */
    @Override
    public void insert(Data data) {
        Segment insertedSegment = (Segment)data;

        if(isEmpty()){
            insertEmpty(insertedSegment);
            equilibrateAVL();
        }else{
            Segment currentSegment = this.getData();
            if(isLeaf()){
                if(insertedSegment.isLeftOf(currentSegment,point)){

                    if(!insertedSegment.equals(currentSegment)){
                        insertSegment(insertedSegment,currentSegment);
                    }else {
                        return;
                    }

                }else{

                    insertSegment(currentSegment,insertedSegment);

                }
            }else{
                TTree subtree = insertedSegment.isLeftOf(currentSegment,point) ? getLeftTree() : getRightTree();
                subtree.setCurrentPoint(point);
                subtree.insert(insertedSegment);
            }
            equilibrateAVL();
            setData(getMaxOfTree(getLeftTree()));
        }
    }

    /**
     * Effectue une rotation vers la gauche autour de ce noeud TTree.
     * Cette rotation réorganise les sous-arbres de manière à ce que le sous-arbre droit devienne la nouvelle racine
     * et le sous-arbre gauche de ce sous-arbre droit devienne le sous-arbre droit de ce noeud.
     * La hauteur de l'arbre est recalculée après la rotation.
     */
    @Override
    protected void RotateLeft() {
        Data d = getData();
        TTree t = getRightTree();

        setData(t.getData());
        t.setData(d);

        TTree tRight = t.getRightTree();
        tRight.setParent(this);
        setRightTree(tRight);

        t.setRightTree(t.getLeftTree());

        TTree left = getLeftTree();
        left.setParent(t);
        t.setLeftTree(left);

        setLeftTree(t);

        t.computeHeight();
    }

    /**
     * Effectue une rotation vers la droite autour de ce noeud TTree.
     * Cette rotation réorganise les sous-arbres de manière à ce que le sous-arbre gauche devienne la nouvelle racine
     * et le sous-arbre droit de ce sous-arbre gauche devienne le sous-arbre gauche de ce noeud.
     * La hauteur de l'arbre est recalculée après la rotation.
     */
    @Override
    protected void RotateRight() {
        Data d = getData();
        TTree t = getLeftTree();

        setData(t.getData());
        t.setData(d);

        TTree tLeft = t.getLeftTree();
        tLeft.setParent(this);
        setLeftTree(tLeft);

        t.setLeftTree(t.getRightTree());

        TTree right = getRightTree();
        right.setParent(t);
        t.setRightTree(right);

        setRightTree(t);

        t.computeHeight();
    }

    /**
     * Insère deux segments dans les sous-arbres gauche et droit de ce noeud TTree.
     * Cette méthode est utilisée lorsque ce noeud TTree est une feuille et que deux segments doivent être insérés.
     * Chaque segment est inséré dans un sous-arbre différent.
     *
     * @param ls Le segment à insérer dans le sous-arbre gauche.
     * @param rs Le segment à insérer dans le sous-arbre droit.
     */
    private void insertSegment(Data ls,Data rs){
        getLeftTree().insertEmpty(ls);
        getLeftTree().setParent(this);
        getRightTree().insertEmpty(rs);
        getRightTree().setParent(this);
    }

    /**
     * Renvoie le segment le plus à droite dans l'arbre TTree spécifié.
     *
     * @param t L'arbre TTree dans lequel rechercher le segment le plus à droite.
     * @return Le segment le plus à droite dans l'arbre TTree spécifié.
     */
    private Segment getMaxOfTree(TTree t){
        if(t.getRightTree().isEmpty()){
            return t.getData();
        }else{
            return getMaxOfTree(t.getRightTree());
        }
    }

    /**
     * Renvoie le segment le plus à gauche dans l'arbre TTree spécifié.
     *
     * @param t L'arbre TTree dans lequel rechercher le segment le plus à gauche.
     * @return Le segment le plus à gauche dans l'arbre TTree spécifié.
     */
    private Segment getMinOfTree(TTree t){
        if(t.getLeftTree().isEmpty()){
            return t.getData();
        }else{
            return getMinOfTree(t.getLeftTree());
        }
    }

    /**
     * Supprime le segment spécifié de cet arbre TTree.
     *
     * @param segment Le segment à supprimer de cet arbre TTree.
     */
    public void delete(Segment segment){
        Segment currentSegment = this.getData();
        if(this.isLeaf() && currentSegment.equals(segment)){
            OverwriteTTree(null,null,null);
        }else{
            TTree leftTree = this.getLeftTree();
            TTree rightTree = this.getRightTree();
            leftTree.setCurrentPoint(point);
            rightTree.setCurrentPoint(point);

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
                if(currentSegment.isLeftOf(segment,point)){
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
        if(!isEmpty()){
            if(!isLeaf()){
                setData(getMaxOfTree(getLeftTree()));
            }
        }

    }

    /**
     * Remplace les données et les sous-arbres de ce noeud TTree par les valeurs spécifiées.
     * Cette méthode met à jour les données, les sous-arbres gauche et droit de ce noeud TTree avec les valeurs fournies.
     * Par défaut, le sous-arbre gauche est placé avant le sous-arbre droit.
     *
     * @param data      Les nouvelles données à définir pour ce noeud TTree.
     * @param leftTree  Le nouveau sous-arbre gauche à définir pour ce noeud TTree.
     * @param rightTree Le nouveau sous-arbre droit à définir pour ce noeud TTree.
     */
    private void OverwriteTTree(Data data,TTree leftTree,TTree rightTree){
        OverwriteTTree(data,leftTree,rightTree,true);
    }

    /**
     * Remplace les données et les sous-arbres de ce noeud TTree par les valeurs spécifiées.
     * Cette méthode met à jour les données, les sous-arbres gauche et droit de ce noeud TTree avec les valeurs fournies.
     * La position du sous-arbre gauche par rapport au sous-arbre droit peut être spécifiée à l'aide du paramètre leftBefore.
     *
     * @param data        Les nouvelles données à définir pour ce noeud TTree.
     * @param leftTree    Le nouveau sous-arbre gauche à définir pour ce noeud TTree.
     * @param rightTree   Le nouveau sous-arbre droit à définir pour ce noeud TTree.
     * @param leftBefore  Indique si le sous-arbre gauche doit être placé avant le sous-arbre droit (true) ou après (false).
     */
    private void OverwriteTTree(Data data,TTree leftTree,TTree rightTree,boolean leftBefore){
        this.setData(data);
        if(leftBefore){
            this.setLeftTree(leftTree);
            this.setRightTree(rightTree);
        }else {
            this.setRightTree(rightTree);
            this.setLeftTree(leftTree);
        }
        if(leftTree != null){
            leftTree.setParent(this);
        }
        if(rightTree != null){
            rightTree.setParent(this);
        }
        this.computeHeight();
    }

    /**
     * Renvoie un ensemble de segments dont la ligne de fin est inférieure (y plus petit) à la coordonnée y du point spécifié.
     *
     * @param point Le point utilisé comme référence pour comparer la coordonnée y.
     * @return Un ensemble de segments dont la ligne de fin est inférieure à la coordonnée y du point spécifié.
     */
    public Set<Segment> getSegmentsWithLower(Point point) {

        Set<Segment> result = new LinkedHashSet<>();
        searchSegmentsWithLower(this, point, result);
        return result;

    }

    /**
     * Recherche récursivement les segments dont la ligne de fin est égale à la coordonnée y du point spécifié dans l'arbre AVL.
     * Les segments trouvés sont ajoutés à l'ensemble de résultats.
     *
     * @param tree   L'arbre AVL dans lequel rechercher les segments.
     * @param point  Le point utilisé comme référence pour comparer la coordonnée y.
     * @param result L'ensemble dans lequel ajouter les segments trouvés.
     */
    private void searchSegmentsWithLower(AVLTree tree, Point point, Set<Segment> result) {
        if (tree.getData() == null || point == null) {
            return;
        }

        Segment treeNodeSegment = (Segment) tree.getData();


        if (treeNodeSegment.getEPoint().equals(point)) {

            result.add(treeNodeSegment);
        }

        searchSegmentsWithLower(tree.getLeftTree(), point, result);
        searchSegmentsWithLower(tree.getRightTree(), point, result);
    }

    /**
     * Renvoie un ensemble de segments qui contiennent le point spécifié.
     *
     * @param point Le point à rechercher dans les segments.
     * @return Un ensemble de segments qui contiennent le point spécifié.
     */
    public Set<Segment> getSegmentsContains(Point point) {
        Set<Segment> result = new LinkedHashSet<>();
        searchSegmentsContains(point, result);
        return result;
    }

    /**
     * Recherche récursivement les segments qui contiennent le point spécifié dans cet arbre TTree.
     * Les segments trouvés sont ajoutés à l'ensemble de résultats.
     *
     * @param point  Le point à rechercher dans les segments.
     * @param result L'ensemble dans lequel ajouter les segments trouvés.
     */
    private void searchSegmentsContains(Point point, Set<Segment> result) {
        if (this.getData() == null || point == null) {
            return;
        }

        Segment segment =  this.getData();

        if(isLeaf()){
            if(segment.contains(point)){
                result.add(segment);
            }
        }else {
            getLeftTree().searchSegmentsContains(point,result);
            getRightTree().searchSegmentsContains(point,result);
        }
    }

    /**
     * Recherche le voisin de gauche du point spécifié dans cet arbre TTree.
     * Le voisin de gauche est le segment dont la ligne de fin est la plus à gauche et dont le point d'intersection avec la ligne de balayage est le plus proche du point spécifié.
     *
     * @param point Le point à partir duquel rechercher le voisin de gauche.
     * @return Le segment voisin de gauche du point spécifié.
     */
    public Segment findLeftNeighbor(Point point) {
        Segment currentSegment = getData();
        Point currentPoint = currentSegment.getIntersectSweep(point.getY());

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

    /**
     * Recherche le voisin de droite du point spécifié dans cet arbre TTree.
     * Le voisin de droite est le segment dont la ligne de fin est la plus à droite et dont le point d'intersection avec la ligne de balayage est le plus proche du point spécifié.
     *
     * @param point Le point à partir duquel rechercher le voisin de droite.
     * @return Le segment voisin de droite du point spécifié.
     */
    public Segment findRightNeighbor(Point point) {
        Segment currentSegment = getData();
        Point currentPoint = currentSegment.getIntersectSweep(point.getY());

        if(isLeaf()){
            if(currentPoint.isLeftOf(point)){
                return null;
            }else{
                return getData();
            }
        }else {
            if(currentPoint.isLeftOf(point)){
                return getRightTree().findRightNeighbor(point);
            }else {
                return getLeftTree().findRightNeighbor(point);
            }
        }
    }

    /**
     * Recherche le segment adjacent à gauche du segment spécifié dans cet arbre TTree.
     * Le segment adjacent à gauche est le segment dont la ligne de fin est la plus à gauche parmi tous les segments dont la ligne de fin est inférieure à celle du segment spécifié.
     *
     * @param segment Le segment dont on recherche l'adjacent à gauche.
     * @return Le segment adjacent à gauche du segment spécifié.
     */
   public Segment findLeftAdjacentSegment(Segment segment){
        return findLeft(findLeave(segment));
   }


    /**
     * Recherche le segment adjacent à droite du segment spécifié dans cet arbre TTree.
     * Le segment adjacent à droite est le segment dont la ligne de fin est la plus à droite parmi tous les segments dont la ligne de fin est inférieure à celle du segment spécifié.
     *
     * @param segment Le segment dont on recherche l'adjacent à droite.
     * @return Le segment adjacent à droite du segment spécifié.
     */
   public Segment findRightAdjacentSegment(Segment segment){
        return findRight(findLeave(segment));
   }


    /**
     * Recherche récursivement le segment adjacent à droite du segment spécifié dans cet arbre TTree.
     * Le segment adjacent à droite est le segment dont la ligne de fin est la plus à droite parmi tous les segments dont la ligne de fin est inférieure à celle du segment spécifié.
     *
     * @param current Le nœud TTree actuellement examiné dans la recherche.
     * @return Le segment adjacent à droite du segment spécifié.
     */
    private Segment findRight(TTree current) {
        if(current == null){
            return null;
        }
        
        TTree parent = current.parent;
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

    /**
     * Recherche récursivement le segment adjacent à gauche du segment spécifié dans cet arbre TTree.
     * Le segment adjacent à gauche est le segment dont la ligne de fin est la plus à gauche parmi tous les segments dont la ligne de fin est inférieure à celle du segment spécifié.
     *
     * @param current Le nœud TTree actuellement examiné dans la recherche.
     * @return Le segment adjacent à gauche du segment spécifié.
     */
    public Segment findLeft(TTree current){
        if(current == null){
            return null;
        }

       TTree parent = current.parent;
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

    /**
     * Recherche récursivement le nœud feuille contenant le segment spécifié dans cet arbre TTree.
     *
     * @param segment Le segment dont on recherche le nœud feuille.
     * @return Le nœud feuille contenant le segment spécifié.
     */
    public TTree findLeave(Segment segment){
       Segment currentSegment = getData();
       if(isLeaf()){
           if (currentSegment.equals(segment)){
               return this;
           }else {
               return null;
           }
       }

        TTree rightTree = getRightTree();
        TTree leftTree = getLeftTree();

       if(currentSegment.isLeftOf(segment,point)){
           if(currentSegment.equals(segment)){
               if(rightTree.getData().isLeftOf(segment,point)){
                   return leftTree.findLeave(segment);
               }else{
                   return getMaxOfTreeWithParent(leftTree);
               }
           }else{
               TTree leave = rightTree.findLeave(segment);
               if(leave != null){
                   return leave;
               }else {
                   return leftTree.findLeave(segment);
               }
           }
       }else{
           return leftTree.findLeave(segment);
       }
    }

    /**
     * Renvoie le nœud le plus à droite dans cet arbre TTree, en incluant son parent.
     *
     * @param t Le nœud à partir duquel commencer la recherche.
     * @return Le nœud le plus à droite dans cet arbre TTree, en incluant son parent.
     */
    private TTree getMaxOfTreeWithParent(TTree t){
        if(t.getRightTree().isEmpty()){
            return t;
        }else{
            return getMaxOfTreeWithParent(t.getRightTree());
        }
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de cet arbre TTree.
     * Les nœuds sont représentés entre parenthèses et séparés par une barre verticale pour les sous-arbres gauche et droit.
     *
     * @return Une représentation sous forme de chaîne de caractères de cet arbre TTree.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "()";
        }else {
            if(this.isLeaf()){
                return "("+this.getData().toString()+")";
            }else{
                return this.getLeftTree().toString()+ " | " + this.getRightTree().toString();
            }
        }
    }
}
