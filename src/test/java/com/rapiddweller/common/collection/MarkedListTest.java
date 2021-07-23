package com.rapiddweller.common.collection;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MarkedListTest {
  @Test
  public void testMark() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertFalse((new MarkedList<>(objectList)).mark(1));
  }

  @Test
  public void testIsMarked() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertFalse((new MarkedList<>(objectList)).isMarked(1));
  }

  @Test
  public void testUnmark() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertFalse((new MarkedList<>(objectList)).unmark(1));
  }

  @Test
  public void testGetMarkedElements() {
    assertTrue((new MarkedList<>(new ArrayList<>())).getMarkedElements().isEmpty());
  }

  @Test
  public void testGetMarkedElements2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    assertTrue((new MarkedList<>(objectList)).getMarkedElements().isEmpty());
  }

  @Test
  public void testGetUnmarkedElements() {
    assertTrue((new MarkedList<>(new ArrayList<>())).getUnmarkedElements().isEmpty());
  }

  @Test
  public void testGetUnmarkedElements2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    assertEquals(1, (new MarkedList<>(objectList)).getUnmarkedElements().size());
  }

  @Test
  public void testAdd() {
    assertTrue((new MarkedList<>(new ArrayList<>())).add("element"));
  }

  @Test
  public void testAddAll() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    MarkedList<Object> objectList1 = new MarkedList<>(objectList);
    assertFalse(objectList1.addAll(1, new ArrayList<>()));
  }

  @Test
  public void testAddAll2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertTrue((new MarkedList<>(objectList)).addAll(1, objectList1));
  }

  @Test
  public void testAddAll3() {
    MarkedList<Object> objectList = new MarkedList<>(new ArrayList<>());
    assertFalse(objectList.addAll(new ArrayList<>()));
  }

  @Test
  public void testAddAll4() {
    MarkedList<Object> objectList = new MarkedList<>(new ArrayList<>());
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertTrue(objectList.addAll(objectList1));
  }

  @Test
  public void testRemove() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    objectList.add("e");
    assertEquals("e", (new MarkedList<>(objectList)).remove(1));
  }

  @Test
  public void testRemove2() {
    assertFalse((new MarkedList<>(new ArrayList<>())).remove("element"));
  }

  @Test
  public void testRemove3() {
    MarkedList<Object> objectList = new MarkedList<>(new ArrayList<>());
    objectList.add("element");
    assertTrue(objectList.remove("element"));
  }

  @Test
  public void testRemoveAll() {
    MarkedList<Object> objectList = new MarkedList<>(new ArrayList<>());
    assertFalse(objectList.removeAll(new ArrayList<>()));
  }

  @Test
  public void testRemoveAll2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    MarkedList<Object> objectList1 = new MarkedList<>(objectList);
    assertFalse(objectList1.removeAll(new ArrayList<>()));
  }

  @Test
  public void testRemoveAll3() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertTrue((new MarkedList<>(objectList)).removeAll(objectList1));
  }

  @Test
  public void testRemoveAll4() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(2);
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertFalse((new MarkedList<>(objectList)).removeAll(objectList1));
  }

  @Test
  public void testRetainAll() {
    MarkedList<Object> objectList = new MarkedList<>(new ArrayList<>());
    assertFalse(objectList.retainAll(new ArrayList<>()));
  }

  @Test
  public void testRetainAll2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    MarkedList<Object> objectList1 = new MarkedList<>(objectList);
    assertFalse(objectList1.retainAll(new ArrayList<>()));
  }

  @Test
  public void testRetainAll3() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertFalse((new MarkedList<>(objectList)).retainAll(objectList1));
  }

  @Test
  public void testRetainAll4() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(2);
    ArrayList<Object> objectList1 = new ArrayList<>();
    objectList1.add("e");
    assertFalse((new MarkedList<>(objectList)).retainAll(objectList1));
  }

  @Test
  public void testSubList() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    assertTrue((new MarkedList<>(objectList)).subList(1, 1).isEmpty());
  }

  @Test
  public void testConstructor() {
    assertTrue((new MarkedList<>(new ArrayList<>())).isEmpty());
  }

  @Test
  public void testConstructor2() {
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("e");
    assertEquals(1, (new MarkedList<>(objectList)).size());
  }
}

