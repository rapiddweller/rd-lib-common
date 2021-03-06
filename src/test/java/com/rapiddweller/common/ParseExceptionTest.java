package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParseExceptionTest {
    @Test
    public void testToString() {
        assertEquals("An error occurred in Parsed Text",
                (new ParseException("An error occurred", "Parsed Text")).toString());
        assertEquals("An error occurred at line 2, column 1 in Parsed Text",
                (new ParseException("An error occurred", "Parsed Text", 2, 1)).toString());
        assertEquals("An error occurred", (new ParseException("An error occurred", null)).toString());
        assertEquals("An error occurred in Parsed Text",
                (new ParseException("An error occurred", "Parsed Text", 2, -1)).toString());
    }
}

