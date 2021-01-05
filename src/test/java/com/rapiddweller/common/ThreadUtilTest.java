package com.rapiddweller.common;

import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.converter.Base64ToByteArrayConverter;
import com.rapiddweller.common.converter.ByteArray2StringConverter;

import java.util.ArrayList;

import org.junit.Test;

public class ThreadUtilTest {
    @Test
    public void testAllThreadSafe() {
        assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allThreadSafe(new ArrayList<ThreadAware>()));
        assertTrue(ThreadUtil.<ThreadAware>allThreadSafe(new ThreadAware[]{new Base64ToByteArrayConverter()}));
    }

    @Test
    public void testAllThreadSafe2() {
        ArrayList<ThreadAware> threadAwareList = new ArrayList<ThreadAware>();
        threadAwareList.add(new Base64ToByteArrayConverter());
        assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allThreadSafe(threadAwareList));
    }

    @Test
    public void testAllThreadSafe3() {
        ArrayList<ThreadAware> threadAwareList = new ArrayList<ThreadAware>();
        threadAwareList.add(new ByteArray2StringConverter());
        assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allThreadSafe(threadAwareList));
    }

    @Test
    public void testAllParallelizable() {
        assertTrue(
                ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allParallelizable(new ArrayList<ThreadAware>()));
        assertTrue(ThreadUtil.<ThreadAware>allParallelizable(new ThreadAware[]{new Base64ToByteArrayConverter()}));
    }

    @Test
    public void testAllParallelizable2() {
        ArrayList<ThreadAware> threadAwareList = new ArrayList<ThreadAware>();
        threadAwareList.add(new Base64ToByteArrayConverter());
        assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allParallelizable(threadAwareList));
    }

    @Test
    public void testAllParallelizable3() {
        ArrayList<ThreadAware> threadAwareList = new ArrayList<ThreadAware>();
        threadAwareList.add(new ByteArray2StringConverter());
        assertTrue(ThreadUtil.<java.util.Collection<ThreadAware>, ThreadAware>allParallelizable(threadAwareList));
    }
}

