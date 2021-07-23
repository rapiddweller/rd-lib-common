package com.rapiddweller.common.collection;

import com.rapiddweller.common.NullSafeComparator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SortedListTest {
  @Test
  public void testAdd() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertTrue((new SortedList<>(baseList, new NullSafeComparator<>())).add("e"));
  }

  @Test
  public void testAddAll() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    SortedList<Object> objectList1 = new SortedList<>(objectList, new NullSafeComparator<>());
    assertFalse(objectList1.addAll(1, new ArrayList<>()));
  }

  @Test
  public void testAddAll2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    SortedList<Object> objectList1 = new SortedList<>(objectList, new NullSafeComparator<>());
    ArrayList<Object> objectList2 = new ArrayList<>();
    objectList2.add("e");
    assertTrue(objectList1.addAll(1, objectList2));
  }

  @Test
  public void testAddAll3() {
    ArrayList<Object> baseList = new ArrayList<>();
    SortedList<Object> objectList = new SortedList<>(baseList, new NullSafeComparator<>());
    assertFalse(objectList.addAll(new ArrayList<>()));
  }

  @Test
  public void testAddAll4() {
    ArrayList<Object> baseList = new ArrayList<>();
    SortedList<Object> objectList = new SortedList<>(baseList, new NullSafeComparator<>());
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertTrue(objectList.addAll(objectList1));
  }

  @Test
  public void testContains() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertFalse((new SortedList<>(baseList, new NullSafeComparator<>())).contains("o"));
  }

  @Test
  public void testContainsAll() {
    ArrayList<Object> baseList = new ArrayList<>();
    SortedList<Object> objectList = new SortedList<>(baseList, new NullSafeComparator<>());
    assertTrue(objectList.containsAll(new ArrayList<>()));
  }

  @Test
  public void testContainsAll2() {
    ArrayList<Object> baseList = new ArrayList<>();
    SortedList<Object> objectList = new SortedList<>(baseList, new NullSafeComparator<>());
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertFalse(objectList.containsAll(objectList1));
  }

  @Test
  public void testGet() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertEquals("e", (new SortedList<>(objectList, new NullSafeComparator<>())).get(1));
  }

  @Test
  public void testIndexOf() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals(-1, (new SortedList<>(baseList, new NullSafeComparator<>())).indexOf("o"));
  }

  @Test
  public void testIsEmpty() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertTrue((new SortedList<>(baseList, new NullSafeComparator<>())).isEmpty());
  }

  @Test
  public void testIsEmpty2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    assertFalse((new SortedList<>(objectList, new NullSafeComparator<>())).isEmpty());
  }

  @Test
  public void testLastIndexOf() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals(-1, (new SortedList<>(baseList, new NullSafeComparator<>())).lastIndexOf("o"));
  }

  @Test
  public void testRemove() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertEquals("e", (new SortedList<>(objectList, new NullSafeComparator<>())).remove(1));
  }

  @Test
  public void testRemove2() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertFalse((new SortedList<>(baseList, new NullSafeComparator<>())).remove("o"));
  }

  @Test
  public void testRemoveAll() {
    ArrayList<Object> baseList = new ArrayList<>();
    SortedList<Object> objectList = new SortedList<>(baseList, new NullSafeComparator<>());
    assertFalse(objectList.removeAll(new ArrayList<>()));
  }

  @Test
  public void testRemoveAll2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    SortedList<Object> objectList1 = new SortedList<>(objectList, new NullSafeComparator<>());
    ArrayList<Object> objectList2 = new ArrayList<>();
    objectList2.add("e");
    assertTrue(objectList1.removeAll(objectList2));
  }

  @Test
  public void testRetainAll() {
    ArrayList<Object> baseList = new ArrayList<>();
    SortedList<Object> objectList = new SortedList<>(baseList, new NullSafeComparator<>());
    assertFalse(objectList.retainAll(new ArrayList<>()));
  }

  @Test
  public void testRetainAll2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    SortedList<Object> objectList1 = new SortedList<>(objectList, new NullSafeComparator<>());
    assertTrue(objectList1.retainAll(new ArrayList<>()));
  }

  @Test
  public void testSet() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertEquals("e", (new SortedList<>(objectList, new NullSafeComparator<>())).set(1, "element"));
  }

  @Test
  public void testSize() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals(0, (new SortedList<>(baseList, new NullSafeComparator<>())).size());
  }

  @Test
  public void testToArray() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals(0, (new SortedList<>(baseList, new NullSafeComparator<>())).toArray().length);
  }

  @Test
  public void testToArray2() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals(3, (new SortedList<>(baseList, new NullSafeComparator<>()))
        .toArray(new Object[] {"foo", "foo", "foo"}).length);
  }

  @Test
  public void testEquals() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertFalse((new SortedList<>(baseList, new NullSafeComparator<>())).equals("o"));
  }

  @Test
  public void testHashCode() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals(1, (new SortedList<>(baseList, new NullSafeComparator<>())).hashCode());
  }

  @Test
  public void testToString() {
    ArrayList<Object> baseList = new ArrayList<>();
    assertEquals("[]", (new SortedList<>(baseList, new NullSafeComparator<>())).toString());
  }
}

