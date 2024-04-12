package com.mapoverlay.view.graph;

import com.mapoverlay.model.datastructure.AVLTree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GraphicBinaryTree extends CanvasBasic{
    private static final int RADIUSY = 20;
    private AVLTree tree = null;
    private double spacingX;
    private double treeX = 0,treeY = 0;

    public GraphicBinaryTree(int width, int height, AVLTree tree,double spacingX) {
        super(width, height);
        setTree(tree,0,0,spacingX);
    }

    @Override
    public void update() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        currentCenterX = centerX + offsetX;
        currentCenterY = centerY + offsetY;

        drawTree(gc,tree,treeX,treeY,spacingX);
    }

    public void setTree(AVLTree tree, double x, double y,double spacingX){
        this.tree = tree;
        this.spacingX = (tree.getHeight()+1)*spacingX*10;
        this.treeX = x;
        this.treeY = y;
        update();
    }
    public void drawTree(GraphicsContext gc,AVLTree tree, double x, double y,double spacingX) {
        if (tree != null && tree.getData() != null) {
            String data = String.valueOf(tree.getData());
            // Draw node
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.fillOval(currentCenterX+(x - data.length()*4)*zoomFactor, currentCenterY+(y - RADIUSY)*zoomFactor, (2 *  data.length()*4)*zoomFactor, (2 * RADIUSY)*zoomFactor);
            gc.strokeOval(currentCenterX+(x -  data.length()*4)*zoomFactor, currentCenterY+(y - RADIUSY)*zoomFactor, (2 *  data.length()*4)*zoomFactor, (2 * RADIUSY)*zoomFactor);
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Arial", (12*zoomFactor)));
            gc.fillText(data, currentCenterX+((x-8-(data.length()*2))*zoomFactor), currentCenterY+((y+ 4)*zoomFactor));
            int balance = tree.getRightTree().getHeight() - tree.getLeftTree().getHeight();
            gc.setFill(Color.RED);
            gc.fillText(String.valueOf(balance) , currentCenterX+((x-(data.length()*2))*zoomFactor), currentCenterY+((y-20)*zoomFactor));

            // Draw left child
            if (tree.getLeftTree().getData() != null) {
                double newX = x - spacingX;
                double newY = y + 100;
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1.5);
                gc.strokeLine(currentCenterX+x*zoomFactor, currentCenterY+(y+RADIUSY)*zoomFactor, currentCenterX+newX*zoomFactor, currentCenterY+newY*zoomFactor);
                drawTree(gc,tree.getLeftTree(), newX, newY, spacingX / 2);
            }

            // Draw right child
            if (tree.getRightTree().getData() != null) {
                double newX = x + spacingX;
                double newY = y + 100;
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1.5);
                gc.strokeLine(currentCenterX+x*zoomFactor, currentCenterY+(y+RADIUSY)*zoomFactor, currentCenterX+newX*zoomFactor, currentCenterY+newY*zoomFactor);
                drawTree(gc,tree.getRightTree(), newX, newY, spacingX / 2);
            }
        }
    }

    public void setNewTree(AVLTree tree) {
        this.tree = tree;
        update();
    }

    public void upSpacing() {
        this.spacingX += 20;
        update();
    }

    public void downSpacing() {
        if(spacingX > 30){
            this.spacingX -= 20;
            update();
        }
    }
}
