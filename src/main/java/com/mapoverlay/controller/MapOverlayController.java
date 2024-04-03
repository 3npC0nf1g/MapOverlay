package com.mapoverlay.controller;

import com.mapoverlay.model.data.InterserctionPoint;
import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.view.MapOverlayViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapOverlayController implements MapOverlayViewController.Listener,MapItemController.Listener {
    private Stage stage;

    private MapOverlayViewController MOVC;

    private CanvasController CC;

    private List<Map> maps = new ArrayList<>();

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

    @Override
    public void update(){
        updateListView();
        updateCanvas();
    }

    private void updateCanvas(){
        CC.setMap(maps);
    }

    private void updateListView(){
        VBox list = MOVC.getList();
        list.getChildren().clear();
        for(Map m : maps){
            try {
                MapItemController MIC = new MapItemController(m,stage);
                MIC.setListener(this);
                list.getChildren().add(MIC.getPane());
            } catch (IOException e) {
                // TODO
            }
        }
    }

    @Override
    public void importMap() {
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
                Map map = new Map(new ArrayList<>(), Color.BLACK);
                while ((line = reader.readLine()) != null) {
                    String[] coords = line.trim().split("\\s+");
                    if (coords.length == 4) {
                        double x1 = Double.parseDouble(coords[0]);
                        double y1 = Double.parseDouble(coords[1]);
                        double x2 = Double.parseDouble(coords[2]);
                        double y2 = Double.parseDouble(coords[3]);
                        Segment s = new Segment(new Point(x1,y1),new Point(x2,y2));
                        map.addSegment(s);
                    }
                }
                maps.add(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        update();
    }

    @Override
    public void addMap() {
        Map newMap = new Map(new ArrayList<>(),Color.BLACK);
        maps.add(newMap);
        update();
    }

    @Override
    public void clearGraph() {
        maps.clear();
        update();
    }

    @Override
    public void deleteMap(Map map) {
        maps.remove(map);
        update();
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
