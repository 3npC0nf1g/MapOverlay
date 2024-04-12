package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Data;

public abstract class AVLTree {

    protected Data data;
    protected AVLTree leftTree,rightTree;
    private int height;

    // Constructor
    public AVLTree(Data data,AVLTree left,AVLTree right){
        this.data = data;
        this.leftTree = left;
        this.rightTree = right;
        this.height = 0;
    }

    public AVLTree(){
        this(null,null,null);
    }

    // Getter & Setter
    public void setData(Data data) {
        this.data = data;
    }
    public int getHeight() {
        return height;
    }
    private void setHeight(int height) {
        this.height = height;
    }
    public void setLeftTree(AVLTree leftTree) {
        this.leftTree = leftTree;
    }
    public void setRightTree(AVLTree rightTree) {
        this.rightTree = rightTree;
    }

    // Principe AVL
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



    private int getBalance(){
        if(isEmpty())
            return 0;
        else
            return this.rightTree.getHeight() - this.leftTree.getHeight();
    }

    protected void computeHeight() {
        if(isEmpty()){
            this.height = 0;
        }else{
            this.height = Math.max(leftTree.getHeight(),rightTree.getHeight()) + 1;
        }
    }

    // Status méthode
    public boolean isEmpty(){
        return this.data == null && this.leftTree == null && this.rightTree == null;
    }

    protected boolean isLeaf(){
        return getLeftTree().isEmpty() && getRightTree().isEmpty();
    }

    // Override méthod
    public abstract void insert(Data d);
    public abstract AVLTree getLeftTree();
    public abstract AVLTree getRightTree();
    public abstract Data getData();

    protected void insertEmpty(Data d){
        setData(d);
        setHeight(1);
    }
}
