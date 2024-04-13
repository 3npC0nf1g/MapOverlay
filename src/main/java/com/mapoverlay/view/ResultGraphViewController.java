package com.mapoverlay.view;

import com.mapoverlay.view.graph.GraphicBinaryTree;
import com.mapoverlay.view.graph.GraphicSegment;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Contrôleur pour la vue d'affichage du résultat graphique.
 */
public class ResultGraphViewController {

    @FXML
    private AnchorPane ResultContainer;

    /**
     * Définit le composant graphique à afficher dans la vue.
     *
     * @param graphicSegment Le composant graphique à afficher.
     */
    public void setGraphic(GraphicSegment graphicSegment) {
        ResultContainer.getChildren().add(graphicSegment);
    }
}
