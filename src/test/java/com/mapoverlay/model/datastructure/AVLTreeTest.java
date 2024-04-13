package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.point.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    @Test
    void testAVL() {
        QTree qTree = new QTree();
        TTree tTree = new TTree();

        for (int i = 1; i <= 10;i++){
            qTree.insert(new Point(i,i));
        }

        assertTrue(testBalance(qTree));
        QTree LeftTree = qTree.getLeftTree();
        assertTrue(testBalance(LeftTree));
        QTree RightTree = qTree.getRightTree();
        assertTrue(testBalance(RightTree));
    }

    private boolean testBalance(AVLTree tree){
        int balance = tree.getRightTree().getHeight() - tree.getLeftTree().getHeight();
        return balance <= 1 && balance >= -1;
    }
}
