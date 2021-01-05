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
package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

/**
 * Tests the {@link Long2DateConverter}.
 * Created: 26.02.2010 08:30:14
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Long2DateConverterTest extends AbstractConverterTest {

	public Long2DateConverterTest() {
	    super(Long2DateConverter.class);
    }

	@Test
	public void testRevert() {
		assertNull(new Long2DateConverter().convert(null));
		new Long2DateConverter().convert(0L);
	}
	
}
