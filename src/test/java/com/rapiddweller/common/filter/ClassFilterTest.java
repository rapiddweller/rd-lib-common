package com.rapiddweller.common.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassFilterTest {
    @Test
    public void testAccept() {
        assertTrue((new ClassFilter<Object>(Object.class, true)).accept("candidate"));
        assertFalse((new ClassFilter<Object>(Object.class, false)).accept("candidate"));
        assertFalse((new ClassFilter<Object>(Object.class, true)).accept(null));
    }
}

