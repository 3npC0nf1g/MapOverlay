package com.mapoverlay.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewSegmentViewController {

    @FXML
    private TextField x1;

    @FXML
    private TextField x2;

    @FXML
    private TextField y1;

    @FXML
    private TextField y2;

    @FXML
    void CreateSegment(ActionEvent event) {
        try {
            double dx1 = Double.parseDouble(x1.getText());
            double dy1 = Double.parseDouble(y1.getText());
            double dx2 = Double.parseDouble(x2.getText());
            double dy2 = Double.parseDouble(y2.getText());
            listener.addSegment(dx1,dy1,dx2,dy2);
        }catch (Exception e){

        }
    }

    // Listener implementation
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void addSegment(double x1,double y1,double x2,double y2);
    }
}

