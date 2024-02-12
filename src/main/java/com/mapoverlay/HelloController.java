package com.mapoverlay;

import com.mapoverlay.model.AVLTree;
import com.mapoverlay.model.MapOverlay;
import com.mapoverlay.model.Point;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    MapOverlay MO = new MapOverlay();

    @FXML
    protected void onHelloButtonClick() {
        Point p = MO.testIntersection();
        if(p != null){
            welcomeText.setText(p.getX() + "x " + p.getY() + "Y ");
        }else{
            welcomeText.setText("plus de valeur dans Q");
        }
    }
}