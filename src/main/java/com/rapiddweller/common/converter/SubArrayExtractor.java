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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.ConversionException;

import java.lang.reflect.Array;

/**
 * Extracts a sub array from another array.
 * Created: 30.07.2007 21:05:07
 *
 * @author Volker Bergmann
 */
public class SubArrayExtractor extends ThreadSafeConverter<Object[], Object[]> {

  private final int[] indexes;

  /**
   * Instantiates a new Sub array extractor.
   *
   * @param indexes the indexes
   */
  public SubArrayExtractor(int... indexes) {
    super(Object[].class, Object[].class);
    this.indexes = indexes;
  }

  @Override
  public Object[] convert(Object[] sourceValue) throws ConversionException {
    Class<Object> componentType = ArrayUtil.componentType(sourceValue);
    Object[] array = (Object[]) Array.newInstance(componentType, indexes.length);
    for (int i = 0; i < indexes.length; i++) {
      array[i] = sourceValue[indexes[i]];
    }
    return array;
  }

}
