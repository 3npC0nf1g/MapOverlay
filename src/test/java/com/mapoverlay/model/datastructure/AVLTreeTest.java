package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.datastructure.AVLTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    /**
     * Regarde si un arbre est équilibrer
     */
    @Test
    void testAVL() {
        QTree qTree = new QTree();

        for (int i = 1; i <= 10;i++){
            qTree.insert(new Point(i,i));
        }

        testAllBalance(qTree);
    }

    /**
     * test toutes les balance d'un arbre
     * @param tree l'arbre sur lequel le test est fait ainsi que ces sous arbre
     */
    private void testAllBalance(AVLTree tree){
        assertTrue(testBalance(tree));

        AVLTree LeftTree = tree.getLeftTree();

        if(!LeftTree.isEmpty()){
            assertTrue(testBalance(LeftTree));
            testAllBalance(LeftTree);
        }

        AVLTree RightTree = tree.getRightTree();

        if(!RightTree.isEmpty()){
            assertTrue(testBalance(RightTree));
            testAllBalance(RightTree);
        }
    }

    /**
     * test la balance d'un arbre pour voir si elle est équilibré
     * @param tree l'arbre sur lequel le test est fait
     * @return true si l'arbre est équilibré
     */
    private boolean testBalance(AVLTree tree){
        int balance = tree.getRightTree().getHeight() - tree.getLeftTree().getHeight();
        return balance <= 1 && balance >= -1;
    }
}
