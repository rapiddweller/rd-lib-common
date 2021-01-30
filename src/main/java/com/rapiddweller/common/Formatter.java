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

import com.rapiddweller.common.converter.PercentageFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Provides text formatting features for different data types.
 * Created: 06.07.2013 10:21:19
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class Formatter {
	
	public static final String DEFAULT_NUMBER_PATTERN = "#,##0.00";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public static final String DEFAULT_DATE_TIME_PATTERN = DEFAULT_DATE_PATTERN + " " + DEFAULT_TIME_PATTERN;
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

	public static String formatPercentage(double fraction) {
		return PercentageFormatter.format(fraction, 1, false);
	}
	
	public static String formatPercentalChange(double fraction) {
		return formatPercentalChange(fraction, 1);
	}

	public static String formatPercentalChange(double fraction, int fractionDigits) {
		return PercentageFormatter.format(fraction, fractionDigits, true);
	}

	public static String format(LocalDate localDate) {
		return (localDate != null ? localDate.format(DATE_FORMATTER) : null);
	}

	public static String format(ZonedDateTime date) {
		return date.format(DATE_TIME_FORMATTER);
	}

	public static String format(Date date) {
		return format(date, DEFAULT_DATE_PATTERN);
	}

	public static String formatDate(Date date) {
		return format(date, DEFAULT_DATE_PATTERN);
	}

	public static String formatDateTime(Date date) {
		return format(date, DEFAULT_DATE_TIME_PATTERN);
	}

	public static String formatTime(Date date) {
		return format(date, DEFAULT_TIME_PATTERN);
	}

	public static String format(Date date, String pattern) {
		return (date != null ? new SimpleDateFormat(pattern).format(date) : "null");
	}
	
	public static String formatLocal(Date date) {
		return (date != null ? DateFormat.getDateInstance().format(date) : "null");
	}

	public static String format(double value) {
		return format(value, Locale.getDefault());
	}

	public static String format(double value, int maximumFractionDigits) {
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(maximumFractionDigits);
		return format.format(value);
	}

	public static String format(double value, Locale locale) {
		if (Double.isNaN(value))
			return "NaN";
		else
			return new DecimalFormat(DEFAULT_NUMBER_PATTERN, DecimalFormatSymbols.getInstance(locale)).format(value);
	}

	public static String formatDaysFromNow(Date date) {
		int days = TimeUtil.daysBetween(TimeUtil.today(), date);
		switch (days) {
			case -2: return getBundle().getString("days_from_now.two_ago");
			case -1: return getBundle().getString("days_from_now.yesterday");
			case  0: return getBundle().getString("days_from_now.today");
			case  1: return getBundle().getString("days_from_now.tomorrow");
			case  2: return getBundle().getString("days_from_now.two_later");
			default: String key = (days < 0 ? "days_from_now.n_ago" : "days_from_now.n_later");
					 String format = getBundle().getString(key);
					 return MessageFormat.format(format, Math.abs(days));
		}
	}

	private static ResourceBundle getBundle() {
		return PropertyResourceBundle.getBundle("com/rapiddweller/common/formatter", Locale.getDefault());
	}

}
