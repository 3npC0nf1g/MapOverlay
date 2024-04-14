package com.mapoverlay.model;

import com.mapoverlay.model.data.Segment;
import com.mapoverlay.model.data.point.Point;
import com.mapoverlay.model.datastructure.QTree;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapOverlayTest {

    MapOverlay MO = new MapOverlay();
    List<Segment> segments = new ArrayList<>();

    /**
     * test la méthode qui renvoie les points pour savoir si elle renvoie les bons points
     */
    @Test
    void testFindInterSectionsStep() {
        Point p1 = new Point(8,8);
        Point p2 = new Point(0,0);
        Point p3 = new Point(0,8);
        Point p4 = new Point(8,0);
        segments.add(new Segment(p1,p2));
        segments.add(new Segment(p3,p4));

        MO.InitQ(segments);

        Point pfind = MO.FindInterSectionsStep();
        assertEquals(p3,pfind); // le plus haut point insert
        pfind = MO.FindInterSectionsStep();
        assertEquals(p1,pfind); // 2e Point
        pfind = MO.FindInterSectionsStep(); // doit avoir trouvé une intersection à (4,4)
        assertEquals(new Point(4,4),pfind);
        pfind = MO.FindInterSectionsStep();
        assertEquals(p2,pfind);
        pfind = MO.FindInterSectionsStep();
        assertEquals(p4,pfind);
        pfind = MO.FindInterSectionsStep();
        assertNull(pfind);
    }

    @Test
    void testFindNewEvent(){
        Point p1 = new Point(8,8);
        Point p2 = new Point(0,0);
        Point p3 = new Point(0,8);
        Point p4 = new Point(8,0);
        Segment sr = new Segment(p1,p2);
        Segment sl = new Segment(p3,p4);

        MO.FindNewEvent(sr,sl,p1);// lorque p1 est rajouté
        Point pfind = MO.q.getNextPoint();
        assertEquals(new Point(4,4),pfind);
    }

    /**
     * test si l'inversion s'effectue bien
     */
    @Test
    void testHandleEventPoint(){
        Point p1 = new Point(8,8);
        Point p2 = new Point(0,0);
        Point p3 = new Point(0,8);
        Point p4 = new Point(8,0);
        Segment s1 = new Segment(p1,p2);
        Segment s2 = new Segment(p3,p4);
        segments.add(s1);
        segments.add(s2);

        MO.InitQ(segments);

        assertNull(MO.t.getData());

        Point pfind = MO.FindInterSectionsStep();
        assertEquals(p3,pfind); // le plus haut point insert
        assertTrue(MO.t.getData() == s2); // a bien s2 init dans t
        assertTrue(MO.t.getHeight() == 1);

        pfind = MO.FindInterSectionsStep();
        assertEquals(p1,pfind); // 2e Point

        assertTrue(MO.t.getData() == s2); // a bien s2 init dans t
        assertTrue(MO.t.getLeftTree().getData() == s2); // a bien s2 init dans t
        assertTrue(MO.t.getRightTree().getData() == s1); // a bien s1 init dans t
        assertTrue(MO.t.getHeight() == 2);

        pfind = MO.FindInterSectionsStep(); // doit avoir trouvé une intersection à (4,4)
        assertEquals(new Point(4,4),pfind); // regarde si l'inversion c'est bien éffectué

        assertTrue(MO.t.getData() == s1); // a bien s2 init dans t
        assertTrue(MO.t.getLeftTree().getData() == s1); // a bien s2 init dans t
        assertTrue(MO.t.getRightTree().getData() == s2); // a bien s1 init dans t
        assertTrue(MO.t.getHeight() == 2);

    }

}