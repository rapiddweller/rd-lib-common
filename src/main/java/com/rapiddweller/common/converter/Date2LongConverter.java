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
import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.LoggerEscalator;

import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * Converts a {@link Date} into the number of milliseconds since 1970-01-01 in a certain time zone and back.
 * By default, it uses the system's default time zone.
 * Created: 05.08.2007 07:10:25
 * @author Volker Bergmann
 */
public class Date2LongConverter extends AbstractZonedConverter<Date, Long> {

  private static final LoggerEscalator escalator = new LoggerEscalator();
  public static final String DEPRECATION_MESSAGE = "Property 'timeZone' of " + Date2LongConverter.class.getSimpleName() +
      " is deprecated and will be removed in a future version. Please use property 'zone' instead";

  // constructors ----------------------------------------------------------------------------------------------------

  public Date2LongConverter() {
    this(ZoneId.systemDefault());
  }

  public Date2LongConverter(ZoneId zone) {
    super(Date.class, Long.class, zone);
  }

  // properties ------------------------------------------------------------------------------------------------------

  public TimeZone getTimeZone() {
    escalateTimeZoneDeprecation();
    return JavaTimeUtil.toTimeZone(getZone());
  }

  public void setTimeZone(TimeZone timeZone) {
    escalateTimeZoneDeprecation();
    setZone(JavaTimeUtil.toZoneId(timeZone));
  }

  // Converter interface implementation ------------------------------------------------------------------------------

  @Override
  public Class<Long> getTargetType() {
    return Long.class;
  }

  @Override
  public Long convert(Date sourceValue) throws ConversionException {
    if (sourceValue == null) {
      return null;
    } else {
      long result = sourceValue.getTime();
      if (zone != null) {
        result += zone.getRules().getOffset(sourceValue.toInstant()).getTotalSeconds() * 1000L
          - ZoneId.systemDefault().getRules().getOffset(sourceValue.toInstant()).getTotalSeconds() * 1000L;
      }
      return result;
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private void escalateTimeZoneDeprecation() {
    escalator.escalate(DEPRECATION_MESSAGE, Date2LongConverter.class, null);
  }

}
