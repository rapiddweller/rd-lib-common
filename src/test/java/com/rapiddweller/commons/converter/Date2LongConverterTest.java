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

import com.rapiddweller.commons.TimeUtil;

import org.junit.Test;

/**
 * Tests the {@link Date2LongConverter}.
 * Created at 05.01.2009 18:22:11
 * @since 0.4.7
 * @author Volker Bergmann
 */

public class Date2LongConverterTest extends AbstractConverterTest {

	public Date2LongConverterTest() {
	    super(Date2LongConverter.class);
    }

	@Test
	public void testConvert() {
		assertEquals(null, new Date2LongConverter().convert(null));
		assertEquals(0L, new Date2LongConverter().convert(TimeUtil.date(1970, 0, 1, 0, 0, 0, 0)).longValue());
	}

}
