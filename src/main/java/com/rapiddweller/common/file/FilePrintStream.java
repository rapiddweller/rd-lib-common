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

package com.rapiddweller.common.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * {@link PrintStream} implementation that writes to a file
 * and exhibits the file name to client code.
 * Created: 15.11.2010 07:38:28
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class FilePrintStream extends PrintStream {

  /**
   * The File.
   */
  protected File file;

  /**
   * Instantiates a new File print stream.
   *
   * @param file the file
   * @throws FileNotFoundException the file not found exception
   */
  public FilePrintStream(File file) throws FileNotFoundException {
    super(file);
    this.file = file;
  }

  /**
   * Gets file.
   *
   * @return the file
   */
  public File getFile() {
    return file;
  }

}
