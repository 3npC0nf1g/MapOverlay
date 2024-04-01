package com.mapoverlay.controller;

import com.mapoverlay.model.data.InterserctionPoint;
import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.view.MapOverlayViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapOverlayController implements MapOverlayViewController.Listener {
    private Stage stage;

    private MapOverlayViewController MOVC;

    private CanvasController CC;

    private List<Segment> segments = new ArrayList<>();

    public void show() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MapOverlayViewController.class.getResource("mapoverlay-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MOVC = fxmlLoader.getController();
        MOVC.setListener(this);
        CreateCanvas();
        stage.setTitle("MapOverlay project of Odan and Steve");
        stage.setScene(scene);
        stage.show();
    }

    private void CreateCanvas(){
        CC = new CanvasController(MOVC.getCanvasContainer());
        updateCanvas();
    }

    private void update(){
        updateListView();
        updateCanvas();
    }

    private void updateCanvas(){
        CC.setSegments(segments);
    }

    private void updateListView(){
        MOVC.updateSegmentList(segments);
    }

    @Override
    public void importGraph() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers de segments de droite");

        // Définir un filtre pour ne montrer que les fichiers avec l'extension .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue de sélection de fichiers
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        for (File selectedFile : selectedFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] coords = line.trim().split("\\s+");
                    if (coords.length == 4) {
                        double x1 = Double.parseDouble(coords[0]);
                        double y1 = Double.parseDouble(coords[1]);
                        double x2 = Double.parseDouble(coords[2]);
                        double y2 = Double.parseDouble(coords[3]);
                        Segment s = new Segment(new Point(x1,y1),new Point(x2,y2));
                        segments.add(s);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        update();
    }

    @Override
    public void openAddSegment() throws IOException {
        NewSegmentController NSC = new NewSegmentController();
        NSC.show();
        NSC.setListener((segment) -> {
            segments.add(segment);
            update();
        });
    }

    @Override
    public void deleteSegment(Segment segment) {
        segments.remove(segment);
        update();
    }

    @Override
    public void saveSegmentList() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer les segments");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        saveSegmentsToFile(file);
    }

    private void saveSegmentsToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Segment segment : segments) {
                // Écrire les coordonnées des points de début et de fin dans le fichier
                writer.write(segment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Listener implementation
    private listener listener;

    public void setListener(listener listener) {
        this.listener = listener;
    }

    public interface listener {
        List<InterserctionPoint> computeMapOverlay(List<Segment> segments);
    }

}
