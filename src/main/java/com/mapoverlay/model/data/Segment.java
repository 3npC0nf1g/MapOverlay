package com.mapoverlay.model.data;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.point.StartPoint;



/**
 * Représente un segment
 */
public class Segment extends Data {
    private final int mapId;
    private final Point sPoint;
    private final Point ePoint;

    /**
     * Crée un nouveau segment à partir des deux points spécifiés.
     * L'ID du segment est initialisé à -1 par défaut.
     *
     * @param p1 Le premier point du segment.
     * @param p2 Le deuxième point du segment.
     */
    public Segment(Point p1,Point p2){
        this(p1,p2,-1);
    }


    /**
     * Crée un nouveau segment à partir de deux points spécifiés et d'un ID.
     * Si le premier point est plus haut que le deuxième, il est utilisé comme point de départ du segment.
     * Sinon, le deuxième point est utilisé comme point de départ.
     *
     * @param p1 Le premier point du segment.
     * @param p2 Le deuxième point du segment.
     * @param id L'ID du segment.
     */
    public Segment(Point p1,Point p2,int id) {
        mapId = id;
        if(p1.isHigherThan(p2)){
            this.sPoint = new StartPoint(p1.getX(),p1.getY());
            this.ePoint = p2;
        }else{
            this.sPoint = new StartPoint(p2.getX(),p2.getY());
            this.ePoint = p1;
        }
        ((StartPoint)this.sPoint).addSegment(this);
    }


    /**
     * Renvoie le point de départ de ce segment.
     *
     * @return Le point de départ de ce segment.
     */
    public Point getSPoint() {
        return sPoint;
    }

    /**
     * Renvoie le point d'arrivée de ce segment.
     *
     * @return Le point d'arrivée de ce segment.
     */
    public Point getEPoint() {
        return ePoint;
    }

    /**
     * Calcule le point d'intersection entre la ligne de balayage actuelle et ce segment.
     *
     * @param currentY La coordonnée Y de la ligne de balayage actuelle.
     * @return Le point d'intersection entre la ligne de balayage et ce segment.
     */
    public Point getIntersectSweep(double currentY){
        double x1 = sPoint.getX();
        double y1 = sPoint.getY();
        double x2 = ePoint.getX();
        double y2 = ePoint.getY();
        if(currentY >= y2 && currentY <= y1){
            return new Point(Math.round((((currentY - y1) / (y2 - y1)) * (x2 - x1) + x1)*100)/100, currentY);
        }else {
            return new Point(0,0);
        }
    }


    /**
     * Compare ce segment à un autre objet pour déterminer s'ils sont égaux.
     * Deux segments sont considérés comme égaux s'ils ont les mêmes points de départ et d'arrivée.
     *
     * @param object L'objet à comparer à ce segment.
     * @return true si les deux segments sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!(object instanceof Segment))
            return false;

        return ((Segment) object).getSPoint().equals(this.sPoint) && ((Segment) object).getEPoint().equals(this.ePoint);
    }

    /**
     * Vérifie si le segment contient le point spécifié.
     * Un point est considéré comme contenu dans le segment s'il est situé sur la ligne reliant les points de départ et d'arrivée du segment.
     *
     * @param point Le point à vérifier.
     * @return true si le segment contient le point spécifié, sinon false.
     */
    public boolean contains(Point point){
        double ab = distance(sPoint.getX(), sPoint.getY(),ePoint.getX(), ePoint.getY());
        double ac = distance(sPoint.getX(), sPoint.getY(), point.getX(), point.getY());
        double cb = distance(ePoint.getX(), ePoint.getY(), point.getX(), point.getY());
        double ad = ac + cb;

        return Math.abs(ab - ad) < 0.001;
    }

    /**
     * Calcule la distance entre deux points dans un espace euclidien bidimensionnel.
     *
     * @param x1 La coordonnée X du premier point.
     * @param y1 La coordonnée Y du premier point.
     * @param x2 La coordonnée X du deuxième point.
     * @param y2 La coordonnée Y du deuxième point.
     * @return La distance entre les deux points.
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Vérifie si ce segment est à gauche d'un autre segment par rapport à un point spécifié.
     * Un segment est considéré comme étant à gauche d'un autre segment s'il est entièrement à gauche de celui-ci par rapport au point spécifié.
     *
     * @param segment Le segment à comparer.
     * @param point Le point de référence pour la comparaison.
     * @return true si ce segment est à gauche du segment spécifié par rapport au point spécifié, sinon false.
     */
    public boolean isLeftOf(Segment segment,Point point){
        if(Math.abs(segment.sPoint.getY() - segment.ePoint.getY()) < 1 && this.contains(point) && Math.abs(this.sPoint.getY() - this.ePoint.getY()) < 1){
            return this.ePoint.isLeftOf(segment.ePoint);
        }else if(this.contains(point) && Math.abs(this.sPoint.getY() - this.ePoint.getY()) < 1){
            return point.isLeftOf(segment.getIntersectSweep(point.getY()));
        }else if(Math.abs(segment.sPoint.getY() - segment.ePoint.getY()) < 1){
            return this.getIntersectSweep(point.getY()).isLeftOf(point);
        }
        return this.getIntersectSweep(point.getY()).isLeftOf(segment.getIntersectSweep(point.getY()));
    }


    /**
     * Calcule le point d'intersection entre ce segment et un autre segment spécifié.
     *
     * @param s Le segment avec lequel calculer l'intersection.
     * @return Le point d'intersection entre les deux segments, ou null s'ils ne s'intersectent pas ou si le point d'intersection n'est pas contenu dans les deux segments.
     */
    public Point ComputeIntesectPoint(Segment s){
        double den = this.getA() * s.getB() - this.getB() * s.getA();
        double numX = this.getC() * s.getB() - this.getB() * s.getC();
        double numY = this.getA() * s.getC() - this.getC() * s.getA();

        if(den != 0){
            double X = (double) Math.round((numX / den) * 100) /100;
            double Y = (double) Math.round((numY / den) * 100) /100;

            Point p = new Point(X,Y);

            if(this.contains(p) && s.contains(p)){
                return p;
            }else {
                return null;
            }


        } 
        return null;
    }

    /**
     * Calcule le coefficient 'a' de l'équation de la droite associée à ce segment.
     *
     * @return La valeur du coefficient 'a'.
     */
    private double getA(){
        return sPoint.getY() - ePoint.getY();
    }

    /**
     * Calcule le coefficient 'b' de l'équation de la droite associée à ce segment.
     *
     * @return La valeur du coefficient 'b'.
     */
    private double getB(){
        return ePoint.getX() - sPoint.getX();
    }

    /**
     * Calcule le coefficient 'c' de l'équation de la droite associée à ce segment.
     *
     * @return La valeur du coefficient 'c'.
     */
    private double getC(){
        return -((sPoint.getX() * ePoint.getY())-(ePoint.getX() * sPoint.getY()));
    }

    /**
     * Renvoie une représentation textuelle de ce segment sous forme de chaîne de caractères.
     *
     * @return La représentation textuelle de ce segment.
     */
    @Override
    public String toString() {
        return sPoint.getX() + " " + sPoint.getY() + " " + ePoint.getX() + " " + ePoint.getY();
    }

    /**
     * Renvoie l'identifiant de la carte à laquelle ce segment appartient.
     *
     * @return L'identifiant de la carte.
     */
    public int getMapId() {
        return mapId;
    }
}
