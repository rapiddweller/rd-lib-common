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

import org.junit.Test;

/**
 * Tests the {@link PrintfConverter}.
 * Created at 20.07.2009 07:30:34
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class PrintfConverterTest extends AbstractConverterTest {
	
	public PrintfConverterTest() {
	    super(PrintfConverter.class);
    }

	@Test
	public void test() {
		PrintfConverter converter = new PrintfConverter("(%1$s)");
		assertEquals("(X)", converter.convert("X"));
	}
	
}
