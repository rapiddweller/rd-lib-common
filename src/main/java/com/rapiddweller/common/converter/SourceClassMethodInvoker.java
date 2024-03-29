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

import java.lang.reflect.Method;

/**
 * {@link Converter} implementation which invokes a no-arg method on the source object.<br/><br/>
 * Created: 27.02.2010 06:53:27
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class SourceClassMethodInvoker<S, T> extends ThreadSafeConverter<S, T> {

  private final Method method;

  protected SourceClassMethodInvoker(Class<S> sourceType, Class<T> targetType, Method method) {
    super(sourceType, targetType);
    this.method = method;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T convert(S sourceValue) throws ConversionException {
    return (T) BeanUtil.invoke(sourceValue, method, null);
  }

}
