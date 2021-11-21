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

import com.rapiddweller.common.Accessor;
import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.exception.MutationFailedException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the {@link ConditionalMutator}.
 * Created at 02.05.2008 11:45:19
 *
 * @author Volker Bergmann
 * @since 0.4.3
 */
public class ConditionalMutatorTest {

  public static final int OVERWRITE = 1;
  public static final int SET_IF_UNDEFINED = 2;
  public static final int SET_IF_GREATER = 3;

  @Test
  public void testAssertEqualsSuccess() throws MutationFailedException {
    ConnectorMock connector = new ConnectorMock(1);
    ConditionalMutator mutator = createMutator(connector, ConditionalMutator.ASSERT_EQUALS);
    mutator.setValue(null, 1);
  }

  @Test
  public void testAssertEqualsFailure() throws MutationFailedException {
    try {
      ConnectorMock connector = new ConnectorMock(1);
      ConditionalMutator mutator = createMutator(connector,
          ConditionalMutator.ASSERT_EQUALS);
      mutator.setValue(null, 2);
      fail("Expected " + MutationFailedException.class.getSimpleName());
    } catch (MutationFailedException e) {
      // this is expected
    }
  }

  @Test
  public void testOverwrite() throws MutationFailedException {
    ConnectorMock connector = new ConnectorMock(1);
    ConditionalMutator mutator = createMutator(connector, ConditionalMutator.OVERWRITE);
    mutator.setValue(null, 2);
    assertEquals(2, (int) connector.value);
  }

  @Test
  public void testSetIfUndefinedTrue() throws MutationFailedException {
    ConnectorMock connector = new ConnectorMock(null);
    ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_UNDEFINED);
    mutator.setValue(null, 2);
    assertEquals(2, (int) connector.value);
  }

  @Test
  public void testSetIfUndefinedFalse() throws MutationFailedException {
    ConnectorMock connector = new ConnectorMock(1);
    ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_UNDEFINED);
    mutator.setValue(null, 2);
    assertEquals(1, (int) connector.value);
  }

  @Test
  public void testSetValueIfGreaterTrue() throws MutationFailedException {
    ConnectorMock connector = new ConnectorMock(1);
    ConditionalMutator mutator = createMutator(connector, ConditionalMutator.SET_IF_GREATER);
    mutator.setValue(null, 2);
    assertEquals(2, (int) connector.value);
  }

  @Test
  public void testSetValueIfGreaterFalse() throws MutationFailedException {
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
