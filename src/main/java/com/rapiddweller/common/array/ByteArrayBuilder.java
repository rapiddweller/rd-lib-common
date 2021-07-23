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

package com.rapiddweller.common.array;

import com.rapiddweller.common.converter.ToStringConverter;

/**
 * Helper class for constructing byte arrays.
 * Created: 27.12.2010 07:45:22
 *
 * @author Volker Bergmann
 * @since 0.5.5
 */
public class ByteArrayBuilder extends AbstractByteArray {

  // constructors ------------------------------------------------------------

  /**
   * Instantiates a new Byte array builder.
   */
  public ByteArrayBuilder() {
    super();
  }

  /**
   * Instantiates a new Byte array builder.
   *
   * @param initialCapacity the initial capacity
   */
  public ByteArrayBuilder(int initialCapacity) {
    super(initialCapacity);
  }

  // interface ---------------------------------------------------------------

  @Override
  public ByteArrayBuilder add(byte item) {
    if (this.buffer == null) {
      throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
    }
    super.add(item);
    return this;
  }

  /**
   * To array byte [ ].
   *
   * @return the byte [ ]
   */
  public byte[] toArray() {
    if (this.buffer == null) {
      throw new UnsupportedOperationException("ArrayBuilder cannot be reused after invoking toArray()");
    }
    byte[] result = new byte[this.itemCount];
    System.arraycopy(buffer, 0, result, 0, this.itemCount);
    this.itemCount = 0;
    buffer = null;
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < itemCount; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(ToStringConverter.convert(buffer[i], "[NULL]"));
    }
    return builder.toString();
  }

}
