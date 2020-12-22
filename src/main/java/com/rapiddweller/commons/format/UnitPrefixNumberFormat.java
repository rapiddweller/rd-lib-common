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

package com.rapiddweller.commons.format;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Formats numbers using unit prefixes for abbreviation.<br><br>
 * Created: 26.03.2019 08:01:52
 * @since 1.0.12
 * @author Volker Bergmann
 * @see <a href="https://en.wikipedia.org/wiki/Unit_prefix">https://en.wikipedia.org/wiki/Unit_prefix</a>
 */

public class UnitPrefixNumberFormat {
	
	private static final String DEFAULT_PATTERN = "#,##0.####";
	
	private NumberFormat numberFormat;
	
	public UnitPrefixNumberFormat() {
		this(DEFAULT_PATTERN);
	}
	
	public UnitPrefixNumberFormat(String pattern) {
		this.numberFormat = new DecimalFormat(pattern);
	}
	
	public String format(double number) {
		if (number % 1000000 == 0)
			return numberFormat.format(number / 1000000) + "M";
		else if (number % 1000 == 0)
			return numberFormat.format(number / 1000) + "K";
		else
			return numberFormat.format(number);
	}
	
}
