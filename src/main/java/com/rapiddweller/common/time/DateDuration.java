/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.time;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.HF;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a calendric durations defined by a number of years, months and days.<br/><br/>
 * Created: 28.07.2022 09:59:02
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class DateDuration {

	// static factory methods ----------------------------------------------------------------------------------------

	public static DateDuration of(String spec) {
		try {
			PushbackReader reader = new PushbackReader(new StringReader(spec));
			int years = (int) ParseUtil.parseNonNegativeInteger(reader);
			Assert.equals('-', (char) reader.read());
			int months = (int) ParseUtil.parseNonNegativeInteger(reader);
			Assert.equals('-', (char) reader.read());
			int days = (int) ParseUtil.parseNonNegativeInteger(reader);
			return of(years, months, days);
		} catch (IOException e) {
			throw ExceptionFactory.getInstance().illegalArgument("Not a valid " + DateDuration.class.getSimpleName());
		}
	}

	public static DateDuration of(int years, int months, int days) {
		return new DateDuration(years, months, days);
	}

	// instance attributes -------------------------------------------------------------------------------------------

	private final int years;
	private final int months;
	private final int days;

	// constructor ---------------------------------------------------------------------------------------------------

	private DateDuration(int years, int months, int days) {
		this.years = years;
		this.months = months;
		this.days = days;
	}

	// properties ----------------------------------------------------------------------------------------------------

	public int getYears() {
		return years;
	}

	public int getMonths() {
		return months;
	}

	public int getDays() {
		return days;
	}

	// operational interface -----------------------------------------------------------------------------------------

	public int countBetween(LocalDate date1, LocalDate date2) {
		if (years > 0) {
			int result = (date2.getYear() - date1.getYear()) / years;
			LocalDate tmp = plusDurations(date1, result);
			while (tmp.isAfter(date2)) {
				result--;
				tmp = plusDurations(date1, result);
			}
			return result;
		} else if (months > 0) {
			int month1 = date1.getYear() * 12 + date1.getMonthValue();
			int month2 = date2.getYear() * 12 + date2.getMonthValue();
			int result = (month2 - month1) / months;
			LocalDate tmp = plusDurations(date1, result);
			while (tmp.isAfter(date2)) {
				result--;
				tmp = plusDurations(date1, result);
			}
			return result;
		} else if (days > 0) {
			return (int) (ChronoUnit.DAYS.between(date1, date2) / days);
		} else {
			throw ExceptionFactory.getInstance().illegalOperation(
				"countBetween() called on a zero-length " + this.getClass().getSimpleName());
		}
	}

	public LocalDate plusDurations(LocalDate baseDate, long count) {
		return baseDate.plusYears(count * years).plusMonths(count * months).plusDays(count * days);
	}

	// java.lang.Object overrides ------------------------------------------------------------------------------------

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DateDuration that = (DateDuration) o;
		return (this.years == that.years && this.months == that.months && this.days == that.days);
	}

	@Override
	public int hashCode() {
		return Objects.hash(years, months, days);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(getClass().getSimpleName()).append("{");
		if (years > 0) {
			result.append(HF.pluralize(years, "year"));
		}
		if (months > 0) {
			if (years > 0) {
				result.append(", ");
			}
			result.append(HF.pluralize(months, "month"));
		}
		if (days > 0) {
			if (years > 0 || months > 0) {
				result.append(", ");
			}
			result.append(HF.pluralize(days, "day", "days"));
		}
		result.append("}");
		return result.toString();
	}

}
