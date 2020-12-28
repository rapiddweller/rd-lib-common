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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.rapiddweller.SomeEnum;
import com.rapiddweller.commons.ConversionException;

/**
 * Tests the String2EnumConverter.
 * Created: 20.08.2007 07:14:04
 * @author Volker Bergmann
 */
public class String2EnumConverterTest extends AbstractConverterTest {

	public String2EnumConverterTest() {
	    super(String2EnumConverter.class);
    }

	@Test
    public void testNull() throws ConversionException {
        assertNull(String2EnumConverter.convert(null, SomeEnum.class));
    }

	@Test
    public void testNormal() throws ConversionException {
        for (SomeEnum instance : SomeEnum.values()) {
            check(instance);
        }
    }

	@Test(expected = ConversionException.class)
    public void testIllegalArgument() {
        String2EnumConverter.convert("0", SomeEnum.class);
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private static void check(SomeEnum instance) throws ConversionException {
        String2EnumConverter<SomeEnum> converter = new String2EnumConverter<>(SomeEnum.class);
        String name = instance.name();
        assertEquals(instance, converter.convert(name));
    }
    
}
