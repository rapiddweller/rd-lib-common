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

import com.rapiddweller.common.Converter;

/**
 * Converts arrays from one component type to arrays of another component type.
 * Created: 07.06.2007 14:35:18
 *
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 */
public class ArrayTypeConverter<T> extends ArrayConverter<Object, T> {

  /**
   * Instantiates a new Array type converter.
   *
   * @param targetArrayComponentType the target array component type
   * @param elementTypes             the element types
   */
  @SafeVarargs
  public ArrayTypeConverter(Class<T> targetArrayComponentType, Class<? extends T>... elementTypes) {
    super(Object.class, targetArrayComponentType, createConverters(elementTypes));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static <T> Converter<Object, T>[] createConverters(Class<? extends T>... elementTypes) {
    Converter<Object, T>[] converters = new Converter[elementTypes.length];
    for (int i = 0; i < elementTypes.length; i++) {
      converters[i] = new AnyConverter(elementTypes[i]);
    }
    return converters;
  }

  /**
   * Convert object [ ].
   *
   * @param args         the args
   * @param elementTypes the element types
   * @return the object [ ]
   */
  public static Object[] convert(Object[] args, Class<?>[] elementTypes) {
    return new ArrayTypeConverter<>(Object.class, elementTypes).convert(args);
  }

  /**
   * Convert t [ ].
   *
   * @param <T>           the type parameter
   * @param args          the args
   * @param componentType the component type
   * @return the t [ ]
   */
  public static <T> T[] convert(Object[] args, Class<T> componentType) {
    return new ArrayTypeConverter<>(componentType).convert(args);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + getTargetType() + "]";
  }

}
