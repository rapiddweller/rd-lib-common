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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.rapiddweller.commons.ConfigurationError;
import org.junit.Test;

/**
 * Tests the {@link ClassCache}.
 * Created at 15.11.2008 17:16:09
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class ClassCacheTest {

	@Test
	public void testFqName() {
		ClassCache cache = new ClassCache();
		assertEquals(String.class, cache.forName("java.lang.String"));
	}

	@Test
	public void testDefaultPackage() {
		ClassCache cache = new ClassCache();
		assertEquals(String.class, cache.forName("String"));
	}

	@Test
	public void testUndefinedPackage() {
		try {
			ClassCache cache = new ClassCache();
			assertEquals(String.class, cache.forName("ClassCache"));
			fail("ConfigurationError expected");
		} catch (ConfigurationError e) {
			// that's the desired behavior
		}
	}

	@Test
	public void testCustomPackage1() {
		ClassCache cache = new ClassCache();
		cache.importPackage("com.rapiddweller.commons.bean");
		assertEquals(ClassCache.class, cache.forName("ClassCache"));
	}

	@Test
	public void testCustomPackage2() {
		ClassCache cache = new ClassCache();
		cache.importClass("com.rapiddweller.commons.bean.*");
		assertEquals(ClassCache.class, cache.forName("ClassCache"));
	}

	@Test
	public void testUndefinedImportClass() {
		try {
			ClassCache cache = new ClassCache();
			cache.importClass("com.rapiddweller.commons.bean");
		} catch (ConfigurationError e) {
			// that's the desired behavior
		}
	}

	@Test
	public void testCustomClass() {
		ClassCache cache = new ClassCache();
		cache.importClass("com.rapiddweller.commons.bean.ClassCache");
		assertEquals(ClassCache.class, cache.forName("ClassCache"));
	}

}
