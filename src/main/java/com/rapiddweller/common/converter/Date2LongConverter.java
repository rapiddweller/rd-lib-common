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

import java.time.Instant;
import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.util.Date;
import java.util.TimeZone;

/**
 * Converts a {@link Date} into the number of milliseconds since 1970-01-01 in a certain time zone and back.
 * By default, it uses the system's default time zone. If the 'zone' attribute is set, then the Dates are
 * assumed to refer to that time zone.
 * Created: 05.08.2007 07:10:25
 * @author Volker Bergmann
 */
public class Date2LongConverter extends AbstractZonedConverter<Date, Long> {

  private static final LoggerEscalator escalator = new LoggerEscalator();
  public static final String DEPRECATION_MESSAGE = "Property 'timeZone' of " + Date2LongConverter.class.getSimpleName() +
      " is deprecated and will be removed in a future version. Please use property 'zone' instead";
  private final ZoneRules defaultZoneRules;
  private ZoneRules zoneRules;

  // constructors ----------------------------------------------------------------------------------------------------

  public Date2LongConverter() {
    this(ZoneId.systemDefault());
  }

  public Date2LongConverter(ZoneId zone) {
    super(Date.class, Long.class, zone);
    this.defaultZoneRules = ZoneId.systemDefault().getRules();
  }

  // properties ------------------------------------------------------------------------------------------------------

  /** @deprecated Use the 'zone' property instead */
  @Deprecated
  public TimeZone getTimeZone() {
    escalateTimeZoneDeprecation();
    return JavaTimeUtil.toTimeZone(getZone());
  }

  /** @deprecated Use the 'zone' property instead */
  @Deprecated
  public void setTimeZone(TimeZone timeZone) {
    escalateTimeZoneDeprecation();
    setZone(JavaTimeUtil.toZoneId(timeZone));
  }

  @Override
  public void setZone(ZoneId zone) {
    super.setZone(zone);
    this.zoneRules = (zone != null ? zone.getRules() : null);
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
        Instant instant = sourceValue.toInstant();
        zoneRules = zone.getRules();
        int defaultZoneOffset = defaultZoneRules.getOffset(instant).getTotalSeconds();
        int zoneOffset = zoneRules.getOffset(instant).getTotalSeconds();
        result = result + defaultZoneOffset * 1000L - zoneOffset * 1000L;
      }
      return result;
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private void escalateTimeZoneDeprecation() {
    escalator.escalate(DEPRECATION_MESSAGE, Date2LongConverter.class, null);
  }

}
