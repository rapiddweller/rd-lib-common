/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.bean;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.mutator.AnyMutator;
import com.rapiddweller.common.mutator.NamedMutator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * Creates {@link Mutator}s.<br/><br/>
 * Created: 19.12.2021 11:30:15
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class NamedMutatorFactory {

  /** private constructor to prevent instantiation of this utility class. */
  private NamedMutatorFactory() {
    // private constructor to prevent instantiation of this utility class
  }

  public static NamedMutator create(Class<?> beanClass, String featureName, boolean required, boolean autoConvert) {
    if (featureName.contains(".")) {
      return new PropertyGraphMutator(beanClass, featureName, required, autoConvert);
    } else if (beanClass != null) {
      PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(beanClass, featureName, false);
      if (propertyDescriptor != null) {
        return new TypedPropertyMutator(beanClass, featureName, true, true);
      } else {
        Field field = BeanUtil.getField(beanClass, featureName);
        if (field != null) {
          return new FieldMutator(field);
        }
      }
    }
    return new AnyMutator(featureName, required, autoConvert);
  }

}
