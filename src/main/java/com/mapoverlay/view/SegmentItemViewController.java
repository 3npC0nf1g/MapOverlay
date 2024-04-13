package com.mapoverlay.view;

import com.mapoverlay.model.data.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Contrôleur pour la vue d'un élément de segment.
 */
public class SegmentItemViewController {

    @FXML
    private Label endPointLabel;

    @FXML
    private Label startPointLabel;

    private Segment segment;

    /**
     * Définit le segment à afficher dans la vue.
     *
     * @param segment Le segment à afficher.
     */
    public void setSegment(Segment segment){
        this.segment = segment;
        startPointLabel.setText(segment.getSPoint().toString());
        endPointLabel.setText(segment.getEPoint().toString());
    }

    /**
     * Méthode appelée lorsqu'un utilisateur clique sur le bouton de suppression de segment.
     *
     * @param event L'événement associé au clic sur le bouton.
     */
    @FXML
    void removeSegment(ActionEvent event) {
        listener.removeSegment(segment);
    }

    // Listener implementation
    private Listener listener;


    /**
     * Définit le listener pour écouter les événements de suppression de segment.
     *
     * @param listener Le listener à définir.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Interface pour écouter les événements de suppression de segment.
     */
    public interface Listener{
        /**
         * Méthode appelée lorsqu'un segment doit être supprimé.
         *
         * @param segment Le segment à supprimer.
         */
        void removeSegment(Segment segment);
    }

}
