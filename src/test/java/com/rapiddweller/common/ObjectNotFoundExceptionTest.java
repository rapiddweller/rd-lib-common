package com.rapiddweller.common;

import com.rapiddweller.common.ObjectNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ObjectNotFoundExceptionTest {

  @Test
  public void testConstructor1() {
    ObjectNotFoundException actualObjectNotFoundException = new ObjectNotFoundException("An error occurred");
    assertEquals("com.rapiddweller.common.ObjectNotFoundException: An error occurred",
        actualObjectNotFoundException.toString());
    assertEquals("An error occurred", actualObjectNotFoundException.getLocalizedMessage());
    assertNull(actualObjectNotFoundException.getCause());
    assertEquals("An error occurred", actualObjectNotFoundException.getMessage());
    assertEquals(0, actualObjectNotFoundException.getSuppressed().length);
  }

  @Test
  public void testConstructor2() {
    Throwable throwable = new Throwable();
    assertSame((new ObjectNotFoundException("An error occurred", throwable)).getCause(), throwable);
  }

}

