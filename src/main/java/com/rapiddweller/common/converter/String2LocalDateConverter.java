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

import com.rapiddweller.common.ConversionException;

import java.time.LocalDate;

/**
 * Parses a String as {@link LocalDate}.<br><br>
 * Created: 28.07.2019 20:30:49
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class String2LocalDateConverter extends ThreadSafeConverter<String, LocalDate> {

  public String2LocalDateConverter() {
    super(String.class, LocalDate.class);
  }

  @Override
  public LocalDate convert(String target) throws ConversionException {
    return LocalDate.parse(target);
  }

}
