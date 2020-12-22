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
import static org.junit.Assert.assertNull;

import com.rapiddweller.SomeEnum;
import com.rapiddweller.commons.ConversionException;
import org.junit.Test;

/**
 * Tests the {@link Enum2StringConverter}.
 * Created: 25.02.2010 23:55:44
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Enum2StringConverterTest extends AbstractConverterTest {

	public Enum2StringConverterTest() {
	    super(Enum2StringConverter.class);
    }

	@Test
    public void testNull() throws ConversionException {
        assertNull(Enum2StringConverter.convertToString(null));
    }

	@Test
    public void testNormal() throws ConversionException {
        for (SomeEnum instance : SomeEnum.values()) {
            check(instance);
        }
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private static void check(SomeEnum instance) throws ConversionException {
    	Enum2StringConverter<SomeEnum> converter = new Enum2StringConverter<SomeEnum>(SomeEnum.class);
        assertEquals(instance.name(), converter.convert(instance));
    }
    
}
