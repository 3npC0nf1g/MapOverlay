package com.mapoverlay.controller;

import com.mapoverlay.model.data.point.InterserctionPoint;
import com.mapoverlay.view.ResultGraphViewController;
import com.mapoverlay.view.TreeViewController;
import com.mapoverlay.view.graph.GraphicSegment;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
/**
 * Contrôleur pour afficher les résultats des calculs.
 */
public class ResultController {

    /**
     * Constructeur de ResultController.
     *
     * @param pointList La liste des points d'intersection à afficher.
     */
    public ResultController(List<InterserctionPoint> pointList){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(ResultGraphViewController.class.getResource("result-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ResultGraphViewController resultController = fxmlLoader.getController();
            stage.setTitle("Show Result");
            stage.setScene(scene);
            stage.show();

            GraphicSegment graphicSegment = new GraphicSegment(1280,720);
            graphicSegment.setInterserctionPoints(pointList);
            resultController.setGraphic(graphicSegment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
