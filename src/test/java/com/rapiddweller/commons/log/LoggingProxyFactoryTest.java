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
package com.rapiddweller.commons.log;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link LoggingProxyFactory}.
 * Created at 17.09.2009 18:47:09
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class LoggingProxyFactoryTest {

	@Test
	public void test() {
		Calc proxy = LoggingProxyFactory.createProxy(Calc.class, new CalcImpl());
		assertEquals(3, proxy.add(1, 2));
	}
	
	// helpers ---------------------------------------------------------------------------------------------------------
	
	public static interface Calc {
		public int add(int a, int b);
	}
	
	public static class CalcImpl implements Calc {
        @Override
		public int add(int a, int b) {
	        return a + b;
        }
	}
	
}
