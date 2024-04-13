package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.view.MapItemListViewController;
import com.mapoverlay.view.SegmentItemViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Contrôleur responsable de la gestion des éléments de carte individuels dans la vue de liste des cartes.
 * Permet à l'utilisateur d'interagir avec une carte spécifique, de la sauvegarder, de la supprimer, de modifier sa couleur et d'ajouter de nouveaux segments.
 */
public class MapItemController {

    private Stage stage;

    private AnchorPane pane;
    private Map map;

    MapItemListViewController MILVC;

    /**
     * Crée une nouvelle instance du contrôleur.
     * @param map La carte associée au contrôleur.
     * @param currentStage La fenêtre actuelle.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la vue FXML.
     */
    public MapItemController(Map map, Stage currentStage) throws IOException {
        this.stage = currentStage;
        this.map = map;
        FXMLLoader loader = new FXMLLoader(MapItemListViewController.class.getResource("map-list-view.fxml"));
        pane = loader.load();
        MILVC = loader.getController();
        MILVC.setListener(new MapItemListViewController.Listener() {
            @Override
            public void saveMap() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Enregistrer les segments");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
                File file = fileChooser.showSaveDialog(stage);
                saveSegmentsToFile(file);
            }

            @Override
            public void deleteMap() {
                listener.deleteMap(map);
            }

            @Override
            public void updateColor(Color c) {
                map.setColor(c);
                listener.updateColor();
            }

            @Override
            public void addSegment() {
                try {
                    NewSegmentController NSC = new NewSegmentController();
                    NSC.show(map);
                    NSC.setListener((segment) -> {
                        map.addSegment(segment);
                        update();
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void colapse() {
                map.setExtended(!map.getExtended());
            }
        });
        MILVC.setColor(map.getColor());
        updateSegment();
    }

    /**
     * Met à jour l'affichage des segments dans la vue de liste des segments pour une carte spécifique.
     * Les segments sont récupérés à partir de la carte associée, puis chaque segment est ajouté à la liste avec un contrôleur de segment correspondant.
     * L'auditeur est défini pour chaque contrôleur de segment afin de permettre la suppression du segment à partir de la carte lorsqu'un segment est supprimé dans la vue.
     */
    private void updateSegment() {
        TitledPane list = MILVC.getList();
        list.setExpanded(map.getExtended());
        VBox vBox = new VBox();
        for(Segment s: map.getSegments()){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SegmentItemViewController.class.getResource("segment-view.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                SegmentItemViewController SIVC = fxmlLoader.getController();
                SIVC.setSegment(s);
                SIVC.setListener(segment -> {
                    map.deleteSegment(segment);
                    update();
                });
                vBox.getChildren().add(anchorPane);
            } catch (IOException e) {
                // TODO
            }
        }
        list.setContent(vBox);
    }


    /**
     * Renvoie l'objet AnchorPane utilisé dans la classe MapItemController.
     * Cet objet contient les éléments graphiques associés à un élément de carte.
     * Il est utilisé pour afficher les segments et gérer les interactions utilisateur dans l'interface graphique.
     *
     * @return L'objet AnchorPane utilisé dans la classe MapItemController.
     */
    public AnchorPane getPane() {
        return pane;
    }


    /**
     * Déclenche la mise à jour de la vue associée à un élément de carte.
     * Cette méthode notifie le listener associé pour mettre à jour la vue en conséquence.
     */
    public void update(){
        listener.update();
    }

    /**
     * Enregistre les segments de la carte dans un fichier.
     * Chaque segment est écrit dans le fichier sous forme de coordonnées de points de début et de fin.
     *
     * @param file Le fichier dans lequel enregistrer les segments.
     */
    private void saveSegmentsToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Segment segment : map.getSegments()) {
                // Écrire les coordonnées des points de début et de fin dans le fichier
                writer.write(segment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Listener implementation
    private Listener listener;

    /**
     * Définit le listener pour les événements de cette classe.
     *
     * @param listener L'objet qui implémente l'interface Listener pour gérer les événements.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Interface pour gérer les événements de MapItemController.
     */
    public interface Listener {
        /**
         * Appelé lorsqu'une carte est supprimée.
         *
         * @param map La carte qui a été supprimée.
         */
       void deleteMap(Map map);

        /**
         * Appelé lorsqu'une mise à jour est nécessaire.
         */
        void update();

        /**
         * Appelé lorsqu'une couleur est mise à jour.
         */
        void updateColor();
    }
}
