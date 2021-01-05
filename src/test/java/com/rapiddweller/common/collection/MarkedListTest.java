package com.rapiddweller.common.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class MarkedListTest {
    @Test
    public void testMark() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertFalse((new MarkedList<Object>(objectList)).mark(1));
    }

    @Test
    public void testIsMarked() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertFalse((new MarkedList<Object>(objectList)).isMarked(1));
    }

    @Test
    public void testUnmark() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertFalse((new MarkedList<Object>(objectList)).unmark(1));
    }

    @Test
    public void testGetMarkedElements() {
        assertTrue((new MarkedList<Object>(new ArrayList<Object>())).getMarkedElements().isEmpty());
    }

    @Test
    public void testGetMarkedElements2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertTrue((new MarkedList<Object>(objectList)).getMarkedElements().isEmpty());
    }

    @Test
    public void testGetUnmarkedElements() {
        assertTrue((new MarkedList<Object>(new ArrayList<Object>())).getUnmarkedElements().isEmpty());
    }

    @Test
    public void testGetUnmarkedElements2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertEquals(1, (new MarkedList<Object>(objectList)).getUnmarkedElements().size());
    }

    @Test
    public void testAdd() {
        assertTrue((new MarkedList<Object>(new ArrayList<Object>())).add("element"));
    }

    @Test
    public void testAddAll() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        MarkedList<Object> objectList1 = new MarkedList<Object>(objectList);
        assertFalse(objectList1.addAll(1, new ArrayList<Object>()));
    }

    @Test
    public void testAddAll2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertTrue((new MarkedList<Object>(objectList)).addAll(1, objectList1));
    }

    @Test
    public void testAddAll3() {
        MarkedList<Object> objectList = new MarkedList<Object>(new ArrayList<Object>());
        assertFalse(objectList.addAll(new ArrayList<Object>()));
    }

    @Test
    public void testAddAll4() {
        MarkedList<Object> objectList = new MarkedList<Object>(new ArrayList<Object>());
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertTrue(objectList.addAll(objectList1));
    }

    @Test
    public void testRemove() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertEquals("e", (new MarkedList<Object>(objectList)).remove(1));
    }

    @Test
    public void testRemove2() {
        assertFalse((new MarkedList<Object>(new ArrayList<Object>())).remove("element"));
    }

    @Test
    public void testRemove3() {
        MarkedList<Object> objectList = new MarkedList<Object>(new ArrayList<Object>());
        objectList.add("element");
        assertTrue(objectList.remove("element"));
    }

    @Test
    public void testRemoveAll() {
        MarkedList<Object> objectList = new MarkedList<Object>(new ArrayList<Object>());
        assertFalse(objectList.removeAll(new ArrayList<Object>()));
    }

    @Test
    public void testRemoveAll2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        MarkedList<Object> objectList1 = new MarkedList<Object>(objectList);
        assertFalse(objectList1.removeAll(new ArrayList<Object>()));
    }

    @Test
    public void testRemoveAll3() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertTrue((new MarkedList<Object>(objectList)).removeAll(objectList1));
    }

    @Test
    public void testRemoveAll4() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(2);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertFalse((new MarkedList<Object>(objectList)).removeAll(objectList1));
    }

    @Test
    public void testRetainAll() {
        MarkedList<Object> objectList = new MarkedList<Object>(new ArrayList<Object>());
        assertFalse(objectList.retainAll(new ArrayList<Object>()));
    }

    @Test
    public void testRetainAll2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        MarkedList<Object> objectList1 = new MarkedList<Object>(objectList);
        assertFalse(objectList1.retainAll(new ArrayList<Object>()));
    }

    @Test
    public void testRetainAll3() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertFalse((new MarkedList<Object>(objectList)).retainAll(objectList1));
    }

    @Test
    public void testRetainAll4() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(2);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertFalse((new MarkedList<Object>(objectList)).retainAll(objectList1));
    }

    @Test
    public void testSubList() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertTrue((new MarkedList<Object>(objectList)).subList(1, 1).isEmpty());
    }

    @Test
    public void testConstructor() {
        assertTrue((new MarkedList<Object>(new ArrayList<Object>())).isEmpty());
    }

    @Test
    public void testConstructor2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertEquals(1, (new MarkedList<Object>(objectList)).size());
    }
}

