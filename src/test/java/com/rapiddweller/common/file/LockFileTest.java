package com.rapiddweller.common.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LockFileTest {
    @Test
    public void testLockAlreadyAcquiredExceptionConstructor() {
        LockFile.LockAlreadyAcquiredException actualLockAlreadyAcquiredException = new LockFile.LockAlreadyAcquiredException(
                "foo.txt");
        assertEquals(
                "com.rapiddweller.common.file.LockFile$LockAlreadyAcquiredException: Lock file already acquired:" + " foo.txt",
                actualLockAlreadyAcquiredException.toString());
        assertEquals("Lock file already acquired: foo.txt", actualLockAlreadyAcquiredException.getLocalizedMessage());
        assertNull(actualLockAlreadyAcquiredException.getCause());
        assertEquals("Lock file already acquired: foo.txt", actualLockAlreadyAcquiredException.getMessage());
        assertEquals(0, actualLockAlreadyAcquiredException.getSuppressed().length);
    }

    @Test
    public void testLockAlreadyAcquiredExceptionConstructor2() {
        LockFile.LockAlreadyAcquiredException actualLockAlreadyAcquiredException = new LockFile.LockAlreadyAcquiredException(
                "foo.txt");
        assertEquals(
                "com.rapiddweller.common.file.LockFile$LockAlreadyAcquiredException: Lock file already acquired:" + " foo.txt",
                actualLockAlreadyAcquiredException.toString());
        assertEquals("Lock file already acquired: foo.txt", actualLockAlreadyAcquiredException.getLocalizedMessage());
        assertNull(actualLockAlreadyAcquiredException.getCause());
        assertEquals("Lock file already acquired: foo.txt", actualLockAlreadyAcquiredException.getMessage());
        assertEquals(0, actualLockAlreadyAcquiredException.getSuppressed().length);
    }
}

