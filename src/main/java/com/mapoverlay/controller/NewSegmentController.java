package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.view.NewSegmentViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Une classe de contrôleur pour la création de nouveaux segments.
 */
public class NewSegmentController {
    Stage stage;

    /**
     * Affiche la fenêtre de création de segment.
     *
     * @param map La carte à laquelle le segment sera ajouté.
     * @throws IOException Si une erreur survient lors du chargement du fichier FXML.
     */

    public void show(Map map) throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(NewSegmentViewController.class.getResource("new-segment-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        NewSegmentViewController NSVC = fxmlLoader.getController();
        NSVC.setListener((x1, y1, x2, y2) -> {
            listener.addSegment(new Segment(new Point(x1,y1),new Point(x2,y2), map.getId()));
            stage.close();
        });
        stage.setTitle("Ajouter un segment");
        stage.setScene(scene);
        stage.show();
    }


    private listener listener;


    /**
     * Définit le listener pour ce contrôleur.
     *
     * @param listener Le listener à définir.
     */
    public void setListener(listener listener) {
        this.listener = listener;
    }


    /**
     * Une interface pour écouter les événements de création de nouveaux segments.
     */
    public interface listener {

        /**
         * Méthode appelée lorsqu'un nouveau segment est ajouté.
         *
         * @param segment Le segment ajouté.
         */
        void addSegment(Segment segment);
    }
}
