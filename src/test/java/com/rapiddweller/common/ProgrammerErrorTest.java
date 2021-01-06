package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ProgrammerErrorTest {
    @Test
    public void testConstructor() {
        ProgrammerError actualProgrammerError = new ProgrammerError();
        assertEquals("com.rapiddweller.common.ProgrammerError", actualProgrammerError.toString());
        assertNull(actualProgrammerError.getLocalizedMessage());
        assertNull(actualProgrammerError.getCause());
        assertNull(actualProgrammerError.getMessage());
        assertEquals(0, actualProgrammerError.getSuppressed().length);
    }

    @Test
    public void testConstructor2() {
        ProgrammerError actualProgrammerError = new ProgrammerError("Not all who wander are lost");
        assertEquals("com.rapiddweller.common.ProgrammerError: Not all who wander are lost",
                actualProgrammerError.toString());
        assertEquals("Not all who wander are lost", actualProgrammerError.getLocalizedMessage());
        assertNull(actualProgrammerError.getCause());
        assertEquals("Not all who wander are lost", actualProgrammerError.getMessage());
        assertEquals(0, actualProgrammerError.getSuppressed().length);
    }

    @Test
    public void testConstructor3() {
        Throwable throwable = new Throwable();
        assertSame((new ProgrammerError("Not all who wander are lost", throwable)).getCause(), throwable);
    }

    @Test
    public void testConstructor4() {
        Throwable throwable = new Throwable();
        assertSame((new ProgrammerError(throwable)).getCause(), throwable);
    }
}

