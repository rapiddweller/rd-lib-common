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
package com.rapiddweller.commons.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;

import com.rapiddweller.commons.Parser;

/**
 * {@link Parser} implementation which parses {@link Integer}s.
 * Created: 10.03.2011 19:41:30
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class IntParser extends Parser<Integer> {

	DecimalFormat fmt;
	
	public IntParser() {
		fmt = new DecimalFormat();
		fmt.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
		fmt.setGroupingUsed(false);
	}
	
	@Override
	public Integer parseObject(String text, ParsePosition pos) {
		return ((Number) fmt.parseObject(text, pos)).intValue();
	}
	
}
