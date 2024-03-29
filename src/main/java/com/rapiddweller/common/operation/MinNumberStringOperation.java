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

package com.rapiddweller.common.operation;

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Operation;
import com.rapiddweller.common.converter.NumberParser;

/**
 * Returns the maximum value of several number literals.
 * Created: 08.03.2008 07:18:09
 *
 * @author Volker Bergmann
 * @since 0.4.0
 */
@SuppressWarnings("unchecked")
public class MinNumberStringOperation implements Operation<String, String> {

  @SuppressWarnings("rawtypes")
  private final MinOperation<ComparableWrapper> operation;

  private final Converter<String, ?> parser;

  /**
   * Instantiates a new Min number string operation.
   */
  public MinNumberStringOperation() {
    this.operation = new MinOperation<>();
    this.parser = new NumberParser();
  }

  @Override
  public String perform(String... args) {
    ComparableWrapper<String>[] wrappers = ComparableWrapper.wrapAll(args, parser);
    ComparableWrapper<String> min = operation.perform(wrappers);
    return min.realObject;
  }

}