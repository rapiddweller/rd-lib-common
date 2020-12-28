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
package com.rapiddweller.commons.debug;

import static org.junit.Assert.*;

import com.rapiddweller.commons.SysUtil;
import org.junit.Test;

/**
 * Tests the {@link Debug} class.
 * Created: 06.06.2011 11:00:28
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class DebugTest {

	@Test
	public void testInactive() {
		assertFalse(Debug.active());
	}
	
	@Test
	public void testActive() {
		System.setProperty("xyz", "");
		SysUtil.runWithSystemProperty("debug", "", () -> assertTrue(Debug.active()));
	}
	
}
