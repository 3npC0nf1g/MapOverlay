package com.mapoverlay.model.data.point;

import com.mapoverlay.model.data.Data;


/**
 * Cette classe permet la création d'un point
 */
public class Point extends Data {
    private final double x,y;

    /**
     * Crée un nouvel objet Point avec les coordonnées spécifiées.
     *
     * @param x La coordonnée x du point.
     * @param y La coordonnée y du point.
     */
    public Point(double x,double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Crée un nouvel objet Point avec les mêmes coordonnées que le point spécifié.
     *
     * @param d Le point dont les coordonnées seront copiées pour créer le nouvel objet Point.
     */
    public Point(Point d) {
        this(d.getX(),d.getY());
    }

    /**
     * Renvoie la coordonnée x de ce point.
     *
     * @return La coordonnée x de ce point.
     */
    public double getX() {
        return x;
    }


    /**
     * Renvoie la coordonnée y de ce point.
     *
     * @return La coordonnée y de ce point.
     */
    public double getY() {
        return y;
    }


    /**
     * Vérifie si ce point est situé plus haut que le point spécifié dans le plan.
     * Si les coordonnées y sont égales, le point le plus à gauche est considéré comme le plus haut.
     *
     * @param point Le point à comparer.
     * @return true si ce point est situé plus haut que le point spécifié, sinon false.
     */
    public boolean isHigherThan(Point point){
        if(point == null)
            return false;
        return this.getY() > point.getY() || (this.getY() == point.getY() && this.getX() < point.getX());
    }

    /**
     * Vérifie si ce point est situé à gauche ou au même niveau que le point spécifié sur l'axe des x.
     * La méthode considère que ce point est à gauche ou au même niveau que le point spécifié si la différence
     * entre les coordonnées x de ce point et du point spécifié est inférieure ou égale à 0.01.
     *
     * @param p Le point à comparer.
     * @return true si ce point est situé à gauche ou au même niveau que le point spécifié sur l'axe des x, sinon false.
     */
    public boolean isLeftOf(Point p){
        return this.getX() - 0.01 <= p.getX();
    }

    /**
     * Crée un clone de ce point.
     *
     * @return Un nouvel objet Point avec les mêmes coordonnées que ce point.
     */
    public Point clone(){
        return new Point(this);
    }

    /**
     * Vérifie si cet objet Point est égal à l'objet spécifié.
     * Deux points sont considérés comme égaux si leurs coordonnées x et y sont égales.
     *
     * @param object L'objet à comparer à cet objet Point.
     * @return true si les deux objets sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!(object instanceof Point))
            return false;

        return this.x == ((Point) object).x && this.y == ((Point) object).y;
    }


    /**
     * Renvoie une chaîne représentant ce point sous forme de "(x,y)".
     *
     * @return Une chaîne représentant ce point.
     */
    @Override
    public String toString() {
        return "(" + getX()+","+getY()+")";
    }
}
