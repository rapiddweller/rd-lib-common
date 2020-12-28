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

import static org.junit.Assert.*;

import com.rapiddweller.commons.ConversionException;
import org.junit.Test;

/**
 * Tests the {@link ConverterChain} class.
 * Created: 10.01.2011 12:08:00
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class ConverterChainTest {

	@Test
	public void testClone() {
		ConverterChain<Object, Object> chain = new ConverterChain<>(new SubCon(), new SubCon());
		chain.clone();
		String x = "x";
		assertEquals(x, chain.convert(x));
	}

	public static class SubCon extends ThreadSafeConverter<Object, Object> {
		protected SubCon() {
			super(Object.class, Object.class);
		}

		@Override
		public Object convert(Object sourceValue) throws ConversionException {
			return sourceValue;
		}
	}
	
}
