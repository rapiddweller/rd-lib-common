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
import com.rapiddweller.common.Converter;

/**
 * Converting {@link Boolean} values to {@link Number}s: <code>false</code> to <code>0</code>,
 * <code>true</code> to <code>1</code>.
 * Created: 27.02.2010 09:57:17
 *
 * @param <T> the target type of the numbers to convert
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class Boolean2NumberConverter<T extends Number> extends ConverterWrapper<Integer, T>
    implements Converter<Boolean, T> {

  /**
   * Instantiates a new Boolean 2 number converter.
   *
   * @param targetType the target type
   */
  public Boolean2NumberConverter(Class<T> targetType) {
    super(new NumberToNumberConverter<>(Integer.class, targetType));
  }

  @Override
  public T convert(Boolean sourceValue) throws ConversionException {
    return realConverter.convert(sourceValue ? 1 : 0);
  }

  @Override
  public Class<Boolean> getSourceType() {
    return Boolean.class;
  }

  @Override
  public Class<T> getTargetType() {
    return realConverter.getTargetType();
  }

}
