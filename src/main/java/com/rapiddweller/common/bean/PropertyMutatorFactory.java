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

import com.rapiddweller.common.mutator.NamedMutator;

/**
 * Creates PropertyMutator objects by availability of
 * bean class name and/or property name and/or property type.
 * Created: 13.05.2005 09:26:14
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class PropertyMutatorFactory {

  /**
   * Gets property mutator.
   *
   * @param propertyName the property name
   * @return the property mutator
   */
  public static NamedMutator getPropertyMutator(String propertyName) {
    return getPropertyMutator(null, propertyName, true, true);
  }

  /**
   * Gets property mutator.
   *
   * @param beanClass    the bean class
   * @param propertyName the property name
   * @return the property mutator
   */
  public static NamedMutator getPropertyMutator(Class<?> beanClass, String propertyName) {
    return getPropertyMutator(beanClass, propertyName, true, false);
  }

  /**
   * Gets property mutator.
   *
   * @param beanClass    the bean class
   * @param propertyName the property name
   * @param required     the required
   * @param autoConvert  the auto convert
   * @return the property mutator
   */
  public static NamedMutator getPropertyMutator(Class<?> beanClass, String propertyName, boolean required, boolean autoConvert) {
    if (propertyName.contains(".")) {
      return new PropertyGraphMutator(beanClass, propertyName, required, autoConvert);
    } else if (beanClass != null) {
      return new TypedPropertyMutator(beanClass, propertyName, required, autoConvert);
    } else {
      return new UntypedPropertyMutator(propertyName, required, autoConvert);
    }
  }

}
