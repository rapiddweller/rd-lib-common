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

package com.rapiddweller.common.io;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * Special {@link PushbackReader} implementation which provides the current cursor offset.
 * Created: 19.03.2013 21:13:02
 *
 * @author Volker Bergmann
 * @since 0.5.23
 */
public class OffsetPushbackReader extends PushbackReader {

  /**
   * The Offset.
   */
  int offset;

  /**
   * Instantiates a new Offset pushback reader.
   *
   * @param in the in
   */
  public OffsetPushbackReader(Reader in) {
    this(in, 1);
  }

  /**
   * Instantiates a new Offset pushback reader.
   *
   * @param in                 the in
   * @param pushBackBufferSize the push back buffer size
   */
  public OffsetPushbackReader(Reader in, int pushBackBufferSize) {
    super(in, pushBackBufferSize);
    this.offset = 0;
  }

  @Override
  public int read() throws IOException {
    offset++;
    return super.read();
  }

  @Override
  public int read(char[] charBuffer) throws IOException {
    int count = super.read(charBuffer);
    offset += count;
    return count;
  }

  @Override
  public int read(CharBuffer charVuffer) throws IOException {
    int count = super.read(charVuffer);
    offset += count;
    return count;
  }

  @Override
  public void unread(int c) throws IOException {
    offset--;
    super.unread(c);
  }

  @Override
  public void unread(char[] charBuffer) throws IOException {
    offset -= charBuffer.length;
    super.unread(charBuffer);
  }

  @Override
  public void unread(char[] charBuffer, int off, int len) throws IOException {
    offset -= len;
    super.unread(charBuffer, off, len);
  }

  /**
   * Gets offset.
   *
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

}
