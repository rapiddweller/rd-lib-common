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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.ConversionException;

import java.util.Locale;

import com.rapiddweller.common.LocaleUtil;
import org.junit.Test;

/**
 * Tests the {@link ToStringConverter}.
 * Created at 15.03.2009 20:32:19
 *
 * @author Volker Bergmann
 * @since 0.5.8
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
        assertEquals("0", new ToStringConverter().convert(0));
        assertEquals("-11", new ToStringConverter().convert(-11));
        assertEquals("1000", new ToStringConverter().convert(1000));
    }

    @Test
    public void testDecimal_US() {
        LocaleUtil.runInLocale(Locale.US, this::checkDecimalConversions);
    }

    @Test
    public void testDecimal_DE() {
        LocaleUtil.runInLocale(Locale.GERMANY, this::checkDecimalConversions);
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

    @Test
    public void testConstructor() {
        ToStringConverter actualToStringConverter = new ToStringConverter();
        assertNull(actualToStringConverter.getStringQuote());
        assertNull(actualToStringConverter.decimalConverter);
        assertNull(actualToStringConverter.integralConverter);
        assertEquals("yyyy-MM-dd'T'HH:mm:ss", actualToStringConverter.getDateTimePattern());
        assertEquals("", actualToStringConverter.getNullString());
        assertEquals(Capitalization.mixed, actualToStringConverter.getDateTimeCapitalization());
        assertEquals("yyyy-MM-dd", actualToStringConverter.getDatePattern());
        assertEquals("HH:mm:ss", actualToStringConverter.getTimePattern());
        assertEquals(Capitalization.mixed, actualToStringConverter.getTimestampCapitalization());
        assertEquals("yyyy-MM-dd'T'HH:mm:ss.", actualToStringConverter.getTimestampPattern());
        assertNull(actualToStringConverter.getCharQuote());
        assertEquals(Capitalization.mixed, actualToStringConverter.getDateCapitalization());
    }

    @Test
    public void testConstructor2() {
        ToStringConverter actualToStringConverter = new ToStringConverter("Null String");
        assertNull(actualToStringConverter.getStringQuote());
        assertNull(actualToStringConverter.decimalConverter);
        assertNull(actualToStringConverter.integralConverter);
        assertEquals("yyyy-MM-dd'T'HH:mm:ss", actualToStringConverter.getDateTimePattern());
        assertEquals("Null String", actualToStringConverter.getNullString());
        assertEquals(Capitalization.mixed, actualToStringConverter.getDateTimeCapitalization());
        assertEquals("yyyy-MM-dd", actualToStringConverter.getDatePattern());
        assertEquals("HH:mm:ss", actualToStringConverter.getTimePattern());
        assertEquals(Capitalization.mixed, actualToStringConverter.getTimestampCapitalization());
        assertEquals("yyyy-MM-dd'T'HH:mm:ss.", actualToStringConverter.getTimestampPattern());
        assertNull(actualToStringConverter.getCharQuote());
        assertEquals(Capitalization.mixed, actualToStringConverter.getDateCapitalization());
    }

    @Test
    public void testConstructor3() {
        ToStringConverter actualToStringConverter = new ToStringConverter("Null String", "2020-03-01", "Timestamp Pattern");
        assertNull(actualToStringConverter.getStringQuote());
        assertNull(actualToStringConverter.decimalConverter);
        assertNull(actualToStringConverter.integralConverter);
        assertEquals("yyyy-MM-dd'T'HH:mm:ss", actualToStringConverter.getDateTimePattern());
        assertEquals("Null String", actualToStringConverter.getNullString());
        assertEquals(Capitalization.mixed, actualToStringConverter.getDateTimeCapitalization());
        assertEquals("2020-03-01", actualToStringConverter.getDatePattern());
        assertEquals("HH:mm:ss", actualToStringConverter.getTimePattern());
        assertEquals(Capitalization.mixed, actualToStringConverter.getTimestampCapitalization());
        assertEquals("Timestamp Pattern", actualToStringConverter.getTimestampPattern());
        assertNull(actualToStringConverter.getCharQuote());
        assertEquals(Capitalization.mixed, actualToStringConverter.getDateCapitalization());
    }

    @Test
    public void testCanConvert() {
        assertTrue((new ToStringConverter()).canConvert("sourceValue"));
    }

    @Test
    public void testConvert() throws ConversionException {
        assertEquals("source", (new ToStringConverter()).convert("source"));
        assertEquals("", (new ToStringConverter()).convert(null));
        assertEquals("0", (new ToStringConverter()).convert(0));
        assertEquals("source", ToStringConverter.<Object>convert("source", "Null String"));
        assertEquals("Null String", ToStringConverter.convert(null, "Null String"));
        assertEquals("0", ToStringConverter.<Object>convert(0, "Null String"));
    }

    @Test
    public void testConvert2() throws ConversionException {
        ToStringConverter toStringConverter = new ToStringConverter();
        toStringConverter.setStringQuote("String Quote");
        assertEquals("String QuotesourceString Quote", toStringConverter.convert("source"));
    }
}
