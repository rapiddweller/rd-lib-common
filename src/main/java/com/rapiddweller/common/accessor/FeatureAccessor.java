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

package com.rapiddweller.common.accessor;

import com.rapiddweller.common.Accessor;
import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Composite;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.Escalator;
import com.rapiddweller.common.LoggerEscalator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Get values from Maps, Contexts, Composites and JavaBeans.
 * Created: 12.06.2007 18:36:11
 *
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 * @author Volker Bergmann
 */
public class FeatureAccessor<C, V> implements Accessor<C, V> {

  private static final Logger LOGGER = LogManager.getLogger(FeatureAccessor.class);

  private static final Escalator escalator = new LoggerEscalator();

  private String featureName;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Feature accessor.
   *
   * @param featureName the feature name
   */
  public FeatureAccessor(String featureName) {
    this(featureName, true);
  }

  /**
   * Instantiates a new Feature accessor.
   *
   * @param featureName the feature name
   * @param strict      the strict
   */
  public FeatureAccessor(String featureName, boolean strict) {
    LOGGER.debug("FeatureAccessor({}, {})", featureName, strict);
    this.featureName = featureName;
  }

  /**
   * Gets feature name.
   *
   * @return the feature name
   */
  public String getFeatureName() {
    return featureName;
  }

  /**
   * Sets feature name.
   *
   * @param featureName the feature name
   */
  public void setFeatureName(String featureName) {
    this.featureName = featureName;
  }

  // Accessor interface implementation -------------------------------------------------------------------------------

  @Override
  @SuppressWarnings("unchecked")
  public V getValue(C target) {
    return (V) getValue(target, featureName);
  }

  // static convenience methods --------------------------------------------------------------------------------------

  /**
   * Gets value.
   *
   * @param target      the target
   * @param featureName the feature name
   * @return the value
   */
  public static Object getValue(Object target, String featureName) {
    LOGGER.debug("getValue({}, {})", target, featureName);
    return getValue(target, featureName, true);
  }

  /**
   * Gets value.
   *
   * @param target      the target
   * @param featureName the feature name
   * @param required    the required
   * @return the value
   */
  @SuppressWarnings("unchecked")
  public static Object getValue(Object target, String featureName, boolean required) {
    if (target == null) {
      return null;
    } else if (target instanceof Map) {
      return ((Map<String, Object>) target).get(featureName);
    } else if (target instanceof Context) {
      return ((Context) target).get(featureName);
    } else if (target instanceof Composite) {
      return ((Composite) target).getComponent(featureName);
    } else {
      // try generic get(String) method
      Method genericGetMethod = BeanUtil.findMethod(target.getClass(), "get", String.class);
      if (genericGetMethod != null) {
        return BeanUtil.invoke(target, genericGetMethod, new Object[] {featureName});
      }
      // try JavaBean property
      PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(target.getClass(), featureName);
      if (propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
        try {
          return propertyDescriptor.getReadMethod().invoke(target);
        } catch (Exception e) {
          throw new ConfigurationError("Unable to read property '" + featureName + "'", e);
        }
      } else {
        Class<?> type = ((target instanceof Class) ? (Class<?>) target : target.getClass());
        Field field = BeanUtil.getField(type, featureName);
        if (field != null) {
          return BeanUtil.getFieldValue(field, target, false);
        }
      }
    }
    // the feature has not been identified, yet - escalate or raise an exception
    if (required) {
      throw new UnsupportedOperationException(
          target.getClass() + " does not support a feature '" + featureName + "'");
    } else {
      escalator.escalate("Feature '" + featureName + "' not found in object " + target, FeatureAccessor.class, null);
    }
    return null;
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return getClass().getSimpleName() + '[' + featureName + ']';
  }
}
