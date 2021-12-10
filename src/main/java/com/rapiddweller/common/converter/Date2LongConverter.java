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

import java.util.Date;
import java.util.TimeZone;

/**
 * Converts a {@link Date} into the number of milliseconds since 1970-01-01 in a certain time zone and back.
 * By default it uses the system's default time zone.
 * Created: 05.08.2007 07:10:25
 * @author Volker Bergmann
 */
public class Date2LongConverter extends ThreadSafeConverter<Date, Long> {

  private TimeZone timeZone;

  // construcors -----------------------------------------------------------------------------------------------------

  public Date2LongConverter() {
    this(TimeZone.getDefault());
  }

  public Date2LongConverter(TimeZone timeZone) {
    super(Date.class, Long.class);
    this.timeZone = timeZone;
  }

  // properties ------------------------------------------------------------------------------------------------------

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }

  // BidirectionalConverter interface implementation -----------------------------------------------------------------

  @Override
  public Class<Long> getTargetType() {
    return Long.class;
  }

  @Override
  public Long convert(Date sourceValue) throws ConversionException {
    if (sourceValue == null) {
      return null;
    }
    return sourceValue.getTime() + timeZone.getRawOffset();
  }

}
