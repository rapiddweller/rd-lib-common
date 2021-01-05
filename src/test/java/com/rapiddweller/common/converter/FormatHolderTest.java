package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.CompositeFormatter;
import org.junit.Test;

public class FormatHolderTest {
    @Test
    public void testSetNullString() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setNullString("Null Result");
        assertEquals("Null Result", compositeFormatter.getNullString());
    }

    @Test
    public void testSetDatePattern() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setDatePattern("Pattern");
        assertEquals("Pattern", compositeFormatter.getDatePattern());
    }

    @Test
    public void testSetDateCapitalization() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setDateCapitalization(Capitalization.upper);
        assertEquals(Capitalization.upper, compositeFormatter.getDateCapitalization());
    }

    @Test
    public void testSetDateTimePattern() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setDateTimePattern("Pattern");
        assertEquals("Pattern", compositeFormatter.getDateTimePattern());
    }

    @Test
    public void testSetDateTimeCapitalization() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setDateTimeCapitalization(Capitalization.upper);
        assertEquals(Capitalization.upper, compositeFormatter.getDateTimeCapitalization());
    }

    @Test
    public void testSetTimePattern() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setTimePattern("Time Pattern");
        assertEquals("Time Pattern", compositeFormatter.getTimePattern());
    }

    @Test
    public void testSetTimestampPattern() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setTimestampPattern("Pattern");
        assertEquals("Pattern", compositeFormatter.getTimestampPattern());
    }

    @Test
    public void testSetTimestampCapitalization() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setTimestampCapitalization(Capitalization.upper);
        assertEquals(Capitalization.upper, compositeFormatter.getTimestampCapitalization());
    }

    @Test
    public void testSetCharQuote() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setCharQuote("Char Quote");
        assertEquals("Char Quote", compositeFormatter.getCharQuote());
    }

    @Test
    public void testSetStringQuote() {
        CompositeFormatter compositeFormatter = new CompositeFormatter();
        compositeFormatter.setStringQuote("String Quote");
        assertEquals("String Quote", compositeFormatter.getStringQuote());
    }
}

