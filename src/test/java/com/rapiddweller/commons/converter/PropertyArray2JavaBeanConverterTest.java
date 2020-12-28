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

import com.rapiddweller.commons.converter.PropertyArray2JavaBeanConverter;
import com.rapiddweller.commons.converter.util.ClassProvider;
import com.rapiddweller.commons.converter.util.ConstantClassProvider;
import com.rapiddweller.commons.converter.util.ReferenceResolver;
import org.junit.Test;

/**
 * Tests the {@link PropertyArray2JavaBeanConverter}.
 * Created: 28.11.2014 19:18:27
 * @since 1.0.1
 * @author Volker Bergmann
 */

public class PropertyArray2JavaBeanConverterTest {

	@Test
	public void test() {
		ClassProvider<Object> provider = new ConstantClassProvider<>(A.class);
		ReferenceResolver referenceResolver = (value, target, featureName) -> value;
		PropertyArray2JavaBeanConverter converter = new PropertyArray2JavaBeanConverter(provider, new String[] {"name", "b.x", "b.y", "b.c.x", "b.c.y" }, referenceResolver);
		A result = (A) converter.convert(new Object[] { "theName", "xVal", "yVal", "cxVal", "cyVal" });
		assertEquals("theName", result.name);
		assertEquals("xVal", result.b.x);
		assertEquals("yVal", result.b.y);
		assertEquals("cxVal", result.b.c.x);
		assertEquals("cyVal", result.b.c.y);
	}

	public static final class A {
		public String name;
		public B b;
		public void setB(B b) {
			this.b = b;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	public static final class B {
		public String x;
		public String y;
		public C c;
		public void setX(String x) {
			this.x = x;
		}
		public void setY(String y) {
			this.y = y;
		}
		public void setC(C c) {
			this.c = c;
		}
	}

	public static final class C {
		public String x;
		public String y;
		public void setX(String x) {
			this.x = x;
		}
		public void setY(String y) {
			this.y = y;
		}
	}

}
