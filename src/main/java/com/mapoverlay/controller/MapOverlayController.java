package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.dataStructure.QTree;
import com.mapoverlay.model.dataStructure.TTree;
import com.mapoverlay.view.MapOverlayViewController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapOverlayController {
    private Stage stage;

    private MapOverlayViewController MOVC;

    private CanvasController CC;

    private List<Map> maps = new ArrayList<>();

    private TreeController showQTreeController;

    private TreeController showTTreeController;

    private boolean autoStep = false;

    public void show() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MapOverlayViewController.class.getResource("mapoverlay-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MOVC = fxmlLoader.getController();
        MOVC.setListener(new MapOverlayViewController.Listener() {
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
                updateView();
            }

            @Override
            public void addMap() {
                Map newMap = new Map(new ArrayList<>(),Color.BLACK);
                maps.add(newMap);
                updateView();
            }

            @Override
            public void clearGraph() {
                maps.clear();
                CC.clear();
                updateListView();
            }

            @Override
            public void launchMapOverlay() {
                Point p = listener.computeMapOverlayStep();
                if(p != null){
                    CC.MakeSweepLine(p.getX(),p.getY());
                    updateShowTree();
                }
            }

            @Override
            public void setSweepColor(Color value) {
                CC.setSweepColor(value);
            }

            @Override
            public void showQ() {
                showQTreeController = new TreeController(listener.getQTree());
                showQTreeController.setListener(() -> showQTreeController = null);
            }

            @Override
            public void ShowT() {
                showTTreeController = new TreeController(listener.getTTree());
                showTTreeController.setListener(() -> showTTreeController = null);
            }

            @Override
            public void resetQ() {
                CC.clearSweep();
                InitQ();
            }

            @Override
            public void autoStep() {
                autoStep = !autoStep;
            }
        });
        CreateCanvas();
        stage.setTitle("MapOverlay project of Odan and Steve");
        stage.setScene(scene);
        stage.show();
        StartDaemon();
    }

    private void StartDaemon() {
        Thread thread = new Thread(() -> {
            while (true){
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                if(autoStep){
                    Platform.runLater(() ->{
                        Point p = listener.computeMapOverlayStep();
                        if(p != null){
                            CC.MakeSweepLine(p.getX(),p.getY());
                            updateShowTree();
                        }
                    });
                }else {

                }

            }

        });
        thread.setDaemon(true);
        thread.start();
    }

    private void updateShowTree(){
        if(showQTreeController != null){
            showQTreeController.setNewTree(listener.getQTree());
        }
        if(showTTreeController != null){
            showTTreeController.setNewTree(listener.getTTree());
        }
    }

    public void InitQ() {
        List<Segment> segments = new ArrayList<>();
        for(Map m : maps){
            segments.addAll(m.getSegments());
        }
        listener.InitQ(segments);
    }

    private void CreateCanvas(){
        CC = new CanvasController(MOVC.getCanvasContainer());
        updateCanvas();
    }

    public void updateView(){
        InitQ();
        updateListView();
        updateCanvas();
        updateShowTree();
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
                MIC.setListener(new MapItemController.Listener() {
                    @Override
                    public void deleteMap(Map map) {
                        maps.remove(map);
                        updateView();
                    }

                    @Override
                    public void update() {
                        updateView();
                    }

                    @Override
                    public void updateColor() {
                        updateCanvas();
                    }
                });
                list.getChildren().add(MIC.getPane());
            } catch (IOException e) {
                // TODO
            }
        }
    }

    // Listener implementation
    private listener listener;

    public void setListener(listener listener) {
        this.listener = listener;
    }

    public interface listener {
        void InitQ(List<Segment> segments);

        Point computeMapOverlayStep();

        QTree getQTree();

        TTree getTTree();
    }

}
