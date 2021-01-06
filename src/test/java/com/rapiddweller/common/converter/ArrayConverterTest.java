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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ConversionException;
import org.junit.Test;

/**
 * Tests the ArrayConverter.
 * Created: 20.07.2011 07:14:40
 *
 * @author Volker Bergmann
 * @since 0.5.9
 */
public class ArrayConverterTest {

    private static final Integer[] INT_1_3 = new Integer[]{1, 3};
    private static final Integer[] INT_2_4 = new Integer[]{2, 4};
    private static final String[] STRING_1_3 = new String[]{"1", "3"};

    private final IncrementConverter inc = new IncrementConverter();

    @Test(expected = NullPointerException.class)
    public void testConvert() throws ConversionException {
        Class<?> sourceComponentType = Object.class;
        assertEquals(3, (new ArrayConverter<Object, Object>((Class<Object>) sourceComponentType, Object.class, null))
                .convert(new Object[]{"foo", "foo", "foo"}).length);
    }

    @Test
    public void testConvert2() throws ConversionException {
        Class<?> sourceComponentType = Object.class;
        assertEquals(3, (new ArrayConverter<Object, Object>((Class<Object>) sourceComponentType, Object.class))
                .convert(new Object[]{"foo", "foo", "foo"}).length);
    }

    @Test
    public void testConvert3() throws ConversionException {
        Class<?> sourceComponentType = Object.class;
        assertThrows(IllegalArgumentException.class,
                () -> (new ArrayConverter<Object, Object>((Class<Object>) sourceComponentType, Object.class, null, null))
                        .convert(new Object[]{"foo", "foo", "foo"}));
    }


    @Test
    public void testConvertWith() {
        assertEqualArrays(INT_2_4, ArrayConverter.convertWith(inc, Integer.class, STRING_1_3));
        assertEquals(1,
                ArrayConverter.<Object, Object>convertWith(null, Object.class, new Object[]{"sourceValues"}).length);
        assertEquals(1,
                ArrayConverter.<Object, Object>convertWith(null, Object.class, new Object[]{"sourceValues"}).length);
    }

    @Test
    public void testConvertWith2() throws ConversionException {
        Class<?> componentType = Object.class;
        Object[] actualConvertWithResult = ArrayConverter.<Object, Object>convertWith(new ToCollectionConverter(),
                (Class<Object>) componentType, new Object[]{"sourceValues"});
        assertEquals(1, actualConvertWithResult.length);
        assertEquals(1, ((ArrayList) actualConvertWithResult[0]).size());
    }

    @Test
    public void testArrayTypeConversion() {
        @SuppressWarnings("unchecked")
        ArrayConverter<String, Integer> converter = new ArrayConverter<>(String.class, Integer.class);
        assertEqualArrays(INT_1_3, converter.convert(STRING_1_3));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testArrayElementConversion() {
        ArrayConverter<String, Integer> converter = new ArrayConverter<>(String.class, Integer.class, inc, inc);
        assertEqualArrays(INT_2_4, converter.convert(STRING_1_3));
    }

    private static void assertEqualArrays(Object[] array1, Object[] array2) {
        assertTrue("Expected [" + ArrayFormat.format(array1) + "] but was [" + ArrayFormat.format(array2) + "]",
                Arrays.equals(array1, array2));
    }

    public class IncrementConverter extends UnsafeConverter<String, Integer> {
        protected IncrementConverter() {
            super(String.class, Integer.class);
        }

        @Override
        public Integer convert(String sourceValue) throws ConversionException {
            return Integer.parseInt(sourceValue) + 1;
        }
    }

}
