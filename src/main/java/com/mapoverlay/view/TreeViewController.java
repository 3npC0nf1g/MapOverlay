package com.mapoverlay.view;

import com.mapoverlay.view.graph.GraphicBinaryTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


/**
 * Contrôleur pour la vue d'un arbre.
 */
public class TreeViewController {
    @FXML
    private AnchorPane TreeContainer;

    /**
     * Méthode appelée lorsqu'un utilisateur clique sur le bouton pour augmenter l'espacement de l'arbre.
     *
     * @param event L'événement associé au clic sur le bouton.
     */
    @FXML
    void downSpacing(ActionEvent event) {
        listener.downSpacing();
    }

    /**
     * Méthode appelée lorsqu'un utilisateur clique sur le bouton pour réduire l'espacement de l'arbre.
     *
     * @param event L'événement associé au clic sur le bouton.
     */
    @FXML
    void upSpacing(ActionEvent event) {
        listener.upSpacing();
    }

    /**
     * Définit le composant graphique représentant l'arbre.
     *
     * @param graphicTree Le composant graphique représentant l'arbre à afficher.
     */
    public void setGraphic(GraphicBinaryTree graphicTree) {
        TreeContainer.getChildren().add(graphicTree);
    }

    // Listener implementation
    private listener listener;


    /**
     * Définit le listener pour écouter les événements liés à l'arbre.
     *
     * @param listener Le listener à définir.
     */
    public void setListener(listener listener) {
        this.listener = listener;
    }

    /**
     * Interface pour écouter les événements liés à l'arbre.
     */
    public interface listener {

        /**
         * Méthode appelée lorsqu'un utilisateur souhaite augmenter l'espacement de l'arbre.
         */
        void upSpacing();

        /**
         * Méthode appelée lorsqu'un utilisateur souhaite réduire l'espacement de l'arbre.
         */
        void downSpacing();
    }

}
