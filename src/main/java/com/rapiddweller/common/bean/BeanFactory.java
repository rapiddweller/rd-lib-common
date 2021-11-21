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
import com.rapiddweller.common.mutator.NamedMutator;

import java.util.Map;

/**
 * Instantiates JavaBeans from class name and a Properties object
 * that contains the text representation of the bean property values.
 * Created: 04.09.2007 21:13:06
 * @author Volker Bergmann
 */
public class BeanFactory {

  private static final ClassProvider DEFAULT_CLASS_PROVIDER = new DefaultClassProvider();

  private BeanFactory() {
    // private constructor to prevent instantiation
  }

  /** Creates an object of the specified type.
   *  @param beanClassName the name of the class to instantiate
   *  @param properties    the bean properties
   *  @return an object of the specified class */
  public static Object newBean(String beanClassName, Map<String, Object> properties) {
    return newBean(beanClassName, properties, DEFAULT_CLASS_PROVIDER);
  }

  public static Object newBean(String beanClassName, Map<String, Object> properties, ClassProvider factory) {
    Object bean = BeanUtil.newInstance(factory.forName(beanClassName));
    for (Map.Entry<String, Object> entry : properties.entrySet()) {
      String propertyName = entry.getKey();
      NamedMutator mutator = PropertyMutatorFactory.getPropertyMutator(bean.getClass(), propertyName, false, true);
      mutator.setValue(bean, entry.getValue());
    }
    return bean;
  }

}
