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

package com.rapiddweller.common.mutator;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.UpdateFailedException;

/**
 * Converts its input by a Converter object and forwards the result to another Mutator.
 * Created: 12.05.2005 19:08:30
 */
@SuppressWarnings("unchecked")
public class ConvertingMutator extends MutatorWrapper {

  @SuppressWarnings("rawtypes")
  private final Converter converter;

  /**
   * Instantiates a new Converting mutator.
   *
   * @param realMutator the real mutator
   * @param converter   the converter
   */
  @SuppressWarnings("rawtypes")
  public ConvertingMutator(Mutator realMutator, Converter converter) {
    super(realMutator);
    this.converter = converter;
  }

  @Override
  public void setValue(Object target, Object value) throws UpdateFailedException {
    try {
      Object convertedValue = converter.convert(value);
      realMutator.setValue(target, convertedValue);
    } catch (ConversionException e) {
      throw new UpdateFailedException(e);
    }
  }

}
