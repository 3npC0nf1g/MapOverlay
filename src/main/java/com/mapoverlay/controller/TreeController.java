package com.mapoverlay.controller;

import com.mapoverlay.model.datastructure.AVLTree;
import com.mapoverlay.view.TreeViewController;
import com.mapoverlay.view.graph.GraphicBinaryTree;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TreeController {
    Stage stage;
    TreeViewController launchViewController;

    GraphicBinaryTree graphicTree;

    public TreeController(AVLTree tree){
        try {
            stage = new Stage();
            stage.setOnCloseRequest(windowEvent -> {
                listener.closeTreeView();
                stage.close();
            });
            FXMLLoader fxmlLoader = new FXMLLoader(TreeViewController.class.getResource("launch-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            launchViewController = fxmlLoader.getController();
            stage.setTitle("Show Tree");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        graphicTree = new GraphicBinaryTree(1280,720,tree,20);
        launchViewController.setGraphic(graphicTree);
        launchViewController.setListener(new TreeViewController.listener() {
            @Override
            public void upSpacing() {
                graphicTree.upSpacing();
            }

            @Override
            public void downSpacing() {
                graphicTree.downSpacing();
            }
        });
    }

    public void update(){
        graphicTree.update();
    }




    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setNewTree(AVLTree tree) {
        graphicTree.setNewTree(tree);
    }

    public interface Listener {
        void closeTreeView();
    }
}
