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

import static org.junit.Assert.*;

import com.rapiddweller.common.ConversionException;

import java.sql.Timestamp;

import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

/**
 * Tests the {@link TimestampFormatter}.
 * Created: 18.02.2010 17:54:24
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class TimestampFormatterTest extends AbstractConverterTest {

    Timestamp timestamp = TimeUtil.timestamp(1971, 1, 3, 13, 14, 15, 123456789);

    @Test
    public void testConstructor() {
        TimestampFormatter actualTimestampFormatter = new TimestampFormatter();
        Class<String> expectedTargetType = actualTimestampFormatter.targetType;
        assertSame(expectedTargetType, actualTimestampFormatter.getTargetType());
        Class<Timestamp> expectedSourceType = actualTimestampFormatter.sourceType;
        assertSame(expectedSourceType, actualTimestampFormatter.getSourceType());
    }

    @Test
    public void testConstructor2() {
        TimestampFormatter actualTimestampFormatter = new TimestampFormatter("Pattern");
        Class<String> expectedTargetType = actualTimestampFormatter.targetType;
        assertSame(expectedTargetType, actualTimestampFormatter.getTargetType());
        Class<Timestamp> expectedSourceType = actualTimestampFormatter.sourceType;
        assertSame(expectedSourceType, actualTimestampFormatter.getSourceType());
    }

    @Test
    public void testConstructor3() {
        TimestampFormatter actualTimestampFormatter = new TimestampFormatter("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        Class<String> expectedTargetType = actualTimestampFormatter.targetType;
        assertSame(expectedTargetType, actualTimestampFormatter.getTargetType());
        Class<Timestamp> expectedSourceType = actualTimestampFormatter.sourceType;
        assertSame(expectedSourceType, actualTimestampFormatter.getSourceType());
    }

    @Test
    public void testConvert() throws ConversionException {
        Timestamp sourceValue = new Timestamp(10L);
        assertNotNull((new TimestampFormatter()).convert(sourceValue));
    }

    @Test
    public void testConvert2() throws ConversionException {
        assertNull((new TimestampFormatter()).convert(null));
    }

    public TimestampFormatterTest() {
        super(TimestampFormatter.class);
    }

    @Test
    public void testDefaultFormat() {
        assertNotNull(new TimestampFormatter().format(timestamp));
    }

    @Test
    public void testFormat() {
        Timestamp timestamp = new Timestamp(10L);
        assertNotNull((new TimestampFormatter()).format(timestamp));
    }

    @Test
    public void testFormat2() {
        assertNull((new TimestampFormatter()).format(null));
    }

    @Test
    public void testMillisFormat() {
        assertNotNull(new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS").format(timestamp));
    }

    @Test
    public void testCentisFormat() {
        assertNotNull(new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSS").format(timestamp));
    }

    @Test
    public void testNanosFormat() {
        assertNotNull(new TimestampFormatter("yyyy-MM-dd HH:mm:ss.SSSSSSSSS").format(timestamp));
    }

    @Test
    public void testSecondsFormat() {
        assertNotNull(new TimestampFormatter("yyyy-MM-dd HH:mm:ss").format(timestamp));
    }

    @Test
    public void testNull() {
        assertNull(new TimestampFormatter().format(null));
    }

}
