package com.mapoverlay.view;

import com.mapoverlay.model.data.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;


/**
 * Contrôleur pour la vue de la superposition de la carte.
 */
public class MapOverlayViewController {

    @FXML
    private VBox MapList;

    @FXML
    private AnchorPane canvasContainer;

    @FXML
    private ColorPicker colorSweep;


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite ajouter une carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void addMap(ActionEvent event) {
        listener.addMap();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite effacer le graphique.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void clearGraph(ActionEvent event) {
        listener.clearGraph();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite importer une carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void importGraph(ActionEvent event) {
        listener.importMap();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite lancer la superposition de carte.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void launchMapOverlay(ActionEvent event) {
        listener.launchMapOverlay();
    }


    /**
     * Renvoie le conteneur du canevas.
     *
     * @return Le conteneur du canevas.
     */
    public AnchorPane getCanvasContainer() {
        return canvasContainer;
    }


    /**
     * Méthode appelée lorsqu'un utilisateur choisit une nouvelle couleur pour le balayage.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void SweepColor(ActionEvent event) {
        listener.setSweepColor(colorSweep.getValue());
    }


    /**
     * Méthode appelée lorsqu'un utilisateur active ou désactive le pas automatique.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void AutoStep(ActionEvent event) {
        listener.autoStep();
    }

    /**
     * Méthode appelée lorsqu'un utilisateur souhaite afficher l'arbre Q.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void ShowQ(ActionEvent event) {
        listener.showQ();
    }

    /**
     * Méthode appelée lorsqu'un utilisateur souhaite afficher l'arbre T.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void ShowT(ActionEvent event) {
        listener.ShowT();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite réinitialiser l'arbre Q.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void resetQ(ActionEvent event) {
        listener.resetQ();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite afficher les résultats.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void showResult(ActionEvent event) {
        listener.showRésult();
    }


    /**
     * Méthode appelée lorsqu'un utilisateur souhaite choisir l'intersection de soi.
     * Déclenche l'action correspondante via le listener.
     *
     * @param event L'événement déclencheur.
     */
    @FXML
    void chooseSelfIntersection(ActionEvent event) {
        listener.changeSelfInter();
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

    public VBox getList() {
        return MapList;
    }

    public interface Listener {
        void importMap();
        void addMap();
        void clearGraph();
        void launchMapOverlay();
        void setSweepColor(Color value);
        void showQ();
        void ShowT();
        void resetQ();
        void autoStep();
        void changeSelfInter();
        void showRésult();
    }

}
