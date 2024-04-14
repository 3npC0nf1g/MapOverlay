package com.mapoverlay.model.data.point;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point p1 = new Point(1,4);
    Point p2 = new Point(3,4);
    Point p3 = new Point(1,2);

    /**
     * test si un point est plus grand qu'un autre
     */
    @Test
    void testIsHigherThan() {
       assertTrue(p1.isHigherThan(p2));
        assertTrue(p1.isHigherThan(p3));
        assertTrue(p2.isHigherThan(p3));
        assertFalse(p1.isHigherThan(p1));
        assertFalse(p2.isHigherThan(p1));
    }

    /**
     * test si un point est à gauche d'un autre
     */
    @Test
    void testIsLeftOf() {
        assertTrue(p1.isLeftOf(p2));
        assertFalse(p2.isLeftOf(p3));

        // même coord X
        assertTrue(p1.isLeftOf(p3));
        assertTrue(p3.isLeftOf(p1));
    }
}