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

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

/**
 * Tests the Formatter.
 * Created: 23.12.2013 09:07:11
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class FormatterTest {
	
	@Test
	public void test_DE() {
		LocaleUtil.runInLocale(Locale.GERMAN, new Runnable() {
			@Override
			public void run() {
				assertEquals("morgen", Formatter.formatDaysFromNow(TimeUtil.tomorrow()));
				assertEquals("gestern", Formatter.formatDaysFromNow(TimeUtil.yesterday()));
				assertEquals("heute", Formatter.formatDaysFromNow(TimeUtil.today()));
				assertEquals("übermorgen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 2)));
				assertEquals("vorgestern", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -2)));
				assertEquals("vor 3 Tagen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -3)));
				assertEquals("in 3 Tagen", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 3)));
			}
		});
	}
	
	@Test
	public void test_EN() {
		LocaleUtil.runInLocale(Locale.ENGLISH, new Runnable() {
			@Override
			public void run() {
				assertEquals("tomorrow", Formatter.formatDaysFromNow(TimeUtil.tomorrow()));
				assertEquals("yesterday", Formatter.formatDaysFromNow(TimeUtil.yesterday()));
				assertEquals("today", Formatter.formatDaysFromNow(TimeUtil.today()));
				assertEquals("the day after tomorrow", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 2)));
				assertEquals("the day before yesterday", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -2)));
				assertEquals("3 days ago", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), -3)));
				assertEquals("in 3 days", Formatter.formatDaysFromNow(TimeUtil.addDays(TimeUtil.today(), 3)));
			}
		});
	}
	
}
