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

import org.junit.Assume;
import org.junit.Test;

import java.io.File;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;

import static com.rapiddweller.common.SystemInfo.isLinux;
import static com.rapiddweller.common.SystemInfo.isMacOsx;
import static com.rapiddweller.common.SystemInfo.isWindows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests the {@link ShellUtil}.
 * Created: 22.02.2010 17:09:02
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ShellUtilTest {

  @Test
  public void testRunShellCommands() {
    ReaderLineIterator iterator = new ReaderLineIterator(Reader.nullReader());
    ErrorHandler errorHandler = new ErrorHandler(Object.class);
    assertEquals(0, ShellUtil.runShellCommands(iterator, null, Writer.nullWriter(), errorHandler));
  }

  @Test
  public void test_echo_42() {
    StringWriter writer = new StringWriter();
    ShellUtil.runShellCommand("echo 42", null, writer, ErrorHandler.getDefault());
    assertEquals("42", writer.toString());
  }

  @Test
  public void test_echo_JAVA_HOME_linux() {
    Assume.assumeTrue(isLinux());
    checkEchoJavaHomeIx();
  }

  @Test
  public void test_echo_JAVA_HOME_macosx() {
    Assume.assumeTrue(isMacOsx());
    checkEchoJavaHomeIx();
  }

  @Test
  public void test_echo_JAVA_HOME_win() {
    Assume.assumeTrue(isWindows());
    checkEchoJavaHomeWin();
  }

  // helper methods --------------------------------------------------------------------------------------------------

  private void checkEchoJavaHomeIx() {
    String expectedResult = System.getenv("JAVA_HOME");
    assertNotNull(expectedResult);
    StringWriter writer = new StringWriter();
    String command = "echo $JAVA_HOME";
    ShellUtil.runShellCommand(command, null, writer, ErrorHandler.getDefault());
    assertEquals(expectedResult, writer.toString());
  }

  private void checkEchoJavaHomeWin() {
    String expectedResult = System.getenv("JAVA_HOME");
    assertNotNull(expectedResult);
    StringWriter writer = new StringWriter();
    String command = "echo %JAVA_HOME%";
    ShellUtil.runShellCommand(command, null, writer, ErrorHandler.getDefault());
    assertEquals(expectedResult, writer.toString());
  }

}
