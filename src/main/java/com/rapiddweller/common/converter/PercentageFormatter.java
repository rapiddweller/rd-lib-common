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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Formats percentage values.
 * Created: 19.06.2013 07:30:33
 * @author Volker Bergmann
 * @since 0.5.24
 */
public class PercentageFormatter {

  public static String formatChange(double value) {
    return format(value, 1, true);
  }

  public static String format(double value, int fractalDigits, boolean renderPlus) {
    return format(value, fractalDigits, renderPlus, Locale.getDefault());
  }

  public static String format(double value, int fractalDigits, boolean renderPlus, Locale locale) {
    NumberFormat format = DecimalFormat.getInstance(locale);
    format.setMinimumFractionDigits(fractalDigits);
    format.setMaximumFractionDigits(fractalDigits);
    return (renderPlus && value > 0 ? "+" : "") + format.format(value * 100) + "%";
  }

}
