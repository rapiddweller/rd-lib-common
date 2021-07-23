package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ImportFailedExceptionTest {
  @Test
  public void testConstructor() {
    ImportFailedException actualImportFailedException = new ImportFailedException();
    assertEquals("com.rapiddweller.common.ImportFailedException", actualImportFailedException.toString());
    assertNull(actualImportFailedException.getLocalizedMessage());
    assertNull(actualImportFailedException.getCause());
    assertNull(actualImportFailedException.getMessage());
    assertEquals(0, actualImportFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    ImportFailedException actualImportFailedException = new ImportFailedException("An error occurred");
    assertEquals("com.rapiddweller.common.ImportFailedException: An error occurred",
        actualImportFailedException.toString());
    assertEquals("An error occurred", actualImportFailedException.getLocalizedMessage());
    assertNull(actualImportFailedException.getCause());
    assertEquals("An error occurred", actualImportFailedException.getMessage());
    assertEquals(0, actualImportFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor3() {
    Throwable throwable = new Throwable();
    assertSame((new ImportFailedException("An error occurred", throwable)).getCause(), throwable);
  }

  @Test
  public void testConstructor4() {
    Throwable throwable = new Throwable();
    assertSame((new ImportFailedException(throwable)).getCause(), throwable);
  }
}

