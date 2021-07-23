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
import com.rapiddweller.common.Patterns;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts {@link Time} objects to {@link String}s.
 * Created: 26.02.2010 07:59:11
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class Time2StringConverter extends ThreadSafeConverter<Time, String> {

  /**
   * Instantiates a new Time 2 string converter.
   */
  public Time2StringConverter() {
    super(Time.class, String.class);
  }

  @Override
  public String convert(Time target) throws ConversionException {
    return new SimpleDateFormat(Patterns.DEFAULT_TIME_MILLIS_PATTERN).format(new Date(target.getTime()));
  }

}
