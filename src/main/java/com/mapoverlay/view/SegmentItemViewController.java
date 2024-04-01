package com.mapoverlay.view;

import com.mapoverlay.model.data.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SegmentItemViewController {

    @FXML
    private Label endPointLabel;

    @FXML
    private Label startPointLabel;

    private Segment segment;

    void setSegment(Segment segment){
        this.segment = segment;
        startPointLabel.setText(segment.getSPoint().toString());
        endPointLabel.setText(segment.getEPoint().toString());
    }

    @FXML
    void removeSegment(ActionEvent event) {
        listener.removeSegment(segment);
    }

    // Listener implementation
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public interface Listener{
        void removeSegment(Segment segment);
    }

}
