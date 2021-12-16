package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ExceptionUtilTest {
  @Test
  public void testGetRootCause() {
    Throwable throwable = new Throwable();
    assertSame(throwable, ExceptionUtil.getRootCause(throwable));
  }

  @Test
  public void testGetRootCause2() {
    Throwable throwable = new Throwable("Not all who wander are lost");
    assertSame(throwable, ExceptionUtil.getRootCause(throwable));
  }

  @Test
  public void testGetRootCause3() {
    Throwable throwable = new Throwable();
    Throwable throwable1 = new Throwable();
    throwable.initCause(throwable1);
    assertSame(throwable1, ExceptionUtil.getRootCause(throwable));
  }

  @Test
  public void testStackTraceToString2() {
    Throwable throwable = new Throwable();
    throwable
        .setStackTrace(new StackTraceElement[] {new StackTraceElement("Declaring Class", "Method Name", "foo.txt", 2)});
    assertEquals("java.lang.Throwable\n\tat Declaring Class.Method Name(foo.txt:2)\n",
        ExceptionUtil.stackTraceToString(throwable));
  }

  @Test
  public void testFormatMessageWithLocation() {
    assertEquals("Message. ", ExceptionUtil.formatMessageWithLocation("Message", null));
    assertEquals("Message. File test.xml", ExceptionUtil.formatMessageWithLocation("Message",
        new TextFileLocation("test.xml", -1, -1, -1, -1)));
    assertEquals("Message. File test.xml, line 5", ExceptionUtil.formatMessageWithLocation("Message",
        new TextFileLocation("test.xml", 5, 5, 5, 5)));
    assertEquals("Message. Line 5", ExceptionUtil.formatMessageWithLocation("Message",
        new TextFileLocation(null, 5, 5, 5, 5)));
  }

  @Test
  public void testEndWithDotSpace() {
    assertEquals("Something went wrong. ", ExceptionUtil.endWithDotSpace("Something went wrong"));
    assertEquals("Something went wrong. ", ExceptionUtil.endWithDotSpace("Something went wrong."));
    assertEquals("Something went wrong. ", ExceptionUtil.endWithDotSpace("Something went wrong. "));
  }

}

