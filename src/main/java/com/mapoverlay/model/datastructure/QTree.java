package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.point.StartPoint;
/**
 * Cette classe représente la structure de donnée Q.
 */
public class QTree extends AVLTree{

    public QTree(){
        super();
    }


    /**
     * Insère une nouvelle donnée dans cet AVLTree lorsque le nœud est vide.
     * Cette méthode est appelée lors de l'insertion d'un segment dans l'arbre lors de la construction de la structure de l'intersection de segments.
     * Elle crée également des arbres QTree pour les sous-arbres gauche et droit.
     *
     * @param d La donnée à insérer dans ce nœud AVLTree.
     */
    @Override
    protected void insertEmpty(Data d) {
        super.insertEmpty(d);
        System.out.println("NEW POINT :" + d.toString());
        setLeftTree(new QTree());
        setRightTree(new QTree());
    }

    /**
     * Insère un point dans cet AVLTree.
     * Si le nœud est vide, le point est inséré à cet emplacement.
     * Sinon, le point est inséré dans le sous-arbre approprié en fonction de sa position par rapport au point du nœud actuel.
     * Cette méthode maintient l'équilibre de l'arbre AVL après l'insertion.
     *
     * @param d Le point à insérer dans cet AVLTree.
     */
    @Override
    public void insert(Data d) {
        Point p = ((Point)d).clone();
        if(isEmpty()){
            insertEmpty(p);
        }else{
            Point currentPoint = this.getData();
            if(currentPoint.isHigherThan(p)){

                this.getRightTree().insert(p);
            }else {
                if (currentPoint.equals(p)) {
                    if(p instanceof StartPoint){
                        if(currentPoint instanceof StartPoint){
                            for(Segment s : ((StartPoint) p).getSegments()){
                                ((StartPoint) currentPoint).addSegment(s);
                            }
                        }else{
                            this.setData(p);
                        }
                    }
                }else{
                    this.getLeftTree().insert(p);
                }

            }
        }
        equilibrateAVL();
    }

    /**
     * Renvoie le prochain point dans cet AVLTree.
     * Le prochain point est le point le plus à gauche dans l'arbre.
     * Cette méthode supprime également le point renvoyé de l'arbre.
     * Après la suppression du point, l'arbre est rééquilibré.
     *
     * @return Le prochain point dans cet AVLTree, ou null si l'arbre est vide.
     */
    public Point getNextPoint(){
        Point minPoint;

        if(isEmpty()){
            return null;
        }

        if(getLeftTree().isEmpty()){
            minPoint = getData();
            AVLTree t = getRightTree();
            setData(t.getData());
            setLeftTree(t.getLeftTree());
            setRightTree(t.getRightTree());
        }else{
            minPoint = getLeftTree().getNextPoint();
        }
        equilibrateAVL();
        return minPoint;
    }

    /**
     * Renvoie le sous-arbre gauche de cet AVLTree.
     *
     * @return Le sous-arbre gauche de cet AVLTree.
     */
    @Override
    public QTree getLeftTree() {
        return (QTree) this.leftTree;
    }



    /**
     * Renvoie le sous-arbre droit de cet AVLTree.
     *
     * @return Le sous-arbre droit de cet AVLTree.
     */
    @Override
    public QTree getRightTree() {
        return (QTree) this.rightTree;
    }



    /**
     * Renvoie les données stockées dans ce nœud AVLTree, qui sont un objet de type Point.
     *
     * @return Les données stockées dans ce nœud AVLTree.
     */
    @Override
    public Point getData() {
        return (Point) this.data;
    }

    /**
     * Vérifie si ce nœud AVLTree contient le point spécifié.
     * La méthode compare la distance entre le point spécifié et les données stockées dans ce nœud.
     * Si la distance est inférieure à 0.15 dans les deux dimensions (x et y), le point est considéré comme contenu.
     * Si ce nœud n'a pas de données (c'est-à-dire est vide), la méthode renvoie false.
     * Si ce nœud a des sous-arbres gauche et droit, la méthode vérifie si le point spécifié est contenu dans l'un des sous-arbres.
     * Si ce nœud a seulement un sous-arbre (gauche ou droit), la méthode vérifie si le point spécifié est contenu dans ce sous-arbre.
     *
     * @param point Le point à vérifier.
     * @return true si ce nœud contient le point spécifié, sinon false.
     */
   public Boolean contains(Point point) {
        Point cPoint = (Point)this.data;
        if(cPoint == null){
            return false;
        }

        double x = Math.abs(cPoint.getX() - point.getX());
        double y = Math.abs(cPoint.getY() - point.getY());
        if( x < 0.15 && y < 0.15){
            return true;
        }

        if(this.getLeftTree().getData() != null && this.getRightTree().getData() != null){
            return this.getLeftTree().contains(point) || this.getRightTree().contains(point);
        }

        if(this.getLeftTree().getData() != null){
            return this.getLeftTree().contains(point) ;
        }else if(this.getRightTree().getData() != null) {
            return this.getRightTree().contains(point);
        }else {
            return false;
        }
   }
}
