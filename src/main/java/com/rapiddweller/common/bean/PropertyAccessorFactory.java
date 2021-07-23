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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConfigurationError;

import java.beans.PropertyDescriptor;

/**
 * Creates PropertyAccessors that are as efficient as possible depending on the information provided.
 * The fastest accessors are available when the bean class is provided with the property name.
 * property names may express navigation over refering JavaBean entities, e.g. 'category.name'.
 * A PropertyAccessor in a non-strict mode converts the invocation argument to the correct property type
 * and behaves quietly, if it doesn not find the specified property
 * Created: 06.01.2005 20:04:36
 *
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class PropertyAccessorFactory {

  /**
   * private constructor for preventing that the class is instantiated
   */
  private PropertyAccessorFactory() {
  }

  /**
   * Gets accessor.
   *
   * @param propertyName the name of the property to access
   * @return a property accessor without knowledge about the bean type (the slowest PropertyAccessor type) in strict mode.
   */
  @SuppressWarnings("rawtypes")
  public static PropertyAccessor getAccessor(String propertyName) {
    return getAccessor(null, propertyName);
  }

  /**
   * Gets accessor.
   *
   * @param propertyName the name of the property to access
   * @param strict       set to true if the property must exist
   * @return a property accessor of the specified strictness.
   */
  @SuppressWarnings("rawtypes")
  public static PropertyAccessor getAccessor(String propertyName, boolean strict) {
    return getAccessor(null, propertyName, strict);
  }

  /**
   * @return a property accessor without knowledge about the bean type
   * (the slowest PropertyAccessor type) in strict mode.
   */
/*
    public static PropertyAccessor getAccessor(String propertyName, Class propertyType) {
        return getAccessor(null, propertyName, propertyType, true);
    }
*/

  /**
   * Gets accessor.
   *
   * @param beanClass    the bean type to access
   * @param propertyName the name of the property to access
   * @return a property accessor in strict mode.
   */
  @SuppressWarnings("rawtypes")
  public static PropertyAccessor getAccessor(Class<?> beanClass, String propertyName) {
    return getAccessor(beanClass, propertyName, true);
  }

  /**
   * Gets accessor.
   *
   * @param beanClass    the bean type to access
   * @param propertyName the name of the property to access
   * @param strict       set to true if the property must exist
   * @return a property accessor of the specified strictness.
   */
  @SuppressWarnings("rawtypes")
  public static PropertyAccessor getAccessor(Class<?> beanClass, String propertyName, boolean strict) {
    if (beanClass != null) {
      PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(beanClass, propertyName);
      if (propertyDescriptor == null) {
        if (strict) {
          throw new ConfigurationError("No property '" + propertyName + "' found in " + beanClass);
        }
      }
    }
    int index = propertyName.indexOf('.');
    if (index < 0) {
      if (beanClass == null) {
        return new UntypedPropertyAccessor(propertyName, strict);
      } else {
        return new TypedPropertyAccessor(beanClass, propertyName, strict);
      }
    } else {
      return new PropertyGraphAccessor(beanClass, propertyName, strict);
    }
  }

}
