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
 * Abstract implementation of the {@link Converter} interface, providing management of source and target type.
 *
 * @param <S> the type to convert from
 * @param <T> the type to convert to
 * @author Volker Bergmann
 */
public abstract class AbstractConverter<S, T> implements Converter<S, T> {

  /**
   * The Source type.
   */
  protected Class<S> sourceType;

  /**
   * The Target type.
   */
  protected Class<T> targetType;

  /**
   * Instantiates a new Abstract converter.
   *
   * @param sourceType the source type
   * @param targetType the target type
   */
  protected AbstractConverter(Class<S> sourceType, Class<T> targetType) {
    this.sourceType = sourceType;
    this.targetType = targetType;
  }

  @Override
  public Class<T> getTargetType() {
    return targetType;
  }

  @Override
  public Class<S> getSourceType() {
    return sourceType;
  }

}
