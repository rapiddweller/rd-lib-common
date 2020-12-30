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
package com.rapiddweller.common.validator;

import static org.junit.Assert.*;

import com.rapiddweller.common.CharSet;
import org.junit.Test;

/**
 * Tests the {@link CharacterValidator}.
 * Created: 17.12.2009 19:03:03
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class CharacterValidatorTest {

	@Test
	public void test() {
		CharacterValidator validator = new CharacterValidator(new CharSet('0', '9'));
		assertFalse(validator.valid('a'));
		assertTrue(validator.valid('0'));
		assertTrue(validator.valid('9'));
		assertFalse(validator.valid('X'));
		assertFalse(validator.valid('-'));
	}
	
}
