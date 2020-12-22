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
package com.rapiddweller.commons.bean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.bean.PropertyAccessConverter;

/**
 * Tests the PropertyAccessConverter.
 * Created: 21.07.2007 16:35:28
 * @author Volker Bergmann
 */
public class PropertyAccessConverterTest {

	@Test
    public void test() throws ConversionException {
        Bean bean = new Bean(42, "foobar");
        PropertyAccessConverter numberExtractor = new PropertyAccessConverter("number");
        assertEquals(42, numberExtractor.convert(bean));
        PropertyAccessConverter textExtractor = new PropertyAccessConverter("text");
        assertEquals("foobar", textExtractor.convert(bean));
    }
    
}
