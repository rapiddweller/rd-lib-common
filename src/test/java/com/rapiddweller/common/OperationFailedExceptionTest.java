package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class OperationFailedExceptionTest {
  @Test
  public void testConstructor() {
    OperationFailedException actualOperationFailedException = new OperationFailedException();
    assertEquals("com.rapiddweller.common.OperationFailedException", actualOperationFailedException.toString());
    assertNull(actualOperationFailedException.getLocalizedMessage());
    assertNull(actualOperationFailedException.getCause());
    assertNull(actualOperationFailedException.getMessage());
    assertEquals(0, actualOperationFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    OperationFailedException actualOperationFailedException = new OperationFailedException("An error occurred");
    assertEquals("com.rapiddweller.common.OperationFailedException: An error occurred",
        actualOperationFailedException.toString());
    assertEquals("An error occurred", actualOperationFailedException.getLocalizedMessage());
    assertNull(actualOperationFailedException.getCause());
    assertEquals("An error occurred", actualOperationFailedException.getMessage());
    assertEquals(0, actualOperationFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor3() {
    Throwable throwable = new Throwable();
    assertSame((new OperationFailedException("An error occurred", throwable)).getCause(), throwable);
  }

  @Test
  public void testConstructor4() {
    Throwable throwable = new Throwable();
    assertSame((new OperationFailedException(throwable)).getCause(), throwable);
  }
}

