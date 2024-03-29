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

import com.rapiddweller.common.SystemInfo;

/**
 * {@link TextPrinter} implementation that prints into a buffer
 * and provides the received input as String in {@link #toString()}.
 * Created: 17.03.2013 18:06:28
 * @author Volker Bergmann
 * @since 0.5.23
 */
public class BufferedTextPrinter implements TextPrinter {

  private final StringBuilder buffer;

  public BufferedTextPrinter() {
    this.buffer = new StringBuilder();
  }

  @Override
  public void printStd(String... lines) {
    appendLines(lines);
  }

  @Override
  public void printErr(String... lines) {
    appendLines(lines);
  }

  public void clear() {
    buffer.delete(0, buffer.length());
  }

  private void appendLines(String[] lines) {
    for (String line : lines) {
      buffer.append(line).append(SystemInfo.getLineSeparator());
    }
  }

  @Override
  public String toString() {
    return buffer.toString();
  }

}
