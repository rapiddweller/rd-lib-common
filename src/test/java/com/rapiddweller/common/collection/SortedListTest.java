package com.rapiddweller.common.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.NullSafeComparator;

import java.util.ArrayList;

import org.junit.Test;

public class SortedListTest {
    @Test
    public void testAdd() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertTrue((new SortedList<Object>(baseList, new NullSafeComparator<Object>())).add("e"));
    }

    @Test
    public void testAddAll() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        SortedList<Object> objectList1 = new SortedList<Object>(objectList, new NullSafeComparator<Object>());
        assertFalse(objectList1.addAll(1, new ArrayList<Object>()));
    }

    @Test
    public void testAddAll2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        SortedList<Object> objectList1 = new SortedList<Object>(objectList, new NullSafeComparator<Object>());
        ArrayList<Object> objectList2 = new ArrayList<Object>();
        objectList2.add("e");
        assertTrue(objectList1.addAll(1, objectList2));
    }

    @Test
    public void testAddAll3() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        SortedList<Object> objectList = new SortedList<Object>(baseList, new NullSafeComparator<Object>());
        assertFalse(objectList.addAll(new ArrayList<Object>()));
    }

    @Test
    public void testAddAll4() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        SortedList<Object> objectList = new SortedList<Object>(baseList, new NullSafeComparator<Object>());
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertTrue(objectList.addAll(objectList1));
    }

    @Test
    public void testContains() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertFalse((new SortedList<Object>(baseList, new NullSafeComparator<Object>())).contains("o"));
    }

    @Test
    public void testContainsAll() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        SortedList<Object> objectList = new SortedList<Object>(baseList, new NullSafeComparator<Object>());
        assertTrue(objectList.containsAll(new ArrayList<Object>()));
    }

    @Test
    public void testContainsAll2() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        SortedList<Object> objectList = new SortedList<Object>(baseList, new NullSafeComparator<Object>());
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        objectList1.add("e");
        assertFalse(objectList.containsAll(objectList1));
    }

    @Test
    public void testGet() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertEquals("e", (new SortedList<Object>(objectList, new NullSafeComparator<Object>())).get(1));
    }

    @Test
    public void testIndexOf() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals(-1, (new SortedList<Object>(baseList, new NullSafeComparator<Object>())).indexOf("o"));
    }

    @Test
    public void testIsEmpty() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertTrue((new SortedList<Object>(baseList, new NullSafeComparator<Object>())).isEmpty());
    }

    @Test
    public void testIsEmpty2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertFalse((new SortedList<Object>(objectList, new NullSafeComparator<Object>())).isEmpty());
    }

    @Test
    public void testLastIndexOf() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals(-1, (new SortedList<Object>(baseList, new NullSafeComparator<Object>())).lastIndexOf("o"));
    }

    @Test
    public void testRemove() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertEquals("e", (new SortedList<Object>(objectList, new NullSafeComparator<Object>())).remove(1));
    }

    @Test
    public void testRemove2() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertFalse((new SortedList<Object>(baseList, new NullSafeComparator<Object>())).remove("o"));
    }

    @Test
    public void testRemoveAll() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        SortedList<Object> objectList = new SortedList<Object>(baseList, new NullSafeComparator<Object>());
        assertFalse(objectList.removeAll(new ArrayList<Object>()));
    }

    @Test
    public void testRemoveAll2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        SortedList<Object> objectList1 = new SortedList<Object>(objectList, new NullSafeComparator<Object>());
        ArrayList<Object> objectList2 = new ArrayList<Object>();
        objectList2.add("e");
        assertTrue(objectList1.removeAll(objectList2));
    }

    @Test
    public void testRetainAll() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        SortedList<Object> objectList = new SortedList<Object>(baseList, new NullSafeComparator<Object>());
        assertFalse(objectList.retainAll(new ArrayList<Object>()));
    }

    @Test
    public void testRetainAll2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        SortedList<Object> objectList1 = new SortedList<Object>(objectList, new NullSafeComparator<Object>());
        assertTrue(objectList1.retainAll(new ArrayList<Object>()));
    }

    @Test
    public void testSet() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add("e");
        assertEquals("e", (new SortedList<Object>(objectList, new NullSafeComparator<Object>())).set(1, "element"));
    }

    @Test
    public void testSize() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals(0, (new SortedList<Object>(baseList, new NullSafeComparator<Object>())).size());
    }

    @Test
    public void testToArray() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals(0, (new SortedList<Object>(baseList, new NullSafeComparator<Object>())).toArray().length);
    }

    @Test
    public void testToArray2() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals(3, (new SortedList<Object>(baseList, new NullSafeComparator<Object>()))
                .<Object>toArray(new Object[]{"foo", "foo", "foo"}).length);
    }

    @Test
    public void testEquals() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertFalse((new SortedList<Object>(baseList, new NullSafeComparator<Object>())).equals("o"));
    }

    @Test
    public void testHashCode() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals(1, (new SortedList<Object>(baseList, new NullSafeComparator<Object>())).hashCode());
    }

    @Test
    public void testToString() {
        ArrayList<Object> baseList = new ArrayList<Object>();
        assertEquals("[]", (new SortedList<Object>(baseList, new NullSafeComparator<Object>())).toString());
    }
}

