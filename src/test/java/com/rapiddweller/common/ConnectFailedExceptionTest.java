package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ConnectFailedExceptionTest {
  @Test
  public void testConstructor() {
    ConnectFailedException actualConnectFailedException = new ConnectFailedException();
    assertEquals("com.rapiddweller.common.ConnectFailedException", actualConnectFailedException.toString());
    assertNull(actualConnectFailedException.getLocalizedMessage());
    assertNull(actualConnectFailedException.getCause());
    assertNull(actualConnectFailedException.getMessage());
    assertEquals(0, actualConnectFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    ConnectFailedException actualConnectFailedException = new ConnectFailedException("An error occurred");
    assertEquals("com.rapiddweller.common.ConnectFailedException: An error occurred",
        actualConnectFailedException.toString());
    assertEquals("An error occurred", actualConnectFailedException.getLocalizedMessage());
    assertNull(actualConnectFailedException.getCause());
    assertEquals("An error occurred", actualConnectFailedException.getMessage());
    assertEquals(0, actualConnectFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor3() {
    Throwable throwable = new Throwable();
    assertSame((new ConnectFailedException("An error occurred", throwable)).getCause(), throwable);
  }

  @Test
  public void testConstructor4() {
    Throwable throwable = new Throwable();
    assertSame((new ConnectFailedException(throwable)).getCause(), throwable);
  }
}

