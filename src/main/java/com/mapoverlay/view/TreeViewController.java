package com.mapoverlay.view;

import com.mapoverlay.view.graph.GraphicBinaryTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TreeViewController {
    @FXML
    private AnchorPane TreeContainer;

    @FXML
    void downSpacing(ActionEvent event) {
        listener.downSpacing();
    }

    @FXML
    void upSpacing(ActionEvent event) {
        listener.upSpacing();
    }

    public void setGraphic(GraphicBinaryTree graphicTree) {
        TreeContainer.getChildren().add(graphicTree);
    }

    // Listener implementation
    private listener listener;

    public void setListener(listener listener) {
        this.listener = listener;
    }

    public interface listener {

        void upSpacing();

        void downSpacing();
    }

}
