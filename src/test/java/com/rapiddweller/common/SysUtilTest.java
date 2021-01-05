package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;

import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.appender.rolling.action.FileRenameAction;
import org.apache.logging.log4j.core.util.DefaultShutdownCallbackRegistry;
import org.junit.Test;

public class SysUtilTest {
    @Test
    public void testRunWithSystemProperty() {
        DefaultShutdownCallbackRegistry defaultShutdownCallbackRegistry = new DefaultShutdownCallbackRegistry();
        SysUtil.runWithSystemProperty("Name", "value", defaultShutdownCallbackRegistry);
        assertEquals(LifeCycle.State.INITIALIZED, defaultShutdownCallbackRegistry.getState());
    }

    @Test
    public void testRunWithSystemProperty2() {
        File src = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        FileRenameAction fileRenameAction = new FileRenameAction(src,
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(), true);
        SysUtil.runWithSystemProperty("Name", "value", fileRenameAction);
        assertTrue(fileRenameAction.isInterrupted());
        assertTrue(fileRenameAction.isComplete());
    }
}

