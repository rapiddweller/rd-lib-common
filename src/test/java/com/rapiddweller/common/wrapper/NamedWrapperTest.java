package com.rapiddweller.common.wrapper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NamedWrapperTest {
    @Test
    public void testSetWrapped() {
        NamedWrapper<Object> namedWrapper = new NamedWrapper<>("Name", "wrapped");
        namedWrapper.setWrapped("wrapped");
        assertTrue(namedWrapper.getWrapped() instanceof String);
    }
}

