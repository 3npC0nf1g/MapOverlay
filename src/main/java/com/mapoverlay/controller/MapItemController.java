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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapItemController {

    private Stage stage;

    private AnchorPane pane;
    private Map map;

    MapItemListViewController MILVC;

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
                update();
            }

            @Override
            public void addSegment() {
                try {
                    NewSegmentController NSC = new NewSegmentController();
                    NSC.show();
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

    public AnchorPane getPane() {
        return pane;
    }

    public void update(){
        listener.update();
    }

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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
       void deleteMap(Map map);
       void update();
    }
}
