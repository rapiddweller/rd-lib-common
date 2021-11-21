package com.rapiddweller.common;

import com.rapiddweller.common.ImportFailedException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ImportFailedExceptionTest {

  @Test
  public void testConstructor1() {
    ImportFailedException actualImportFailedException = new ImportFailedException("An error occurred");
    assertEquals("com.rapiddweller.common.ImportFailedException: An error occurred",
        actualImportFailedException.toString());
    assertEquals("An error occurred", actualImportFailedException.getLocalizedMessage());
    assertNull(actualImportFailedException.getCause());
    assertEquals("An error occurred", actualImportFailedException.getMessage());
    assertEquals(0, actualImportFailedException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    Throwable throwable = new Throwable();
    assertSame((new ImportFailedException("An error occurred", throwable)).getCause(), throwable);
  }

}

