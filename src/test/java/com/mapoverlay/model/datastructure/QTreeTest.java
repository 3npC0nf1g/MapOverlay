package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.point.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QTreeTest {

    @Test
    public void testInsert(){
        QTree qTree = new QTree();
        Point p1 = new Point(1,2);
        Point p2 = new Point(5,3);

        assertTrue(qTree.isEmpty());

        qTree.insert(p1);
        assertTrue(qTree.contains(p1));
        assertTrue(qTree.getLeftTree().isEmpty());
        assertTrue(qTree.getRightTree().isEmpty());

        qTree.insert(p2);
        assertTrue(qTree.contains(p2));
        assertTrue(qTree.getLeftTree().getData() == p2);
    }

    @Test
    public void testGetNextPoint(){
        QTree qTree = new QTree();
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,1);
        Point p3 = new Point(2,2);
        Point p4 = new Point(7,3);
        Point p5 = new Point(1,4);
        Point p6 = new Point(2,4);

        qTree.insert(p1);
        qTree.insert(p2);
        qTree.insert(p3);
        qTree.insert(p4);
        qTree.insert(p5);
        qTree.insert(p6);

        // p5 > p6 > p4 > p3 > p1 > p2
        assertTrue(qTree.getNextPoint() == p5);
        assertTrue(qTree.getNextPoint() == p6);
        assertTrue(qTree.getNextPoint() == p4);
        assertTrue(qTree.getNextPoint() == p3);
        assertTrue(qTree.getNextPoint() == p1);
        assertTrue(qTree.getNextPoint() == p2);
    }
}
