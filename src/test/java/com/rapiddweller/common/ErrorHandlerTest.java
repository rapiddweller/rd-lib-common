package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ErrorHandlerTest {
    @Test
    public void testConstructor() {
        ErrorHandler actualErrorHandler = new ErrorHandler(Object.class);
        assertEquals(Level.fatal, actualErrorHandler.getLevel());
        assertTrue(actualErrorHandler.isLoggingStackTrace());
    }

    @Test
    public void testConstructor2() {
        ErrorHandler actualErrorHandler = new ErrorHandler("Category");
        assertEquals(Level.fatal, actualErrorHandler.getLevel());
        assertTrue(actualErrorHandler.isLoggingStackTrace());
    }

    @Test
    public void testConstructor3() {
        ErrorHandler actualErrorHandler = new ErrorHandler("Category", Level.ignore);
        assertEquals(Level.ignore, actualErrorHandler.getLevel());
        assertTrue(actualErrorHandler.isLoggingStackTrace());
    }

    @Test
    public void testSetLoggingStackTrace() {
        ErrorHandler errorHandler = new ErrorHandler(Object.class);
        errorHandler.setLoggingStackTrace(true);
        assertTrue(errorHandler.isLoggingStackTrace());
    }
}

