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
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parent class for {@link Converter} implementations which support all modes of threaded usage.<br/><br/>
 * Created: 26.02.2010 12:47:56
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class ThreadSafeConverter<S, T> extends AbstractConverter<S, T> implements Cloneable {

  protected ThreadSafeConverter(Class<S> sourceType, Class<T> targetType) {
    super(sourceType, targetType);
  }

  @Override
  public final boolean isThreadSafe() {
    return true;
  }

  @Override
  public final boolean isParallelizable() {
    return true;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw ExceptionFactory.getInstance().cloningFailed("Failed to clone " + this, e);
    }
  }

}
