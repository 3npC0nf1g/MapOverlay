package com.mapoverlay.model.datastructure;

import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.point.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TTreeTest {

    /**
     * test l'insertion dans un TTree
     */
    @Test
    public void testInsert(){
        TTree tTree = new TTree();

        assertTrue(tTree.isEmpty());

        Segment s1 = new Segment(new Point(1,1),new Point(5,5));
        Segment s2 = new Segment(new Point(1,5),new Point(5,1));

        List<Segment> segments = new ArrayList<>();
        segments.add(s1);
        segments.add(s2);

        tTree.setCurrentPoint(new Point(4,2)); // le point actuel de la sweep line
        for (Segment s : segments){
            tTree.insert(s);
        }

        assertTrue(tTree.data == s1);
        assertTrue(tTree.getLeftTree().data == s1);
        assertTrue(tTree.getRightTree().data == s2);

        tTree = new TTree();
        assertTrue(tTree.isEmpty());
        tTree.setCurrentPoint(new Point(2,4)); // le point actuel de la sweep line
        for (Segment s : segments){
            tTree.insert(s);
        }
        assertTrue(tTree.data == s2);
        assertTrue(tTree.getLeftTree().data == s2);
        assertTrue(tTree.getRightTree().data == s1);
    }

    /**
     * test la récuperation d'une feuille avec le segment voulu
     */
    @Test
    public void testFindLeaf(){
        TTree tTree = new TTree();

        assertTrue(tTree.isEmpty());

        Segment s1 = new Segment(new Point(1,1),new Point(5,5));
        Segment s2 = new Segment(new Point(1,5),new Point(5,1));
        Segment s3 = new Segment(new Point(1,5),new Point(5,1));

        List<Segment> segments = new ArrayList<>();
        segments.add(s1);
        segments.add(s2);
        segments.add(s3);


        tTree.setCurrentPoint(new Point(4,2)); // le point actuel de la sweep line
        for (Segment s : segments){
            tTree.insert(s);
        }

        TTree leave = tTree.findLeave(s2);
        assertSame(leave.data, s2);
        assertTrue(leave.isLeaf());
    }

    /**
     * Test le fait de trouvé le voisin de gauche et droite d'un segment dans l'arbre
     */
    @Test
    public void testFindAdjacent(){
        TTree tTree = new TTree();

        assertTrue(tTree.isEmpty());

        Segment s1 = new Segment(new Point(1,1),new Point(5,5));
        Segment s2 = new Segment(new Point(1,5),new Point(5,1));
        Segment s3 = new Segment(new Point(4,2),new Point(1,5));
        Segment s4 = new Segment(new Point(2,5),new Point(2,6));

        List<Segment> segments = new ArrayList<>();
        segments.add(s1);
        segments.add(s2);
        segments.add(s3);
        segments.add(s4);


        tTree.setCurrentPoint(new Point(4,2)); // le point actuel de la sweep line
        for (Segment s : segments){
            tTree.insert(s);
        }

        TTree leave = tTree.findLeave(s2);
        assertSame(leave.data, s2);

        Segment sls2 = tTree.findLeftAdjacentSegment(s2);
        assertSame(sls2, s3);

        Segment srs2 = tTree.findRightAdjacentSegment(s2);
        assertNull(srs2);

        Segment sls3 = tTree.findLeftAdjacentSegment(s3);
        assertSame(sls3, s1);

        Segment srs3 = tTree.findRightAdjacentSegment(s3);
        assertSame(srs3, s2);

        Segment sls1 = tTree.findLeftAdjacentSegment(s1);
        assertSame(sls1, s4);

        Segment sls4 = tTree.findLeftAdjacentSegment(s4);
        assertNull(sls4);
    }

    /**
     * Test de la méthode de delete d'un TTree
     */
    @Test
    public void testDelete(){
        TTree tTree = new TTree();

        assertTrue(tTree.isEmpty());


        Segment s1 = new Segment(new Point(1,1),new Point(5,5));
        Segment s2 = new Segment(new Point(1,5),new Point(5,1));
        Segment s3 = new Segment(new Point(4,2),new Point(1,5));
        Segment s4 = new Segment(new Point(2,5),new Point(2,6));

        List<Segment> segments = new ArrayList<>();
        segments.add(s1);
        segments.add(s2);
        segments.add(s3);
        segments.add(s4);

        tTree.setCurrentPoint(new Point(4,2)); // le point actuel de la sweep line
        for (Segment s : segments){
            tTree.insert(s);
        }

        assertTrue(tTree.getHeight() >= 3 && tTree.getHeight() <= 4);

        TTree tS2 = tTree.findLeave(s2);
        tTree.delete(s2);
        tS2 = tTree.findLeave(s2);
        assertNull(tS2);
        assertTrue(tTree.getHeight() >= 3 && tTree.getHeight() <= 4);
        tTree.delete(s1);
        assertEquals(2, tTree.getHeight());

        assertSame(tTree.getData(),s4);
        assertSame(tTree.getLeftTree().getData(),s4);
        assertSame(tTree.getRightTree().getData(),s3);
    }
}
