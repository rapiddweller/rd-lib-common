package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ObjectNotFoundExceptionTest {
    @Test
    public void testConstructor() {
        ObjectNotFoundException actualObjectNotFoundException = new ObjectNotFoundException();
        assertEquals("com.rapiddweller.common.ObjectNotFoundException", actualObjectNotFoundException.toString());
        assertNull(actualObjectNotFoundException.getLocalizedMessage());
        assertNull(actualObjectNotFoundException.getCause());
        assertNull(actualObjectNotFoundException.getMessage());
        assertEquals(0, actualObjectNotFoundException.getSuppressed().length);
    }

    @Test
    public void testConstructor2() {
        ObjectNotFoundException actualObjectNotFoundException = new ObjectNotFoundException("An error occurred");
        assertEquals("com.rapiddweller.common.ObjectNotFoundException: An error occurred",
                actualObjectNotFoundException.toString());
        assertEquals("An error occurred", actualObjectNotFoundException.getLocalizedMessage());
        assertNull(actualObjectNotFoundException.getCause());
        assertEquals("An error occurred", actualObjectNotFoundException.getMessage());
        assertEquals(0, actualObjectNotFoundException.getSuppressed().length);
    }

    @Test
    public void testConstructor3() {
        Throwable throwable = new Throwable();
        assertSame((new ObjectNotFoundException("An error occurred", throwable)).getCause(), throwable);
    }

    @Test
    public void testConstructor4() {
        Throwable throwable = new Throwable();
        assertSame((new ObjectNotFoundException(throwable)).getCause(), throwable);
    }
}

