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
import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Holds a {@link NumberFormat} and exhibits properties for its configuration.
 * Created: 26.02.2010 08:37:23
 *
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class NumberFormatBasedConverter<S, T> extends AbstractConverter<S, T> implements Cloneable {

  // constants -------------------------------------------------------------------------------------------------------

  /**
   * The constant DEFAULT_DECIMAL_PATTERN.
   */
  protected static final String DEFAULT_DECIMAL_PATTERN = "0.#";
  /**
   * The constant DEFAULT_DECIMAL_SEPARATOR.
   */
  protected static final char DEFAULT_DECIMAL_SEPARATOR = '.';
  /**
   * The constant DEFAULT_GROUPING_SEPARATOR.
   */
  protected static final char DEFAULT_GROUPING_SEPARATOR = ',';
  /**
   * The constant DEFAULT_NULL_STRING.
   */
  protected static final String DEFAULT_NULL_STRING = "";

  // attributes ------------------------------------------------------------------------------------------------------

  private String pattern;
  private char decimalSeparator;
  private char groupingSeparator;
  /**
   * The Format.
   */
  protected DecimalFormat format;

  /**
   * The string used to represent null values
   */
  private String nullString;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Number format based converter.
   *
   * @param sourceType the source type
   * @param targetType the target type
   */
  public NumberFormatBasedConverter(Class<S> sourceType, Class<T> targetType) {
    this(sourceType, targetType, DEFAULT_DECIMAL_PATTERN);
  }

  /**
   * Instantiates a new Number format based converter.
   *
   * @param sourceType the source type
   * @param targetType the target type
   * @param pattern    the pattern
   */
  public NumberFormatBasedConverter(Class<S> sourceType, Class<T> targetType, String pattern) {
    super(sourceType, targetType);
    setPattern(pattern);
    setDecimalSeparator(DEFAULT_DECIMAL_SEPARATOR);
    setGroupingSeparator(DEFAULT_GROUPING_SEPARATOR);
    setNullString(DEFAULT_NULL_STRING);
  }

  // properties ------------------------------------------------------------------------------------------------------

  /**
   * Gets pattern.
   *
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Sets pattern.
   *
   * @param pattern the pattern
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
    this.format = new DecimalFormat(pattern);
    setDecimalSeparator(decimalSeparator);
  }

  /**
   * Gets decimal separator.
   *
   * @return the decimal separator
   */
  public char getDecimalSeparator() {
    return decimalSeparator;
  }

  /**
   * Sets grouping separator.
   *
   * @param groupingSeparator the grouping separator
   */
  public void setGroupingSeparator(char groupingSeparator) {
    this.groupingSeparator = groupingSeparator;
    updateFormat();
  }

  /**
   * Gets grouping separator.
   *
   * @return the grouping separator
   */
  public char getGroupingSeparator() {
    return decimalSeparator;
  }

  /**
   * Sets decimal separator.
   *
   * @param decimalSeparator the decimal separator
   */
  public void setDecimalSeparator(char decimalSeparator) {
    this.decimalSeparator = decimalSeparator;
    updateFormat();
  }

  private void updateFormat() {
    DecimalFormatSymbols newSymbols = new DecimalFormatSymbols();
    if (groupingSeparator != 0) {
      newSymbols.setGroupingSeparator(groupingSeparator);
    }
    newSymbols.setDecimalSeparator(this.decimalSeparator);
    format.setDecimalFormatSymbols(newSymbols);
  }

  /**
   * Gets null string.
   *
   * @return the null string
   */
  public String getNullString() {
    return nullString;
  }

  /**
   * Sets null string.
   *
   * @param nullString the null string
   */
  public void setNullString(String nullString) {
    this.nullString = nullString;
  }

  /**
   * Format string.
   *
   * @param input the input
   * @return the string
   */
  protected String format(Number input) {
    return (input != null ? format.format(input) : nullString);
  }

  /**
   * Parse number.
   *
   * @param input the input
   * @return the number
   * @throws ConversionException the conversion exception
   */
  protected Number parse(String input) throws ConversionException {
    if (input == null || NullSafeComparator.equals(input, nullString)) {
      return null;
    }
    try {
      return format.parse(input);
    } catch (ParseException e) {
      throw new ConversionException("Error parsing " + input + " as number");
    }
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return getClass().getSimpleName() + '[' + pattern + ']';
  }

  @Override
  public boolean isThreadSafe() {
    return false;
  }

  @Override
  public boolean isParallelizable() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object clone() {
    try {
      NumberFormatBasedConverter<S, T> copy = (NumberFormatBasedConverter<S, T>) super.clone();
      copy.format = (DecimalFormat) format.clone();
      return copy;
    } catch (CloneNotSupportedException e) {
      throw ExceptionFactory.getInstance().cloningFailed("Failed to clone " + this, e);
    }
  }

}
