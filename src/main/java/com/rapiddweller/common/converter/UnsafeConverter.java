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
 * Parent class for {@link Converter}s that are neither parallelizable nor thead-safe.<br/><br/>
 * Created: 26.02.2010 16:19:44
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class UnsafeConverter<S, T> extends AbstractConverter<S, T> {

  protected UnsafeConverter(Class<S> sourceType, Class<T> targetType) {
    super(sourceType, targetType);
  }

  @Override
  public boolean isParallelizable() {
    return false;
  }

  @Override
  public boolean isThreadSafe() {
    return false;
  }

}
