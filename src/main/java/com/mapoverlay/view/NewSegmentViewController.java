package com.mapoverlay.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Contrôleur pour la vue de création d'un nouveau segment.
 */
public class NewSegmentViewController {

    @FXML
    private TextField x1;

    @FXML
    private TextField x2;

    @FXML
    private TextField y1;

    @FXML
    private TextField y2;


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite créer un nouveau segment.
     * Elle récupère les valeurs des champs de texte et déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void CreateSegment(ActionEvent event) {
        try {
            double dx1 = Double.parseDouble(x1.getText());
            double dy1 = Double.parseDouble(y1.getText());
            double dx2 = Double.parseDouble(x2.getText());
            double dy2 = Double.parseDouble(y2.getText());
            listener.addSegment(dx1,dy1,dx2,dy2);
        }catch (Exception e){

        }
    }

    // Listener implementation
    private Listener listener;

    /**
     * Définit le listener pour écouter les événements de l'interface utilisateur.
     *
     * @param listener Le listener à définir.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    /**
     * Interface pour écouter les événements de l'interface utilisateur.
     */
    public interface Listener {
        /**
         * Méthode appelée lorsqu'un nouveau segment doit être ajouté.
         *
         * @param x1 La coordonnée x du premier point.
         * @param y1 La coordonnée y du premier point.
         * @param x2 La coordonnée x du deuxième point.
         * @param y2 La coordonnée y du deuxième point.
         */
        void addSegment(double x1,double y1,double x2,double y2);
    }
}

