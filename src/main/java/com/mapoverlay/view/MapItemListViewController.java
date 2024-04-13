package com.mapoverlay.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;


/**
 * Contrôleur pour la liste des éléments de carte affichée dans l'interface utilisateur.
 */
public class MapItemListViewController {

    @FXML
    private AnchorPane SegmentListContainer;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TitledPane tiltedMap;

    /**
     * Méthode appelée lorsqu'un utilisateur souhaite ajouter un nouveau segment à la carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void addSegment(ActionEvent event) {
        listener.addSegment();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur choisit une nouvelle couleur pour la carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void chooseColor(ActionEvent event) {
        Color c = colorPicker.getValue();
        listener.updateColor(c);
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite supprimer la carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void deleteMap(ActionEvent event) {
        listener.deleteMap();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite enregistrer la carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void saveMap(ActionEvent event) {
        listener.saveMap();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur clique sur la liste des éléments de carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement de clic.
     */
    @FXML
    void colapseClick(MouseEvent event) {
        listener.colapse();
    }


    /**
     * Définit la couleur actuelle de la carte.
     *
     * @param c La couleur à définir.
     */
    public void setColor(Color c){
        colorPicker.setValue(c);
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
     * Renvoie le panneau de liste des éléments de carte.
     *
     * @return Le panneau de liste des éléments de carte.
     */
    public TitledPane getList() {
        return tiltedMap;
    }


    /**
     * Interface pour écouter les événements de l'interface utilisateur.
     */
    public interface Listener {
        void saveMap();

        void deleteMap();

        void updateColor(Color c);

        void addSegment();

        void colapse();
    }
}
