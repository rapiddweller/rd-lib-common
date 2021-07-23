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

package com.rapiddweller.common.format;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Concurrent implementation of the {@link SimpleDateFormat} features.
 * Created: 26.02.2010 15:27:23
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ConcurrentDateFormat extends DateFormat {

  private final ThreadLocal<SimpleDateFormat> format;

  /**
   * Instantiates a new Concurrent date format.
   *
   * @param pattern the pattern
   */
  public ConcurrentDateFormat(final String pattern) {
    format = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
  }

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    return format.get().format(date, toAppendTo, fieldPosition);
  }

  @Override
  public Date parse(String source, ParsePosition pos) {
    return format.get().parse(source, pos);
  }

  private static final long serialVersionUID = -1665638058197198209L;

}

