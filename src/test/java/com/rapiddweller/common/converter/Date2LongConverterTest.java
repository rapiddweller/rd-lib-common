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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.TimeUtil;

import java.util.Date;
import java.util.SimpleTimeZone;

import org.junit.Test;

/**
 * Tests the {@link Date2LongConverter}.
 * Created at 05.01.2009 18:22:11
 *
 * @author Volker Bergmann
 * @since 0.4.7
 */

public class Date2LongConverterTest extends AbstractConverterTest {

    @Test
    public void testSetTimeZone() {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(1, "ID");
        Date2LongConverter date2LongConverter = new Date2LongConverter();
        date2LongConverter.setTimeZone(simpleTimeZone);
        assertSame(simpleTimeZone, date2LongConverter.getTimeZone());
    }

    public Date2LongConverterTest() {
        super(Date2LongConverter.class);
    }

    @Test
    public void testConvert() {
        assertNull(new Date2LongConverter().convert(null));
        assertEquals(0L, new Date2LongConverter().convert(TimeUtil.date(1970, 0, 1, 0, 0, 0, 0)).longValue());
        assertNull((new Date2LongConverter()).convert(null));
    }

    @Test
    public void testConvert2() throws ConversionException {
        Date sourceValue = new Date(1L);
        assertEquals(3600001L, (new Date2LongConverter()).convert(sourceValue).longValue());
    }

    @Test
    public void testConvert3() throws ConversionException {
        Date2LongConverter date2LongConverter = new Date2LongConverter();
        date2LongConverter.setTimeZone(null);
        assertNull(date2LongConverter.convert(null));
    }

}
