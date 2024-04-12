package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Data;
import com.mapoverlay.model.datastructure.AVLTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    @Test
    void testInsert() {
        AVLTree tree = new AVLTree() {
            @Override
            public void insert(Data d) {
                setData(d);
            }

            @Override
            public AVLTree getLeftTree() {
                return null;
            }

            @Override
            public AVLTree getRightTree() {
                return null;
            }

            @Override
            public Data getData() {
                return data;
            }
        };
        assertTrue(tree.isEmpty());
    }
}
