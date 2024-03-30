package com.mapoverlay;

import com.mapoverlay.model.MapOverlay;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HelloController {

    @FXML
    private Canvas canvas;

    MapOverlay MO = new MapOverlay();

    @FXML
    protected void onSaveButtonClick() {

    }

    @FXML
    protected void onImportButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");

        // Définir un filtre pour ne montrer que les fichiers avec l'extension .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] coords = line.trim().split("\\s+");
                    if (coords.length == 4) {
                        double x1 = Double.parseDouble(coords[0]) * 3 ;
                        double y1 = Double.parseDouble(coords[1]) * 3;
                        double x2 = Double.parseDouble(coords[2]) * 3;
                        double y2 = Double.parseDouble(coords[3])* 3 ;
                        gc.setStroke(Color.BLACK);
                        gc.strokeLine(x1, y1, x2, y2);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}