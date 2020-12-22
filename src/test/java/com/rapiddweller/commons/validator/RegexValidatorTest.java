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
package com.rapiddweller.commons.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.constraints.Pattern;

import org.junit.Test;

/**
 * Tests the {@link RegexValidator}.
 * Created at 15.07.2009 15:51:36
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class RegexValidatorTest {

	@Test
	public void testDefault() {
		RegexValidator validator = new RegexValidator("[0-9]");
		assertTrue(validator.valid("3"));
		assertFalse(validator.valid("a"));
		assertFalse(validator.valid("a"));
	}
	
	@Test
	public void testBeanSetup() {
		RegexValidator validator = new RegexValidator();
		validator.setRegexp("[0-9]");
		assertTrue(validator.valid("3"));
		assertFalse(validator.valid("a"));
	}
	
	@Test
	public void testCommentFlag() {
		RegexValidator validator = new RegexValidator(" [0-9] # comment", Pattern.Flag.COMMENTS);
		assertTrue(validator.valid("3"));
		assertFalse(validator.valid("a"));
	}
	
}
