/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * Tests the {@link ShellUtil}.
 * Created: 22.02.2010 17:09:02
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ShellUtilTest {

    @Test
    public void testRunShellCommands() {
        ReaderLineIterator iterator = new ReaderLineIterator(Reader.nullReader());
        ErrorHandler errorHandler = new ErrorHandler(Object.class);
        assertEquals(0, ShellUtil.runShellCommands(iterator, Writer.nullWriter(), errorHandler));
    }

    @Test
    public void testRunShellCommands2() {
        ReaderLineIterator readerLineIterator = new ReaderLineIterator(new StringReader("S"));
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.ignore);
        assertEquals(1, ShellUtil.runShellCommands(readerLineIterator, Writer.nullWriter(), errorHandler));
        assertEquals(2, readerLineIterator.lineCount());
        assertFalse(readerLineIterator.hasNext());
    }

    @Test
    public void testRunShellCommands3() {
        ReaderLineIterator readerLineIterator = new ReaderLineIterator(new StringReader("S"));
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.warn);
        assertEquals(1, ShellUtil.runShellCommands(readerLineIterator, Writer.nullWriter(), errorHandler));
        assertEquals(2, readerLineIterator.lineCount());
        assertFalse(readerLineIterator.hasNext());
    }

    @Test
    public void testRunShellCommands4() {
        ReaderLineIterator readerLineIterator = new ReaderLineIterator(new StringReader("S"));
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.error);
        assertEquals(1, ShellUtil.runShellCommands(readerLineIterator, Writer.nullWriter(), errorHandler));
        assertEquals(2, readerLineIterator.lineCount());
        assertFalse(readerLineIterator.hasNext());
    }

    @Test
    public void test() {
        StringWriter writer = new StringWriter();
        String command = "echo 42";
        if (SystemInfo.isWindows())
            command = "cmd.exe /C " + command;
        ShellUtil.runShellCommand(command, writer, ErrorHandler.getDefault());
        assertEquals("42", writer.toString());
    }

    @Test
    public void testRunShellCommand() {
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.ignore);
        assertEquals(1, ShellUtil.runShellCommand("Command", Writer.nullWriter(), errorHandler));
    }

    @Test
    public void testRunShellCommand2() {
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.warn);
        assertEquals(1, ShellUtil.runShellCommand("Command", Writer.nullWriter(), errorHandler));
    }

    @Test
    public void testRunShellCommand3() {
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.error);
        assertEquals(1, ShellUtil.runShellCommand("Command", Writer.nullWriter(), errorHandler));
    }

    @Test
    public void testRunShellCommand4() {
        File directory = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.ignore);
        assertEquals(1, ShellUtil.runShellCommand("Command", Writer.nullWriter(), directory, errorHandler));
    }

    @Test
    public void testRunShellCommand5() {
        File directory = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.warn);
        assertEquals(1, ShellUtil.runShellCommand("Command", Writer.nullWriter(), directory, errorHandler));
    }

    @Test
    public void testRunShellCommand6() {
        File directory = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.error);
        assertEquals(1, ShellUtil.runShellCommand("Command", Writer.nullWriter(), directory, errorHandler));
    }

    @Test
    public void testRunShellCommand7() {
        File directory = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.ignore);
        assertEquals(-1,
                ShellUtil.runShellCommand(new String[]{"Cmd Array"}, Writer.nullWriter(), directory, errorHandler));
    }

    @Test
    public void testRunShellCommand8() {
        File directory = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.warn);
        assertEquals(-1,
                ShellUtil.runShellCommand(new String[]{"Cmd Array"}, Writer.nullWriter(), directory, errorHandler));
    }

    @Test
    public void testRunShellCommand9() {
        File directory = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile();
        ErrorHandler errorHandler = new ErrorHandler("Category", Level.error);
        assertEquals(-1,
                ShellUtil.runShellCommand(new String[]{"Cmd Array"}, Writer.nullWriter(), directory, errorHandler));
    }

}
