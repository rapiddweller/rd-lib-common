package com.rapiddweller.common.filter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AcceptAllFilterTest {
    @Test
    public void testAccept() {
        assertTrue((new AcceptAllFilter<Object>()).accept("candidate"));
    }
}

