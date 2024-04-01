package com.mapoverlay.controller;

import com.mapoverlay.model.data.Point;
import com.mapoverlay.model.data.Segment;
import com.mapoverlay.view.NewSegmentViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewSegmentController implements NewSegmentViewController.Listener {
    Stage stage;
    public void show() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(NewSegmentViewController.class.getResource("new-segment-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        NewSegmentViewController NSVC = fxmlLoader.getController();
        NSVC.setListener(this);
        stage.setTitle("Ajouter un segment");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void addSegment(double x1, double y1, double x2, double y2) {
        listener.addSegment(new Segment(new Point(x1,y1),new Point(x2,y2)));
        stage.close();
    }

    // Listener implementation
    private listener listener;

    public void setListener(listener listener) {
        this.listener = listener;
    }

    public interface listener {
        void addSegment(Segment segment);
    }
}
