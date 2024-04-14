package com.mapoverlay.controller;

import com.mapoverlay.model.datastructure.AVLTree;
import com.mapoverlay.view.TreeViewController;
import com.mapoverlay.view.graph.GraphicBinaryTree;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Cette classe est responsable de la gestion de l'affichage graphique d'un arbre AVL.
 */
public class TreeController {
    Stage stage;
    TreeViewController launchViewController;

    GraphicBinaryTree graphicTree;


    /**
     * Constructeur de la classe TreeController.
     *
     * @param tree L'arbre AVL à afficher graphiquement.
     */
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
    /**
     * Met à jour l'affichage de l'arbre graphique.
     */
    public void update(){
        graphicTree.update();
    }

    private Listener listener;

    /**
     * Définit le Listener pour écouter les événements de fermeture de la vue de l'arbre.
     *
     * @param listener L'instance de Listener à définir.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    /**
     * Définit un nouvel arbre à afficher graphiquement.
     *
     * @param tree Le nouvel arbre AVL à afficher.
     */
    public void setNewTree(AVLTree tree) {
        graphicTree.setNewTree(tree);
    }


    /**
     * Interface pour écouter les événements de fermeture de la vue de l'arbre.
     */
    public interface Listener {
        void closeTreeView();
    }
}
