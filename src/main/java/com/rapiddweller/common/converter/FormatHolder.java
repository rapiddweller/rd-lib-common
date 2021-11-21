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

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.Patterns;
import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Holds format strings for date and number objects.
 * Created at 01.10.2009 12:18:59
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public abstract class FormatHolder implements Patterns {

  // attributes ------------------------------------------------------------------------------------------------------

  /**
   * The string used to represent null values
   */
  protected String nullString;

  /**
   * The Date pattern.
   */
  protected String datePattern;

  /**
   * The Date capitalization.
   */
  protected Capitalization dateCapitalization;

  /**
   * The Date time pattern.
   */
  protected String dateTimePattern;

  /**
   * The Date time capitalization.
   */
  protected Capitalization dateTimeCapitalization;

  /**
   * The Time pattern.
   */
  protected String timePattern;

  /**
   * The Timestamp pattern.
   */
  protected String timestampPattern;

  /**
   * The Timestamp capitalization.
   */
  protected Capitalization timestampCapitalization;

  /**
   * The Decimal converter.
   */
  protected NumberFormatter decimalConverter;

  /**
   * The Integral converter.
   */
  protected NumberFormatter integralConverter;

  /**
   * The Char quote.
   */
  protected String charQuote;

  /**
   * The String quote.
   */
  protected String stringQuote;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Default constructor that uses an isEmpty String as null representation
   */
  public FormatHolder() {
    this(DEFAULT_NULL_STRING);
  }

  /**
   * Constructor that initializes the null replacement to the specified parameter.
   *
   * @param nullString the String to use for replacing null values.
   */
  public FormatHolder(String nullString) {
    this(nullString, DEFAULT_DATE_PATTERN, DEFAULT_TIMESTAMP_PATTERN);
  }

  /**
   * Instantiates a new Format holder.
   *
   * @param nullString       the null string
   * @param datePattern      the date pattern
   * @param timestampPattern the timestamp pattern
   */
  public FormatHolder(String nullString, String datePattern, String timestampPattern) {
    this.nullString = nullString;
    this.datePattern = datePattern;
    this.dateCapitalization = Capitalization.mixed;
    this.timestampPattern = timestampPattern;
    this.timestampCapitalization = Capitalization.mixed;
    this.timePattern = DEFAULT_TIME_PATTERN;
    this.dateTimePattern = DEFAULT_DATETIME_PATTERN;
    this.dateTimeCapitalization = Capitalization.mixed;
    this.integralConverter = null;
    this.decimalConverter = null;
    this.stringQuote = null;
    this.charQuote = null;
  }

  // properties ------------------------------------------------------------------------------------------------------

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
   * @param nullResult the null result
   */
  public void setNullString(String nullResult) {
    this.nullString = nullResult;
  }

  /**
   * Gets date pattern.
   *
   * @return the date pattern
   */
  public String getDatePattern() {
    return datePattern;
  }

  /**
   * Sets date pattern.
   *
   * @param pattern the pattern
   */
  public void setDatePattern(String pattern) {
    this.datePattern = pattern;
  }

  /**
   * Gets date capitalization.
   *
   * @return the date capitalization
   */
  public Capitalization getDateCapitalization() {
    return dateCapitalization;
  }

  /**
   * Sets date capitalization.
   *
   * @param dateCapitalization the date capitalization
   */
  public void setDateCapitalization(Capitalization dateCapitalization) {
    this.dateCapitalization = dateCapitalization;
  }

  /**
   * Gets date time pattern.
   *
   * @return the date time pattern
   */
  public String getDateTimePattern() {
    return dateTimePattern;
  }

  /**
   * Sets date time pattern.
   *
   * @param pattern the pattern
   */
  public void setDateTimePattern(String pattern) {
    this.dateTimePattern = pattern;
  }

  /**
   * Gets date time capitalization.
   *
   * @return the date time capitalization
   */
  public Capitalization getDateTimeCapitalization() {
    return dateTimeCapitalization;
  }

  /**
   * Sets date time capitalization.
   *
   * @param dateTimeCapitalization the date time capitalization
   */
  public void setDateTimeCapitalization(Capitalization dateTimeCapitalization) {
    this.dateTimeCapitalization = dateTimeCapitalization;
  }

  /**
   * Gets time pattern.
   *
   * @return the time pattern
   */
  public String getTimePattern() {
    return timePattern;
  }

  /**
   * Sets time pattern.
   *
   * @param timePattern the time pattern
   */
  public void setTimePattern(String timePattern) {
    this.timePattern = timePattern;
  }

  /**
   * Gets timestamp pattern.
   *
   * @return the timestamp pattern
   */
  public String getTimestampPattern() {
    return timestampPattern;
  }

  /**
   * Sets timestamp pattern.
   *
   * @param pattern the pattern
   */
  public void setTimestampPattern(String pattern) {
    this.timestampPattern = pattern;
  }

  /**
   * Gets timestamp capitalization.
   *
   * @return the timestamp capitalization
   */
  public Capitalization getTimestampCapitalization() {
    return timestampCapitalization;
  }

  /**
   * Sets timestamp capitalization.
   *
   * @param timestampCapitalization the timestamp capitalization
   */
  public void setTimestampCapitalization(
      Capitalization timestampCapitalization) {
    this.timestampCapitalization = timestampCapitalization;
  }

  /**
   * Gets decimal pattern.
   *
   * @return the decimal pattern
   */
  public String getDecimalPattern() {
    return decimalConverter.getPattern();
  }

  /**
   * Sets decimal pattern.
   *
   * @param pattern the pattern
   */
  public void setDecimalPattern(String pattern) {
    if (decimalConverter == null) {
      decimalConverter = new NumberFormatter(pattern);
    }
    decimalConverter.setPattern(pattern);
  }

  /**
   * Gets grouping separator.
   *
   * @return the grouping separator
   */
  public char getGroupingSeparator() {
    return decimalConverter.getDecimalSeparator();
  }

  /**
   * Sets grouping separator.
   *
   * @param groupingSeparator the grouping separator
   */
  public void setGroupingSeparator(char groupingSeparator) {
    if (decimalConverter == null) {
      decimalConverter = new NumberFormatter();
    }
    decimalConverter.setGroupingSeparator(groupingSeparator);
  }

  /**
   * Gets decimal separator.
   *
   * @return the decimal separator
   */
  public char getDecimalSeparator() {
    return decimalConverter.getDecimalSeparator();
  }

  /**
   * Sets decimal separator.
   *
   * @param separator the separator
   */
  public void setDecimalSeparator(char separator) {
    if (decimalConverter == null) {
      decimalConverter = new NumberFormatter();
    }
    decimalConverter.setDecimalSeparator(separator);
  }

  /**
   * Gets integral pattern.
   *
   * @return the integral pattern
   */
  public String getIntegralPattern() {
    return integralConverter.getPattern();
  }

  /**
   * Sets integral pattern.
   *
   * @param pattern the pattern
   */
  public void setIntegralPattern(String pattern) {
    if (integralConverter == null) {
      integralConverter = new NumberFormatter();
    }
    integralConverter.setPattern(pattern);
  }

  /**
   * Gets char quote.
   *
   * @return the char quote
   */
  public String getCharQuote() {
    return charQuote;
  }

  /**
   * Sets char quote.
   *
   * @param charQuote the char quote
   */
  public void setCharQuote(String charQuote) {
    this.charQuote = charQuote;
  }

  /**
   * Gets string quote.
   *
   * @return the string quote
   */
  public String getStringQuote() {
    return stringQuote;
  }

  /**
   * Sets string quote.
   *
   * @param stringQuote the string quote
   */
  public void setStringQuote(String stringQuote) {
    this.stringQuote = stringQuote;
  }

  @Override
  public Object clone() {
    try {
      FormatHolder copy = (FormatHolder) super.clone();
      copy.decimalConverter = (NumberFormatter) decimalConverter.clone();
      copy.integralConverter = (NumberFormatter) integralConverter.clone();
      return copy;
    } catch (CloneNotSupportedException e) {
      throw ExceptionFactory.getInstance().cloningFailed("Failed to clone " + this, e);
    }
  }

}
