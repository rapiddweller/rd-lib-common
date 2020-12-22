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
package com.rapiddweller.commons;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import com.rapiddweller.commons.collection.MapEntry;

import org.junit.Test;

/**
 * Tests the {@link CompositeFormatter}.
 * Created at 02.05.2008 12:08:43
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class CompositeFormatterTest {
	
	private CompositeFormatter formatter = new CompositeFormatter();
	
	@Test
	public void testRenderNullComponent() {
		checkRendering("name=[null]", "name", null);
	}

	@Test
	public void testRenderDateComponent() {
		TimeZone timeZone = TimeZone.getDefault();
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
			checkRendering("date=1970-01-01", "date", TimeUtil.date(1970, 0, 1));
			checkRendering("date=2001-03-04", "date", TimeUtil.date(2001, 2, 4));
			TimeZone.setDefault(TimeZone.getTimeZone("CET"));
			checkRendering("date=1970-01-01", "date", TimeUtil.date(1970, 0, 1));
			checkRendering("date=2001-03-04", "date", TimeUtil.date(2001, 2, 4));
			TimeZone.setDefault(TimeZone.getTimeZone("PST"));
			checkRendering("date=1970-01-01", "date", TimeUtil.date(1970, 0, 1));
			checkRendering("date=2001-03-04", "date", TimeUtil.date(2001, 2, 4));
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
			checkRendering("date=1970-01-01", "date", TimeUtil.date(1970, 0, 1));
			checkRendering("date=2001-03-04", "date", TimeUtil.date(2001, 2, 4));
		} finally {
			TimeZone.setDefault(timeZone);
		}
	}

	@Test
	public void testRenderTimeComponent() {
		checkRendering("time=1970-01-01T01:02:03", "time", TimeUtil.date(1970, 0, 1, 1, 2, 3, 0));
	}

	@Test
	public void testRenderArray() {
		checkRendering("array=[1, 2, 3]", "array", ArrayUtil.toArray(1, 2, 3));
	}
	
	// private helpers -------------------------------------------------------------------------------------------------

	private void checkRendering(String expected, String name, Object value) {
		StringBuilder builder = new StringBuilder();
		formatter.renderComponent(builder, "", new MapEntry<String, Object>(name, value));
		assertEquals(expected, builder.toString());
	}
	
}
