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

package com.rapiddweller.common.ui;

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.ReaderLineIterator;

/**
 * {@link TextPrinter} implementation that prints info to the console.
 * Created at 21.12.2008 11:34:25
 * @author Volker Bergmann
 * @since 0.4.7
 */
public class ConsolePrinter implements TextPrinter {

  // TextPrinter interface implementation ----------------------------------------------------------------------------

  @Override
  public void printStd(String... lines) {
    printStandard(lines);
  }

  @Override
  public void printErr(String... lines) {
    printError(lines);
  }

  // public utility methods ------------------------------------------------------------------------------------------

  public static void printStandard(String... lines) {
    for (String line : lines) {
      println(line);
    }
  }

  public static void printError(String... lines) {
    for (String line : lines) {
      System.err.println(line);
    }
  }

  public static void printFile(String uri) {
    try (ReaderLineIterator iterator = new ReaderLineIterator(IOUtil.getReaderForURI(uri))) {
      while (iterator.hasNext()) {
        println(iterator.next());
      }
    }
  }

  // private implementation ------------------------------------------------------------------------------------------

  private static void println(String line) {
    System.out.println(line);
  }

}
