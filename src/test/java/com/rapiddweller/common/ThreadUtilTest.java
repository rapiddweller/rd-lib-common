package com.rapiddweller.common;

import com.rapiddweller.common.converter.Base64ToByteArrayConverter;
import com.rapiddweller.common.converter.ByteArray2StringConverter;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class ThreadUtilTest {
  @Test
  public void testAllThreadSafe() {
    assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allThreadSafe(new ArrayList<>()));
    assertTrue(ThreadUtil.allThreadSafe(new ThreadAware[] {new Base64ToByteArrayConverter()}));
  }

  @Test
  public void testAllThreadSafe2() {
    ArrayList<ThreadAware> threadAwareList = new ArrayList<>();
    threadAwareList.add(new Base64ToByteArrayConverter());
    assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allThreadSafe(threadAwareList));
  }

  @Test
  public void testAllThreadSafe3() {
    ArrayList<ThreadAware> threadAwareList = new ArrayList<>();
    threadAwareList.add(new ByteArray2StringConverter());
    assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allThreadSafe(threadAwareList));
  }

  @Test
  public void testAllParallelizable() {
    assertTrue(
        ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allParallelizable(new ArrayList<>()));
    assertTrue(ThreadUtil.allParallelizable(new ThreadAware[] {new Base64ToByteArrayConverter()}));
  }

  @Test
  public void testAllParallelizable2() {
    ArrayList<ThreadAware> threadAwareList = new ArrayList<>();
    threadAwareList.add(new Base64ToByteArrayConverter());
    assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allParallelizable(threadAwareList));
  }

  @Test
  public void testAllParallelizable3() {
    ArrayList<ThreadAware> threadAwareList = new ArrayList<>();
    threadAwareList.add(new ByteArray2StringConverter());
    assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allParallelizable(threadAwareList));
  }
}

