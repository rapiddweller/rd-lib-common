/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.MutationFailed;
import com.rapiddweller.common.mutator.NamedMutator;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Mutates JavaBean object graphs.
 * Created: 08.05.2005 06:24:41
 * @author Volker Bergmann
 * @since 0.1
 */
public class PropertyGraphMutator implements NamedMutator {

  private static final Logger logger = LoggerFactory.getLogger(PropertyGraphMutator.class);

  private final boolean required;
  private final boolean autoConvert;
  private PropertyAccessor<Object, ?>[] subAccessors;
  private final NamedMutator lastMutator;
  private final String propertyName;

  // constructors ----------------------------------------------------------------------------------------------------

  public PropertyGraphMutator(String propertyName) {
    this(propertyName, true, false);
  }

  public PropertyGraphMutator(String propertyName, boolean required, boolean autoConvert) {
    this(null, propertyName, required, autoConvert);
  }

  public PropertyGraphMutator(Class<?> beanClass, String propertyName) {
    this(beanClass, propertyName, true, false);
  }

  @SuppressWarnings("unchecked")
  public PropertyGraphMutator(Class<?> beanClass, String propertyName, boolean required, boolean autoConvert) {
    this.propertyName = propertyName;
    this.required = required;
    this.autoConvert = autoConvert;
    int separatorIndex = propertyName.indexOf('.');
    if (separatorIndex >= 0) {
      String[] nodeNames = StringUtil.tokenize(propertyName, '.');
      Class<?> nodeClass = beanClass;
      subAccessors = new PropertyAccessor[nodeNames.length - 1];
      for (int i = 0; i < nodeNames.length - 1; i++) {
        subAccessors[i] = PropertyAccessorFactory.getAccessor(nodeClass, nodeNames[i], required);
        nodeClass = subAccessors[i].getValueType();
      }
      String lastNodeName = nodeNames[nodeNames.length - 1];
      if (beanClass != null) {
        lastMutator = PropertyMutatorFactory.getPropertyMutator(
            subAccessors[subAccessors.length - 1].getValueType(), lastNodeName, required, autoConvert);
      } else {
        lastMutator = new UntypedPropertyMutator(lastNodeName, required, autoConvert);
      }
    } else {
      lastMutator = new TypedPropertyMutator(beanClass, propertyName, required, autoConvert);
    }
  }

  /**
   * Is required boolean.
   *
   * @return the boolean
   */
  public boolean isRequired() {
    return required;
  }

  /**
   * Is auto convert boolean.
   *
   * @return the boolean
   */
  public boolean isAutoConvert() {
    return autoConvert;
  }

  // PropertyMutator interface ---------------------------------------------------------------------------------------

  @Override
  public String getName() {
    return propertyName;
  }

  @Override
  public void setValue(Object bean, Object propertyValue) throws MutationFailed {
    if (bean == null) {
      if (required) {
        throw ExceptionFactory.getInstance().illegalArgument("Cannot set a property on null");
      } else {
        return;
      }
    }
    logger.debug("setting property '{}' to '{}' on bean {}", getName(), propertyValue, bean);
    Object superBean = bean;
    if (subAccessors != null) {
      for (PropertyAccessor<Object, ?> subAccessor : subAccessors) {
        Object subBean = subAccessor.getValue(superBean);
        if (subBean == null && propertyValue != null) {
          // bean is null but since there is something to set create one
          Class<?> propertyType = subAccessor.getValueType();
          subBean = BeanUtil.newInstance(propertyType);
          BeanUtil.setPropertyValue(superBean, subAccessor.getPropertyName(), subBean);
        }
        superBean = subBean;
      }
    }
    lastMutator.setValue(superBean, propertyValue);
  }

  /**
   * Sets property graph.
   *
   * @param bean          the bean
   * @param propertyGraph the property graph
   * @param propertyValue the property value
   * @param required      the required
   * @param autoConvert   the auto convert
   */
  public static void setPropertyGraph(Object bean, String propertyGraph, Object propertyValue, boolean required, boolean autoConvert) {
    new PropertyGraphMutator(bean.getClass(), propertyGraph, required, autoConvert).setValue(bean, propertyValue);
  }

}
