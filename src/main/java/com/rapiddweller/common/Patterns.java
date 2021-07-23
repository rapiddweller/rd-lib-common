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

package com.rapiddweller.common;

/**
 * Assembles default patterns for all databene format and {@link Converter}s.
 * Created at 01.10.2009 12:36:09
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public interface Patterns {

  /**
   * The constant DEFAULT_NULL_STRING.
   */
  String DEFAULT_NULL_STRING = "";

  /**
   * The constant DEFAULT_DATE_PATTERN.
   */
  String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

  // time patterns ---------------------------------------------------------------------------------------------------

  /**
   * The constant DEFAULT_TIME_MILLIS_PATTERN.
   */
  String DEFAULT_TIME_MILLIS_PATTERN = "HH:mm:ss.SSS";

  /**
   * The constant DEFAULT_TIME_SECONDS_PATTERN.
   */
  String DEFAULT_TIME_SECONDS_PATTERN = "HH:mm:ss";

  /**
   * The constant DEFAULT_TIME_MINUTES_PATTERN.
   */
  String DEFAULT_TIME_MINUTES_PATTERN = "HH:mm";

  /**
   * The constant DEFAULT_TIME_PATTERN.
   */
  String DEFAULT_TIME_PATTERN = DEFAULT_TIME_SECONDS_PATTERN;

  // datetime patterns -----------------------------------------------------------------------------------------------

  /**
   * The constant DEFAULT_DATETIME_MINUTES_PATTERN.
   */
  String DEFAULT_DATETIME_MINUTES_PATTERN = "yyyy-MM-dd'T'HH:mm";

  /**
   * The constant DEFAULT_DATETIME_SECONDS_PATTERN.
   */
  String DEFAULT_DATETIME_SECONDS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

  /**
   * The constant DEFAULT_DATETIME_MILLIS_PATTERN.
   */
  String DEFAULT_DATETIME_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  /**
   * The constant DEFAULT_DATETIME_MICROS_PATTERN.
   */
  String DEFAULT_DATETIME_MICROS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

  /**
   * The constant DEFAULT_DATETIME_NANOS_PATTERN.
   */
  String DEFAULT_DATETIME_NANOS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";

  /**
   * The constant DEFAULT_DATETIME_PATTERN.
   */
  String DEFAULT_DATETIME_PATTERN = DEFAULT_DATETIME_SECONDS_PATTERN;

  // timestamp patterns ----------------------------------------------------------------------------------------------

  /**
   * The constant DEFAULT_TIMESTAMP_PATTERN.
   */
  String DEFAULT_TIMESTAMP_PATTERN = DEFAULT_DATETIME_NANOS_PATTERN;

}
