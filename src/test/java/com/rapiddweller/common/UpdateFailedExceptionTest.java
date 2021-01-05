package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class UpdateFailedExceptionTest {
    @Test
    public void testConstructor() {
        UpdateFailedException actualUpdateFailedException = new UpdateFailedException();
        assertEquals("com.rapiddweller.common.UpdateFailedException", actualUpdateFailedException.toString());
        assertNull(actualUpdateFailedException.getLocalizedMessage());
        assertNull(actualUpdateFailedException.getCause());
        assertNull(actualUpdateFailedException.getMessage());
        assertEquals(0, actualUpdateFailedException.getSuppressed().length);
    }

    @Test
    public void testConstructor2() {
        UpdateFailedException actualUpdateFailedException = new UpdateFailedException("An error occurred");
        assertEquals("com.rapiddweller.common.UpdateFailedException: An error occurred",
                actualUpdateFailedException.toString());
        assertEquals("An error occurred", actualUpdateFailedException.getLocalizedMessage());
        assertNull(actualUpdateFailedException.getCause());
        assertEquals("An error occurred", actualUpdateFailedException.getMessage());
        assertEquals(0, actualUpdateFailedException.getSuppressed().length);
    }

    @Test
    public void testConstructor3() {
        Throwable throwable = new Throwable();
        assertSame((new UpdateFailedException("An error occurred", throwable)).getCause(), throwable);
    }

    @Test
    public void testConstructor4() {
        Throwable throwable = new Throwable();
        assertSame((new UpdateFailedException(throwable)).getCause(), throwable);
    }
}

