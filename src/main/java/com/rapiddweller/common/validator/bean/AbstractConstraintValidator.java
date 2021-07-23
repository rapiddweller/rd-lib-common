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

package com.rapiddweller.common.validator.bean;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Validator;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

/**
 * Abstract implementation of the {@link ConstraintValidator} interface.
 * Created at 08.11.2008 07:52:34
 *
 * @param <A> the corresponding annotation
 * @param <T> the type of object to validate
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class AbstractConstraintValidator<A extends Annotation, T> implements Validator<T>, ConstraintValidator<A, T> {

  @Override
  public void initialize(A params) {
  }

  @Override
  public String toString() {
    return BeanUtil.toString(this);
  }

  @Override
  public boolean valid(T candidate) {
    return isValid(candidate, null);
  }

}
