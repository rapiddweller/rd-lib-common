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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;

import java.lang.reflect.Constructor;

/**
 * {@link Converter} implementation which invokes a constructor of the target class
 * with the source object as argument.
 * Created: 27.02.2010 06:57:40
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ConstructorInvoker<S, T> extends ThreadSafeConverter<S, T> {

  Constructor<T> constructor;

  public ConstructorInvoker(Class<S> sourceType, Constructor<T> constructor) {
    super(sourceType, constructor.getDeclaringClass());
    this.constructor = constructor;
  }

  @Override
  public T convert(S sourceValue) throws ConversionException {
    return BeanUtil.newInstance(constructor, sourceValue);
  }

}
