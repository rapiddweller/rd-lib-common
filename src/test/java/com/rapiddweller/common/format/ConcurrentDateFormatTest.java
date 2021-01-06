package com.rapiddweller.common.format;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ConcurrentDateFormatTest {
    @Test
    public void testConstructor() {
        ConcurrentDateFormat actualConcurrentDateFormat = new ConcurrentDateFormat("Pattern");
        assertNull(actualConcurrentDateFormat.getCalendar());
        assertNull(actualConcurrentDateFormat.getNumberFormat());
    }
}

