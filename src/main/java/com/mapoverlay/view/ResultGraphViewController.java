package com.mapoverlay.view;

import com.mapoverlay.view.graph.GraphicBinaryTree;
import com.mapoverlay.view.graph.GraphicSegment;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ResultGraphViewController {

    @FXML
    private AnchorPane ResultContainer;

    public void setGraphic(GraphicSegment graphicSegment) {
        ResultContainer.getChildren().add(graphicSegment);
    }
}
