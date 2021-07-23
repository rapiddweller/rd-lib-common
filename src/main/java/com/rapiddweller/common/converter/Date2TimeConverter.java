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

import java.sql.Time;
import java.util.Date;

/**
 * Converts {@link Date} objects to {@link Time} objects.
 * Created: 24.01.2013 17:02:27
 *
 * @author Volker Bergmann
 * @since 0.5.21
 */
public class Date2TimeConverter extends ThreadSafeConverter<Date, Time> {

  /**
   * Instantiates a new Date 2 time converter.
   */
  public Date2TimeConverter() {
    super(Date.class, Time.class);
  }

  @Override
  public Time convert(Date sourceValue) throws ConversionException {
    return new Time(sourceValue.getTime());
  }

}
