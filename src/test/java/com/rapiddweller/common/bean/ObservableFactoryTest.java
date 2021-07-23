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

package com.rapiddweller.common.bean;

import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the {@link ObservableFactory}.
 * Created at 17.07.2008 20:33:51
 *
 * @author Volker Bergmann
 * @since 0.4.5
 */
public class ObservableFactoryTest {

  @Test
  public void testToString() {
    String actualString = createAlice().toString();
    String expectedString = "com.rapiddweller.common.bean.IPerson{age=23, name=Alice}";
    String validAlternative = "com.rapiddweller.common.bean.IPerson{name=Alice, age=23}";
    if (!expectedString.equals(actualString) && !validAlternative.equals(actualString)) {
      fail("Expected a string like \"" + expectedString + "\" but found \"" + actualString + "\"");
    }
  }

  @Test
  public void testEquals() {
    assertTrue(createAlice().equals(createAlice()));
  }

  @Test
  public void testHashCode() {
    assertEquals(createAlice().hashCode(), createAlice().hashCode());
  }

  @Test
  public void testEvents() {
    IPerson person = createAlice();
    Listener listener = new Listener();
    person.addPropertyChangeListener("name", listener);
    person.setName("Joe");
    assertEquals("Joe", listener.getName());
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static IPerson createAlice() {
    IPerson person = ObservableFactory.create(IPerson.class);
    person.setName("Alice");
    person.setAge(23);
    return person;
  }

  public static class Listener implements PropertyChangeListener {

    private String name;

    public String getName() {
      return name;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      name = (String) evt.getNewValue();
    }

  }

}
