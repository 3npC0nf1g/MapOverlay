package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Data;

/**
 * Représente un arbre AVL (Arbre Binaire de Recherche Équilibré).
 */
public abstract class AVLTree {

    protected Data data;
    protected AVLTree leftTree,rightTree;
    private int height;

    /**
     * Construit un nouveau noeud AVL avec les données spécifiées et les sous-arbres gauche et droit.
     * La hauteur est initialisée à 0.
     *
     * @param data Les données à stocker dans ce noeud AVL.
     * @param left Le sous-arbre gauche de ce noeud AVL.
     * @param right Le sous-arbre droit de ce noeud AVL.
     */
    public AVLTree(Data data,AVLTree left,AVLTree right){
        this.data = data;
        this.leftTree = left;
        this.rightTree = right;
        this.height = 0;
    }

    /**
     * Construit un nouveau noeud AVL sans données ni sous-arbres.
     * La hauteur est initialisée à 0.
     */
    public AVLTree(){
        this(null,null,null);
    }


    /**
     * Définit les données stockées dans ce noeud AVL.
     * @param data Les données à attribuer à ce noeud.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Renvoie la hauteur de ce noeud AVL.
     * @return La hauteur de ce noeud AVL.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Définit la hauteur de ce noeud AVL.
     * @param height La hauteur à attribuer à ce noeud.
     */
    private void setHeight(int height) {
        this.height = height;
    }

    /**
     * Définit le sous-arbre gauche de ce noeud AVL.
     * @param leftTree Le sous-arbre gauche à attribuer à ce noeud.
     */
    public void setLeftTree(AVLTree leftTree) {
        this.leftTree = leftTree;
    }

    /**
     * Définit le sous-arbre droit de ce noeud AVL.
     * @param rightTree Le sous-arbre droit à attribuer à ce noeud.
     */
    public void setRightTree(AVLTree rightTree) {
        this.rightTree = rightTree;
    }

    /**
     * Rééquilibre l'arbre AVL si nécessaire après une opération d'insertion ou de suppression.
     * Cette méthode vérifie l'équilibre de l'arbre en fonction de la hauteur des sous-arbres gauche et droit,
     * puis effectue des rotations si nécessaire pour rétablir l'équilibre.
     */
    protected void equilibrateAVL(){
        computeHeight();
        int balance = getBalance();
        if(balance >= 2){
            if(getRightTree().getBalance() < 0){
                getRightTree().RotateRight();
            }
            RotateLeft();
        }else if(balance <= -2){
            if(getRightTree().getBalance() > 0){
                getRightTree().RotateLeft();
            }
            RotateRight();
        }
        computeHeight();
    }

    /**
     * Effectue une rotation vers la gauche autour de ce noeud AVL.
     * Cette rotation réorganise les sous-arbres de manière à ce que le sous-arbre droit devienne la nouvelle racine
     * et le sous-arbre gauche de ce sous-arbre droit devienne le sous-arbre gauche de ce noeud.
     * La hauteur de l'arbre est recalculée après la rotation.
     */
    protected void RotateLeft() {
        Data d = getData();
        AVLTree t = getRightTree();

        setData(t.getData());
        t.setData(d);

        setRightTree(t.getRightTree());
        t.setRightTree(t.getLeftTree());
        t.setLeftTree(getLeftTree());
        setLeftTree(t);

        t.computeHeight();
    }

    /**
     * Effectue une rotation vers la droite autour de ce noeud AVL.
     * Cette rotation réorganise les sous-arbres de manière à ce que le sous-arbre gauche devienne la nouvelle racine
     * et le sous-arbre droit de ce sous-arbre gauche devienne le sous-arbre droit de ce noeud.
     * La hauteur de l'arbre est recalculée après la rotation.
     */
    protected void RotateRight() {
        Data d = getData();
        AVLTree t = getLeftTree();

        setData(t.getData());
        t.setData(d);

        setLeftTree(t.getLeftTree());
        t.setLeftTree(t.getRightTree());
        t.setRightTree(getRightTree());
        setRightTree(t);

        t.computeHeight();
    }


    /**
     * Calcule le facteur d'équilibre de ce noeud AVL.
     * Le facteur d'équilibre est la différence entre les hauteurs du sous-arbre droit et du sous-arbre gauche.
     * Un facteur d'équilibre positif indique que le sous-arbre droit est plus haut, tandis qu'un facteur d'équilibre négatif
     * indique que le sous-arbre gauche est plus haut. Un facteur d'équilibre de 0 signifie que les deux sous-arbres ont la
     * même hauteur.
     *
     * @return Le facteur d'équilibre de ce noeud AVL.
     */
    private int getBalance(){
        if(isEmpty())
            return 0;
        else
            return this.rightTree.getHeight() - this.leftTree.getHeight();
    }

    /**
     * Calcule la hauteur de ce noeud AVL.
     * La hauteur d'un noeud est définie comme la longueur du plus long chemin de ce noeud à une feuille.
     * La hauteur est mise à jour en fonction des hauteurs des sous-arbres gauche et droit de ce noeud.
     */
    protected void computeHeight() {
        if(isEmpty()){
            this.height = 0;
        }else{
            this.height = Math.max(leftTree.getHeight(),rightTree.getHeight()) + 1;
        }
    }
    /**
     * Vérifie si ce noeud AVL est vide, c'est-à-dire s'il ne contient pas de données.
     *
     * @return true si ce noeud est vide, sinon false.
     */
    public boolean isEmpty(){
        return this.data == null;
    }

    /**
     * Vérifie si ce noeud AVL est une feuille, c'est-à-dire s'il n'a pas de sous-arbres gauche ou droit.
     *
     * @return true si ce noeud est une feuille, sinon false.
     */
    protected boolean isLeaf(){
        return getLeftTree().isEmpty() && getRightTree().isEmpty();
    }


    /**
     * Insère des données dans ce noeud AVL lorsque celui-ci est vide.
     * Cette méthode est utilisée pour insérer des données dans un noeud vide.
     * Elle définit les données du noeud et met à jour sa hauteur.
     *
     * @param d Les données à insérer dans ce noeud.
     */
    public abstract void insert(Data d);

    /**
     * Renvoie le sous-arbre gauche de ce noeud AVL.
     *
     * @return Le sous-arbre gauche de ce noeud AVL.
     */
    public abstract AVLTree getLeftTree();

    /**
     * Renvoie le sous-arbre droit de ce noeud AVL.
     *
     * @return Le sous-arbre droit de ce noeud AVL.
     */
    public abstract AVLTree getRightTree();

    /**
     * Renvoie les données stockées dans ce noeud AVL.
     *
     * @return Les données stockées dans ce noeud AVL.
     */
    public abstract Data getData();

    protected void insertEmpty(Data d){
        setData(d);
        setHeight(1);
    }
}
