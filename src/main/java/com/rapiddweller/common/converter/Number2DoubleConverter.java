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
 * Converts arbitrary {@link Number}s to {@link Double}s.
 * Created: 15.11.2012 08:26:16
 * @author Volker Bergmann
 * @since 0.5.20
 */
public class Number2DoubleConverter extends ThreadSafeConverter<Number, Double> {

  protected Number2DoubleConverter() {
    super(Number.class, Double.class);
  }

  @Override
  public Double convert(Number sourceValue) throws ConversionException {
    return (sourceValue != null ? sourceValue.doubleValue() : null);
  }

}
