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

package com.rapiddweller.commons.collection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testing {@link RingBuffer}.<br><br>
 * Created: 27.10.2019 18:52:51
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class RingBufferTest {

	@Test
	public void testGet() {
		RingBuffer<Integer> buffer = new RingBuffer<>(Integer.class, 3);
		buffer.add(1);
		assertEquals(1, (int) buffer.get(0));
		buffer.add(2);
		assertEquals(2, (int) buffer.get(0));
		assertEquals(1, (int) buffer.get(1));
		buffer.add(3);
		assertEquals(3, (int) buffer.get(0));
		assertEquals(2, (int) buffer.get(1));
		assertEquals(1, (int) buffer.get(2));
		buffer.add(4);
		assertEquals(4, (int) buffer.get(0));
		assertEquals(3, (int) buffer.get(1));
		assertEquals(2, (int) buffer.get(2));
	}
	
}
