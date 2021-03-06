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
package com.rapiddweller.common.mutator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.rapiddweller.common.Accessor;
import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.UpdateFailedException;

import org.junit.Test;

/**
 * Tests the {@link ConditionalMutator}.
 * Created at 02.05.2008 11:45:19
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class ConditionalMutatorTest {

    public static final int OVERWRITE        = 1;
    public static final int SET_IF_UNDEFINED = 2;
    public static final int SET_IF_GREATER   = 3;

	@Test
	public void testAssertEqualsSuccess() throws UpdateFailedException {
		ConnectorMock connector = new ConnectorMock(1);
		ConditionalMutator mutator = createMutator(connector, ConditionalMutator.ASSERT_EQUALS);
		mutator.setValue(null, 1);
	}

	@Test
	public void testAssertEqualsFailure() throws UpdateFailedException {
		try {
			ConnectorMock connector = new ConnectorMock(1);
			ConditionalMutator mutator = createMutator(connector,
					ConditionalMutator.ASSERT_EQUALS);
			mutator.setValue(null, 2);
			fail("Expected " + UpdateFailedException.class.getSimpleName());
		} catch (UpdateFailedException e) {
			// this is expected
		}
	}

	@Test
	public void testOverwrite() throws UpdateFailedException {
		ConnectorMock connector = new ConnectorMock(1);
		ConditionalMutator mutator = createMutator(connector, ConditionalMutator.OVERWRITE);
		mutator.setValue(null, 2);
		assertEquals(2, (int) connector.value);
	}

	@Test
	public void testSetIfUndefinedTrue() throws UpdateFailedException {
		ConnectorMock connector = new ConnectorMock(null);
		ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_UNDEFINED);
		mutator.setValue(null, 2);
		assertEquals(2, (int) connector.value);
	}

	@Test
	public void testSetIfUndefinedFalse() throws UpdateFailedException {
		ConnectorMock connector = new ConnectorMock(1);
		ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_UNDEFINED);
		mutator.setValue(null, 2);
		assertEquals(1, (int) connector.value);
	}

	@Test
	public void testSetValueIfGreaterTrue() throws UpdateFailedException {
		ConnectorMock connector = new ConnectorMock(1);
		ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_GREATER);
		mutator.setValue(null, 2);
		assertEquals(2, (int) connector.value);
	}
	
	@Test
	public void testSetValueIfGreaterFalse() throws UpdateFailedException {
		ConnectorMock connector = new ConnectorMock(1);
		ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_GREATER);
		mutator.setValue(null, 0);
		assertEquals(1, (int) connector.value);
	}
	
	// helper code -----------------------------------------------------------------------------------------------------
	
	public static class ConnectorMock implements Mutator, Accessor<Object, Integer> {
		
		public Integer value;

		public ConnectorMock(Integer value) {
			this.value = value;
		}
		
		@Override
		public void setValue(Object target, Object value) {
			this.value = (Integer) value;
		}

		@Override
		public Integer getValue(Object target) {
			return value;
		}
		
	}
	
	private static ConditionalMutator createMutator(ConnectorMock connector, int mode) {
		return new ConditionalMutator(connector, connector, mode);
	}

}
