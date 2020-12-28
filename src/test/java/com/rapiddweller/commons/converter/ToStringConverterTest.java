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
package com.rapiddweller.commons.converter;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import com.rapiddweller.commons.LocaleUtil;
import org.junit.Test;

/**
 * Tests the {@link ToStringConverter}.
 * Created at 15.03.2009 20:32:19
 * @since 0.5.8
 * @author Volker Bergmann
 */

public class ToStringConverterTest extends AbstractConverterTest {

	public ToStringConverterTest() {
	    super(ToStringConverter.class);
    }

	@Test
	public void testEmpty() {
		assertEquals("", new ToStringConverter().convert(null));
		assertEquals("", new ToStringConverter().convert(""));
    }
	
	@Test
	public void testInteger() {
		assertEquals(   "0", new ToStringConverter().convert(0));
		assertEquals( "-11", new ToStringConverter().convert(-11));
		assertEquals("1000", new ToStringConverter().convert(1000));
    }
	
	@Test
	public void testDecimal_US() {
		LocaleUtil.runInLocale(Locale.US, () -> checkDecimalConversions());
	}
	
	@Test
	public void testDecimal_DE() {
		LocaleUtil.runInLocale(Locale.GERMANY, () -> checkDecimalConversions());
	}
	
	void checkDecimalConversions() {
        ToStringConverter converter = new ToStringConverter();
		// trim trailing zeros on default configuration
		assertEquals("0", converter.convert(0.));
		assertEquals("9876543210", converter.convert(9876543210.));
		// default should be US
		assertEquals("0.5", converter.convert(0.5));
		assertEquals("1000.5", converter.convert(1000.5));
		// decimal pattern
		converter.setDecimalPattern("0.00");
		assertEquals("0.50", converter.convert(0.5));
		// decimal separator
		converter.setDecimalSeparator(',');
		assertEquals("0,50", converter.convert(0.5));
		// grouping
		converter.setDecimalPattern("#,##0");
		assertEquals("1,000", converter.convert(1000.5));
		// grouping and decimal
		converter.setDecimalPattern("#,##0.00");
		converter.setGroupingSeparator('.');
		assertEquals("1.000,50", converter.convert(1000.5));
    }
}
