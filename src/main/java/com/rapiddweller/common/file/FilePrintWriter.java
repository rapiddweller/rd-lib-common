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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * {@link PrintWriter} implementation which writes to a {@link File}
 * and provides the file's identity.
 * Created: 14.06.2011 09:10:05
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class FilePrintWriter extends PrintWriter {

  /**
   * The File.
   */
  protected File file;
  /**
   * The Encoding.
   */
  protected String encoding;

  /**
   * Instantiates a new File print writer.
   *
   * @param file the file
   * @throws FileNotFoundException the file not found exception
   */
  public FilePrintWriter(File file) throws FileNotFoundException {
    super(file);
    this.file = file;
  }

  /**
   * Instantiates a new File print writer.
   *
   * @param file     the file
   * @param encoding the encoding
   * @throws FileNotFoundException        the file not found exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public FilePrintWriter(File file, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
    super(file, encoding);
    this.file = file;
    this.encoding = encoding;
  }

  /**
   * Gets file.
   *
   * @return the file
   */
  public File getFile() {
    return file;
  }

  /**
   * Gets encoding.
   *
   * @return the encoding
   */
  public String getEncoding() {
    return encoding;
  }

}
