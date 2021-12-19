/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.bean;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.exception.MutationFailed;
import com.rapiddweller.common.mutator.NamedMutator;

import java.lang.reflect.Field;

/**
 * Sets an object's field to a value.<br/><br/>
 * Created: 19.12.2021 11:49:01
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class FieldMutator implements NamedMutator {

  private Field field;

  public FieldMutator(Field field) {
    this.field = field;
  }

  @Override
  public String getName() {
    return field.getName();
  }

  @Override
  public void setValue(Object target, Object value) throws MutationFailed {
    BeanUtil.setFieldValue(field, target, value, true);
  }

}
