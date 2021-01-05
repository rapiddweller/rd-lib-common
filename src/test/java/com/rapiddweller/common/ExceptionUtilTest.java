package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
                .setStackTrace(new StackTraceElement[]{new StackTraceElement("Declaring Class", "Method Name", "foo.txt", 2)});
        assertEquals("java.lang.Throwable\n\tat Declaring Class.Method Name(foo.txt:2)\n",
                ExceptionUtil.stackTraceToString(throwable));
    }
}

