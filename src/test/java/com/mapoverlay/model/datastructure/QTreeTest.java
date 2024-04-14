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
        assertEquals(qTree.getLeftTree().getData(), p2);
    }

    /**
     * test la récupération des prochain points de la sweepline
     */
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
        // ne peut pas utilisé assertSame car fait utilise une copie du point
        assertEquals(qTree.getNextPoint(), p5);
        assertEquals(qTree.getNextPoint(), p6);
        assertEquals(qTree.getNextPoint(), p4);
        assertEquals(qTree.getNextPoint(), p3);
        assertEquals(qTree.getNextPoint(), p1);
        assertEquals(qTree.getNextPoint(), p2);
    }
}
