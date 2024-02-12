package com.mapoverlay.model;

public abstract class AVLTree {

    private Data data;
    private AVLTree leftTree;
    private AVLTree rightTree;
    private int height;

    public AVLTree(Data data,AVLTree left,AVLTree right){
        this.data = data;
        this.leftTree = left;
        this.rightTree = right;
        this.height = 0;
    }
    public AVLTree(){
        this(null,null,null);
    }

    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setLeftTree(AVLTree leftTree) {
        this.leftTree = leftTree;
    }
    public AVLTree getLeftTree() {
        return leftTree;
    }
    public void setRightTree(AVLTree rightTree) {
        this.rightTree = rightTree;
    }
    public AVLTree getRightTree() {
        return rightTree;
    }

    protected void computeHeight() {
        if(isEmpty()){
            this.height = 0;
        }else{
            this.height = Math.max(leftTree.getHeight(),rightTree.getHeight()) + 1;
        }
    }

    protected void equilibrateAVL(){
        switch (getBalance()){
            case 2:
                if(getRightTree().getBalance() < 0){
                    getRightTree().RotateRight();
                }
                RotateLeft();
                break;
            case -2:
                if(getRightTree().getBalance() > 0){
                    getRightTree().RotateLeft();
                }
                RotateRight();
                break;
        }
        computeHeight();
    }

    private void RotateLeft() {
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

    private void RotateRight() {
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

    protected boolean isEmpty(){
        return this.data == null && this.leftTree == null && this.rightTree == null;
    }

    protected abstract void insert(Data d);

    protected void insertEmpty(Data d){
        setData(d);
        setHeight(1);
    }
}
