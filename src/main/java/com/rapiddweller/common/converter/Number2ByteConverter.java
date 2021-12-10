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

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

/**
 * Converts arbitrary {@link Number}s to {@link Byte}s.
 * Created: 15.11.2012 08:25:45
 * @author Volker Bergmann
 * @since 0.5.20
 */
public class Number2ByteConverter extends ThreadSafeConverter<Number, Byte> {

  protected Number2ByteConverter() {
    super(Number.class, Byte.class);
  }

  @Override
  public Byte convert(Number sourceValue) throws ConversionException {
    return (sourceValue != null ? sourceValue.byteValue() : null);
  }

}
