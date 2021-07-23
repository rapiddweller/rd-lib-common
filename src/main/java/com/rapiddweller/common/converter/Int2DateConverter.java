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

package com.rapiddweller.common.converter;

import java.util.Date;

/**
 * Converts {@link Integer} values to {@link Date} objects.
 * Created: 10.01.2011 11:59:43
 *
 * @author Volker Bergmann
 * @since 0.6.4
 */
public class Int2DateConverter extends ConverterChain<Integer, Date> {

  /**
   * Instantiates a new Int 2 date converter.
   */
  public Int2DateConverter() {
    super(new NumberToNumberConverter<>(Integer.class, Long.class),
        new Long2DateConverter());
  }

  @Override
  public Class<Integer> getSourceType() {
    return Integer.class;
  }

}
