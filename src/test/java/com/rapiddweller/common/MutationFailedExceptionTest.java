package com.rapiddweller.common;

import com.rapiddweller.common.exception.MutationFailed;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class MutationFailedExceptionTest {

  @Test
  public void testConstructor1() {
    MutationFailed actualUpdateFailedException = new MutationFailed("An error occurred");
    assertEquals("An error occurred", actualUpdateFailedException.getLocalizedMessage());
    assertNull(actualUpdateFailedException.getCause());
    assertEquals("An error occurred", actualUpdateFailedException.getMessage());
    assertEquals(0, actualUpdateFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    Throwable throwable = new Throwable();
    assertSame((new MutationFailed("An error occurred", throwable)).getCause(), throwable);
  }

}

