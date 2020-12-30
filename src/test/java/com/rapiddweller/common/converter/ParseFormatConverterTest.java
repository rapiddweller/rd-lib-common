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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.TimeUtil;
import com.rapiddweller.common.converter.ParseFormatConverter;

/**
 * Tests the {@link ParseFormatConverter}.
 * Created: 29.09.2006 15:55:35
 * @since 0.1
 * @author Volker Bergmann
 */
public class ParseFormatConverterTest extends AbstractConverterTest {

	public ParseFormatConverterTest() {
	    super(ParseFormatConverter.class);
    }

	@Test
    public void testIntegerConversion() throws ConversionException {
        ParseFormatConverter<Long> converter = new ParseFormatConverter<>(Long.class, NumberFormat.getInstance(), false);
        assertNull(converter.convert(null));
        assertEquals( 1L, (long)converter.convert( "1"));
        assertEquals( 0L, (long)converter.convert( "0"));
        assertEquals(-1L, (long)converter.convert("-1"));
    }

	@Test
    public void testDateConversion() throws ConversionException {
        ParseFormatConverter<Date> converter = new ParseFormatConverter<>(Date.class, new SimpleDateFormat("yyyy-MM-dd"), false);
        assertNull(converter.convert(null));
        assertEquals(TimeUtil.date(1969,  5, 24), converter.convert("1969-06-24"));
        assertEquals(TimeUtil.date(1970,  0,  1), converter.convert("1970-01-01"));
        assertEquals(TimeUtil.date(2000, 11, 31), converter.convert("2000-12-31"));
        assertEquals(TimeUtil.date(2006,  8, 29), converter.convert("2006-09-29"));
    }
	
}
