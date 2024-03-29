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

package com.rapiddweller.common;

/**
 * Base interface for all classes that convert
 * a source object of type S to an object of type T.
 * Created: 30.08.2006 19:41:08
 *
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.1
 */
public interface Converter<S, T> extends ThreadAware {

  /**
   * Gets source type.
   *
   * @return the source type
   */
  Class<S> getSourceType();

  /**
   * Gets target type.
   *
   * @return the target type
   */
  Class<T> getTargetType();

  /**
   * Concerts an object of type S to an object of type T
   *
   * @param sourceValue the object to convert
   * @return the converted object
   * @throws ConversionException the conversion exception
   */
  T convert(S sourceValue) throws ConversionException;

}
