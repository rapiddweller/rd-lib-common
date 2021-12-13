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

package com.rapiddweller.common.bean;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.converter.ThreadSafeConverter;

/**
 * Converter implementation that extracts all property values of a JavaBean to a Properties object.
 * Created: 07.06.2007 14:11:58
 * @param <E> the bean type to access
 * @author Volker Bergmann
 */
public class BeanToPropertyArrayConverter<E> extends ThreadSafeConverter<E, Object[]> {

  private final PropertyAccessor<E, ?>[] accessors;

  public BeanToPropertyArrayConverter(String... propertyNames) {
    this(null, propertyNames);
  }

  @SuppressWarnings("unchecked")
  public BeanToPropertyArrayConverter(Class<E> beanClass, String... propertyNames) {
    super(beanClass, Object[].class);
    this.accessors = new PropertyAccessor[propertyNames.length];
    for (int i = 0; i < propertyNames.length; i++) {
      this.accessors[i] = PropertyAccessorFactory.getAccessor(beanClass, propertyNames[i]);
    }
  }

  @Override
  public Object[] convert(E bean) throws ConversionException {
    Object[] propertyValues = new Object[accessors.length];
    for (int i = 0; i < accessors.length; i++) {
      propertyValues[i] = accessors[i].getValue(bean);
    }
    return propertyValues;
  }

}
