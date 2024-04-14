package com.mapoverlay.model.data;

import com.mapoverlay.model.data.point.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SegmentTest {

    /**
     * Test l'intersection du segment avec la sweepline
     */
    @Test
    public void TestIntersectSweep(){
        Segment s = new Segment(new Point(1,1),new Point(3,3));
        assertEquals(s.getIntersectSweep(2),new Point(2,2));
        assertTrue(s.contains(new Point(2,2)));
    }

    /**
     * test si le segment est à gauche d'un autre par rapport à la sweepline
     */
    @Test
    public void TestIsLeftOf(){
        Segment s1 = new Segment(new Point(3,3),new Point(5,10));
        Segment s2 = new Segment(new Point(3,3),new Point(10,10));

        Point SweepPoint = new Point(4,4);

        assertTrue(s1.isLeftOf(s2,SweepPoint));
        assertFalse(s2.isLeftOf(s1,SweepPoint));
    }

    /**
     * test de la méthode pour calculer les intersections
     */
    @Test
    public void TestIntersection(){
        Segment s1 = new Segment(new Point(1,1),new Point(3,3));
        Segment s2 = new Segment(new Point(1,3),new Point(3,1));

        Segment s3 = new Segment(new Point(1,3),new Point(3,3)); // horizontal
        Segment s4 = new Segment(new Point(3,1),new Point(3,3)); // vertical

        Point iPoint1 = s1.ComputeIntesectPoint(s2);
        Point iPoint2 = s3.ComputeIntesectPoint(s4);

        assertEquals(iPoint1,new Point(2,2));
        assertEquals(iPoint2,new Point(3,3));
    }
}
