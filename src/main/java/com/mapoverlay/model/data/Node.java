package com.mapoverlay.model.data;

public class Node {
    private Segment data;
    private Node left;
    private Node right;
    private Node parent; // Ajout du lien vers le parent
    private int height; // Hauteur du nœud

    // Constructeur
    public Node(Segment data) {
        this.data = data;
        this.height = 1; // Initialisation de la hauteur à 1
        this.left = null;
        this.right = null;
        this.parent = null; // Initialisation du parent à null
    }

    // Getters et setters pour les attributs

    public Segment getData() {
        return data;
    }

    public void setData(Segment data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        if (left != null) {
            left.setParent(this); // Met à jour le parent du nœud gauche
        }
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        if (right != null) {
            right.setParent(this); // Met à jour le parent du nœud droit
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
