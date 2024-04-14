package com.mapoverlay.controller;

import com.mapoverlay.model.data.Map;
import com.mapoverlay.model.data.point.InterserctionPoint;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.datastructure.QTree;
import com.mapoverlay.model.datastructure.TTree;
import com.mapoverlay.view.MapOverlayViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe est responsable de la gestion de la superposition de cartes dans l'application.
 */
public class MapOverlayController {
    private Stage stage;

    private MapOverlayViewController MOVC;

    private CanvasController CC;

    private List<Map> maps = new ArrayList<>();

    private TreeController showQTreeController;

    private TreeController showTTreeController;

    private boolean autoStep = false;

    /**
     * Affiche l'interface utilisateur de l'application MapOverlay.
     * Cette méthode initialise la fenêtre principale, définit les contrôleurs et les listeners,
     * puis affiche la fenêtre.
     *
     * @throws IOException Si une erreur d'entrée-sortie se produit lors du chargement du fichier FXML.
     */
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
                                Segment s = new Segment(new Point(x1,y1),new Point(x2,y2), map.getId());
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

            @Override
            public void changeSelfInter() {
                updateView();
                CC.clearSweep();
                listener.changeSelfInter();
            }

            @Override
            public void showRésult() {
                List<InterserctionPoint> interserctionPoints = new ArrayList<>();
                Point p = listener.computeMapOverlayStep();
                while (p != null){
                    if(p instanceof InterserctionPoint){
                        interserctionPoints.add((InterserctionPoint) p);
                    }
                    p = listener.computeMapOverlayStep();
                }
                new ResultController(interserctionPoints);
                resetQ();
            }
        });
        CreateCanvas();
        stage.setTitle("MapOverlay project of Odan and Steve");
        stage.setScene(scene);
        stage.show();
        StartDaemon();
    }

    /**
     * Lance un thread daemon pour exécuter périodiquement une action.
     * Cette méthode crée un thread qui exécute une action toutes les 2 secondes,
     * si le drapeau `autoStep` est activé.
     */
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
                }

            }

        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Met à jour l'affichage des arbres Q et T dans les contrôleurs associés.
     * Cette méthode vérifie si les contrôleurs d'affichage des arbres Q et T sont
     * initialisés et, le cas échéant, met à jour les arbres affichés avec les
     * nouvelles données obtenues du listener.
     */
    private void updateShowTree(){
        if(showQTreeController != null){
            showQTreeController.setNewTree(listener.getQTree());
        }
        if(showTTreeController != null){
            showTTreeController.setNewTree(listener.getTTree());
        }
    }

    /**
     * Initialise la structure de données de l'arbre Q en utilisant une liste de segments
     * obtenue à partir des cartes fournies. Cette méthode récupère tous les segments
     * des cartes et les passe au listener pour initialiser l'arbre Q.
     */
    public void InitQ() {
        List<Segment> segments = new ArrayList<>();
        for(Map m : maps){
            segments.addAll(m.getSegments());
        }
        listener.InitQ(segments);
    }

    /**
     * Crée un contrôleur de toile en utilisant le conteneur de toile de la vue MapOverlayViewController.
     * Cette méthode initialise le contrôleur de toile et met à jour la toile.
     */
    private void CreateCanvas(){
        CC = new CanvasController(MOVC.getCanvasContainer());
        updateCanvas();
    }

    /**
     * Met à jour la vue en effectuant les actions suivantes :
     * 1. Initialise la structure de données QTree.
     * 2. Met à jour la liste des segments affichée.
     * 3. Met à jour la toile.
     * 4. Met à jour l'affichage des arbres Q et T.
     */
    public void updateView(){
        InitQ();
        updateListView();
        updateCanvas();
        updateShowTree();
    }

    /**
     * Met à jour la toile de l'application en affichant les segments actuels.
     */
    private void updateCanvas(){
        CC.setMap(maps);
    }

    /**
     * Met à jour la vue de la liste des cartes dans l'interface utilisateur.
     */
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

    /**
     * Définit un écouteur pour cette classe.
     *
     * @param listener L'objet écoutant qui implémente l'interface Listener.
     */
    public void setListener(listener listener) {
        this.listener = listener;
    }

    /**
     * Une interface décrivant les méthodes que les écouteurs doivent implémenter pour écouter les événements émis par une classe.
     */
    public interface listener {

        /**
         * Initialise la structure de données Q avec une liste de segments donnée.
         *
         * @param segments La liste des segments à utiliser pour initialiser la structure de données Q.
         */
        void InitQ(List<Segment> segments);

        /**
         * Calcule l'étape suivante dans le traitement de la superposition de carte.
         *
         * @return Le prochain point à utiliser dans le traitement de la superposition de carte.
         */
        Point computeMapOverlayStep();

        /**
         * Renvoie la structure de données Q utilisée dans le traitement de la superposition de carte.
         *
         * @return La structure de données Q utilisée dans le traitement de la superposition de carte.
         */
        QTree getQTree();

        /**
         * Renvoie la structure de données T utilisée dans le traitement de la superposition de carte.
         *
         * @return La structure de données T utilisée dans le traitement de la superposition de carte.
         */
        TTree getTTree();

        /**
         * Change l'état de l'option de détection des auto-intersections.
         */
        void changeSelfInter();
    }

}
