package com.rapiddweller.common;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

public class LocalDatedUtilTest {
    @Test
    public void testSoonestFutureElement() {
        assertNull(LocalDatedUtil.soonestFutureElement(new ArrayList<>()));
    }
}

