package com.rapiddweller.common.accessor;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class FallbackAccessorTest {
    @Test
    public void testGetValue() {
        assertNull((new FallbackAccessor<>()).getValue("target"));
    }
}

