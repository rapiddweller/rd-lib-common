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
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Parent class for {@link Converter} implementations that
 * holds references to several other converter objects.
 * Created: 26.02.2010 13:50:43
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class MultiConverterWrapper<S, T> implements Cloneable {

  protected Converter<S, T>[] components;

  protected MultiConverterWrapper(Converter<S, T>[] components) {
    this.components = components;
  }

  // properties ------------------------------------------------------------------------------------------------------

  public Converter<S, T>[] getComponents() {
    return components;
  }

  public void setComponents(Converter<S, T>[] converters) {
    this.components = converters;
  }

  public void addComponent(Converter<S, T> converter) {
    this.components = ArrayUtil.append(converter, this.components);
  }

  // Converter interface implementation ------------------------------------------------------------------------------

  public boolean isThreadSafe() {
    for (Converter<?, ?> converter : components) {
      if (!converter.isThreadSafe()) {
        return false;
      }
    }
    return true;
  }

  public boolean isParallelizable() {
    for (Converter<?, ?> converter : components) {
      if (!converter.isParallelizable()) {
        return false;
      }
    }
    return true;
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  public Object clone() {
    try {
      MultiConverterWrapper copy = (MultiConverterWrapper) super.clone();
      copy.components = ConverterManager.cloneIfSupported(this.components);
      return copy;
    } catch (CloneNotSupportedException e) {
      throw ExceptionFactory.getInstance().cloningFailed("Failed to clone " + this, e);
    }
  }

}
