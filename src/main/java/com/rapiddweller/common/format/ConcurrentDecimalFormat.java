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

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Thread-safe, concurrent wrapper of Java's {@link DecimalFormat} class.
 * Created: 12.10.2010 17:47:35
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ConcurrentDecimalFormat extends Format {

  private static final long serialVersionUID = 7100542444272244206L;

  private final ThreadLocal<DecimalFormat> format;

  /**
   * Instantiates a new Concurrent decimal format.
   *
   * @param pattern the pattern
   */
  public ConcurrentDecimalFormat(final String pattern) {
    format = ThreadLocal.withInitial(() -> new DecimalFormat(pattern));
  }

  @Override
  public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
    return format.get().format(number, toAppendTo, pos);
  }

  @Override
  public Object parseObject(String source, ParsePosition pos) {
    return format.get().parseObject(source, pos);
  }

}
