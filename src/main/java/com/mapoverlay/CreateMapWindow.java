package com.mapoverlay;

import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.StartPoint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreateMapWindow extends Stage {

    private Canvas canvas;
    private boolean isFirstPoint = true;
    private double startX, startY;

    public CreateMapWindow(List<Segment> segments) {

        setTitle("Create your own map");
        setWidth(640);
        setHeight(720);

        StackPane root = new StackPane();
        canvas = new Canvas(600, 600);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event -> {
            if (isFirstPoint) {
                startX = event.getX();
                startY = event.getY();
                isFirstPoint = false;
            } else {

                double endX = event.getX();
                double endY = event.getY();

                gc.setStroke(Color.BLACK);
                gc.strokeLine(startX, startY, endX, endY);

                isFirstPoint = true;

                // Ajout du segment à la liste

                StartPoint startPoint = new StartPoint((float) startX, (float) startY);
                Point endPoint = new Point((float) endX, (float) endY);
                Segment segment = new Segment(startPoint, endPoint);
                  segments.add(segment);
            }
        });

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> saveMap(segments));

        Button cancelButton = new Button("Discard");
        cancelButton.setOnAction(event -> close());

        HBox buttonsBox = new HBox(10, saveButton, cancelButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(10));

        VBox layout = new VBox();
        layout.getChildren().addAll(root, buttonsBox); // Ajout du root contenant le canvas
        VBox.setVgrow(root, javafx.scene.layout.Priority.ALWAYS); // Le canvas prendra tout l'espace restant

        Scene scene = new Scene(layout);
        setScene(scene);

        // Définir la modalité de la fenêtre
        initModality(Modality.APPLICATION_MODAL);
    }
    private void saveMap(List<Segment> segments) {

        // Ouvrir une boîte de dialogue pour choisir l'emplacement de sauvegarde
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the map");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(this);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                for (Segment segment : segments) {
                    Point startPoint = segment.getSPoint();
                    Point endPoint = segment.getEPoint();
                    writer.write(startPoint.getX() + " " + startPoint.getY() + " " +
                            endPoint.getX() + " " + endPoint.getY());
                    writer.newLine();
                }
                new Alert(Alert.AlertType.INFORMATION, "Map saved!").showAndWait();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong.").showAndWait();
                e.printStackTrace();
            }

       }

        close();

    }
}
