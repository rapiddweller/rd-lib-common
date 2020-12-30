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

import java.util.Locale;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

/**
 * Tests the {@link Locale2StringConverter}.
 * Created: 25.02.2010 23:43:52
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Locale2StringConverterTest extends AbstractConverterTest {

	public Locale2StringConverterTest() {
	    super(Locale2StringConverter.class);
    }

	@Test
    public void test() throws ConversionException {
		Locale2StringConverter converter = new Locale2StringConverter();
        assertEquals("de", converter.convert(Locale.GERMAN));
        assertEquals("de_DE", converter.convert(Locale.GERMANY));
    }
	
}
