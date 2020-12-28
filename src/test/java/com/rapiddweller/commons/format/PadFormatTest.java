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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.rapiddweller.commons.NumberUtil;
import com.rapiddweller.commons.TimeUtil;
import com.rapiddweller.commons.format.Alignment;
import com.rapiddweller.commons.format.PadFormat;

/**
 * Tests the PadFormat.
 * Created: 29.06.2007 19:44:11
 * @since 0.1
 * @author Volker Bergmann
 */
public class PadFormatTest {

	@Test
    public void testFormatLeftAligned() {
        PadFormat format = new PadFormat("", 3, Alignment.LEFT, '0');
        assertEquals("100", format.format("1"));
        assertEquals("000", format.format("0"));
        assertEquals("000", format.format(null));
    }

	@Test
    public void testFormatRightAligned() {
        PadFormat format = new PadFormat("", 3, Alignment.RIGHT, '0');
        assertEquals("001", format.format("1"));
        assertEquals("000", format.format("0"));
        assertEquals("000", format.format(null));
        assertEquals("-01", format.format("-1"));
    }

	@Test
    public void testFormatCentered() {
        PadFormat format = new PadFormat("", 3, Alignment.CENTER, '0');
        assertEquals("010", format.format("1"));
        assertEquals("000", format.format("0"));
        assertEquals("000", format.format(null));
    }

	@Test
    public void testFormatNumber() {
        DecimalFormat numberFormat = new DecimalFormat("00.00", DecimalFormatSymbols.getInstance(Locale.US));
		PadFormat format = new PadFormat(numberFormat, "", 5, Alignment.RIGHT, ' ');
        assertEquals("00.01", format.format(0.01));
        assertEquals("00.10", format.format(0.1));
        assertEquals("01.00", format.format(1));
        assertEquals("01.00", format.format(1.));
        assertEquals("01.00", format.format(1));
        assertEquals("01.00", format.format(1L));
        assertEquals("     ", format.format(null));
    }

	@Test
    public void testFormatDate() {
        PadFormat format = new PadFormat(new SimpleDateFormat("yyyyMMdd", Locale.US), "", 8, Alignment.LEFT, ' ');
        assertEquals("20140325", format.format(TimeUtil.date(2014, 2, 25)));
        assertEquals("        ", format.format(null));
    }

	@Test
    public void testParseLeftAligned() throws ParseException {
        PadFormat format = new PadFormat("", 3, Alignment.LEFT, '0');
        assertEquals(null, format.parseObject(null));
        assertEquals("", format.parseObject(""));
        assertEquals("1", format.parseObject("1"));
        assertEquals("1", format.parseObject("100"));
        assertEquals("01", format.parseObject("0100"));
    }

	@Test
    public void testParseRightAligned() throws ParseException {
        PadFormat format = new PadFormat("", 3, Alignment.RIGHT, '0');
        assertEquals(null, format.parseObject(null));
        assertEquals("", format.parseObject(""));
        assertEquals("1", format.parseObject("1"));
        assertEquals("1", format.parseObject("001"));
        assertEquals("10", format.parseObject("0010"));
        assertEquals("-1", format.parseObject("-01"));
    }

    @Test
    public void testParseCentered() throws ParseException {
        PadFormat format = new PadFormat("", 3, Alignment.CENTER, '0');
        assertEquals(null, format.parseObject(null));
        assertEquals("", format.parseObject(""));
        assertEquals("1", format.parseObject("1"));
        assertEquals("1", format.parseObject("00100"));
    }

	@Test
    public void testEquals() {
		NumberFormat intFormat = NumberUtil.numberFormat(0, Locale.US);
		NumberFormat decFormat = NumberUtil.numberFormat(1, Locale.US);
    	PadFormat p10ls = new PadFormat(intFormat, "", 1, Alignment.LEFT, ' ');
       	PadFormat p20ls = new PadFormat(intFormat, "", 2, Alignment.LEFT, ' ');
    	PadFormat p11ls = new PadFormat(decFormat, "", 1, Alignment.LEFT, ' ');
    	PadFormat p10rs = new PadFormat(intFormat, "", 1, Alignment.RIGHT, ' ');
        PadFormat p10l0 = new PadFormat(intFormat, "", 1, Alignment.LEFT, '0');
        assertFalse(p10ls.equals(null));
        assertFalse(p10ls.equals("Test"));
        assertTrue(p10ls.equals(p10ls));
        assertTrue(p10ls.equals(new PadFormat(intFormat, "", 1, Alignment.LEFT, ' ')));
        assertFalse(p10ls.equals(p20ls));
        assertFalse(p10ls.equals(p11ls));
        assertFalse(p10ls.equals(p10rs));
        assertFalse(p10ls.equals(p10l0));
    }
	
}
