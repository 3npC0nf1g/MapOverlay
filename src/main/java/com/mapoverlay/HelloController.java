package com.mapoverlay;

import com.mapoverlay.model.MapOverlay;
import com.mapoverlay.model.data.Segment;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {


    @FXML
    private Canvas canvas;

    @FXML
    private Canvas mapOverlayCanvas;

    MapOverlay MO = new MapOverlay();

    @FXML
    protected void onSaveButtonClick() {

    }

    @FXML
    protected void onImportButtonClick() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des fichiers de segments de droite");

        // Définir un filtre pour ne montrer que les fichiers avec l'extension .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue de sélection de fichiers
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Calculer la largeur et la hauteur totale des fichiers
           double totalWidth = 0;
            double totalHeight = 0;

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
                            totalWidth = Math.max(totalWidth, Math.max(x1, x2));
                            totalHeight = Math.max(totalHeight, Math.max(y1, y2));

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Calculer la position de départ pour centrer les fichiers
            double startX = (canvas.getWidth() - totalWidth) / 2;
            double startY = (canvas.getHeight() - totalHeight) / 2;

            // Dessiner chaque fichier centré sur le canvas
            for (File selectedFile : selectedFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] coords = line.trim().split("\\s+");
                        if (coords.length == 4) {
                            double x1 = Double.parseDouble(coords[0]) + startX;
                            double y1 = Double.parseDouble(coords[1]) + startY;
                            double x2 = Double.parseDouble(coords[2]) + startX;
                            double y2 = Double.parseDouble(coords[3]) + startY;

                            gc.setStroke(Color.BLACK);
                            gc.strokeLine(x1, y1, x2, y2);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Dessiner le repère gradué au centre du canvas
            drawAxes(gc, canvas.getWidth(), canvas.getHeight(), startX, startY);
        }
    }

    private void drawAxes(GraphicsContext gc, double width, double height, double startX, double startY) {
        // Calculer le centre du canvas
        double centerX = width / 2;
        double centerY = height / 2;

        // Dessiner l'axe x
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0, centerY, width, centerY);

        // Dessiner l'axe y
        gc.strokeLine(centerX, 0, centerX, height);

        // Dessiner les graduations sur l'axe x
        gc.setStroke(Color.RED);
        for (int i = -5; i <= 5; i++) {
            double x = centerX + i * 50;
            gc.strokeLine(x, centerY - 5, x, centerY + 5);
            gc.strokeText(Integer.toString(i * 50), x - 10, centerY + 20);
        }

        // Dessiner les graduations sur l'axe y
        for (int i = -5; i <= 5; i++) {
            double y = centerY + i * 50;
            gc.strokeLine(centerX - 5, y, centerX + 5, y);
            gc.strokeText(Integer.toString(-i * 50), centerX + 10, y + 5);
        }
    }

}