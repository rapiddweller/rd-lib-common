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
package com.rapiddweller.commons.time;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.converter.PropertyResourceBundleConverter;
import com.rapiddweller.commons.converter.ThreadSafeConverter;
import static com.rapiddweller.commons.TimeUtil.*;

/**
 * Formats millisecond values in a rounded and for humans convenient form.
 * Created: 14.12.2010 13:39:18
 * @since 0.5.5
 * @author Volker Bergmann
 */
public class ElapsedTimeFormatter extends ThreadSafeConverter<Long, String> {
	
	private static final String UNIT_BUNDLE_NAME = ElapsedTimeFormatter.class.getPackage().getName() + ".timeUnits";
	
	private final char decimalSeparator;
	private final String space;
	private final boolean localUnits;
	
	private final PropertyResourceBundleConverter unitConverter;
	
	private static final ElapsedTimeFormatter DEFAULT_INSTANCE = new ElapsedTimeFormatter();

	public ElapsedTimeFormatter() {
		this(Locale.getDefault(), " ", true);
	}
	
	public ElapsedTimeFormatter(Locale locale, String space, boolean localUnits) {
		super(Long.class, String.class);
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
		this.decimalSeparator = symbols.getDecimalSeparator();
		this.space = space;
		this.localUnits = localUnits;
		this.unitConverter = new PropertyResourceBundleConverter(UNIT_BUNDLE_NAME, locale);
	}

	@Override
	public String convert(Long millis) throws ConversionException {
		if (millis < SECOND_MILLIS)
			return render(millis, 1, "ms");
		else if (millis < MINUTE_MILLIS)
			return render(millis, SECOND_MILLIS, "s");
		else if (millis < HOUR_MILLIS)
			return render(millis, MINUTE_MILLIS, "min");
		else if (millis < DAY_MILLIS)
			return render(millis, HOUR_MILLIS, "h");
		else
			return render(millis, DAY_MILLIS, "d");
	}
	
	public static String format(long millis) {
		return DEFAULT_INSTANCE.convert(millis);
	}
	
	// private helper --------------------------------------------------------------------------------------------------

	private String render(long millis, long base, String unitCode) {
		long prefix = millis / base;
		long postfix = (millis - prefix * base + base / 20) * 10 / base;
		if (postfix >= 10) {
			prefix++;
			postfix -= 10;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		if (postfix != 0 && prefix / 10 == 0)
			builder.append(decimalSeparator).append(postfix);
		builder.append(space);
		String unit = (localUnits ? unitConverter.convert(unitCode) : unitCode);
		builder.append(unit);
		return builder.toString();
	}

}
