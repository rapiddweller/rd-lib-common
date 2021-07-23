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

import com.rapiddweller.common.UpdateFailedException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link PropertyGraphMutator}.
 * Created: 20.02.2007 08:52:49
 *
 * @author Volker Bergmann
 */
public class PropertyGraphMutatorTest {

  @Test
  public void testLocalProperty() throws UpdateFailedException {
    PropertyGraphMutator aNameMutator = new PropertyGraphMutator(ABean.class, "name", true, false);
    ABean a = new ABean();
    aNameMutator.setValue(a, "aName");
    assertEquals("aName", a.name);
    aNameMutator.setValue(a, null);
    assertEquals(null, a.name);
  }

  @Test
  public void testNavigatedProperty() throws UpdateFailedException {
    PropertyGraphMutator bNameMutator = new PropertyGraphMutator(ABean.class, "b.name", true, false);
    ABean a = new ABean();
    bNameMutator.setValue(a, "bName");
    assertEquals("bName", a.b.name);
    bNameMutator.setValue(a, null);
    assertEquals(null, a.b.name);
  }

  @Test
  public void testNavigatedGraph() throws UpdateFailedException {
    PropertyGraphMutator bNameMutator = new PropertyGraphMutator(ABean.class, "b.c.name", true, false);
    ABean a = new ABean();
    bNameMutator.setValue(a, "cName");
    assertEquals("cName", a.b.c.name);
    bNameMutator.setValue(a, null);
    assertEquals(null, a.b.c.name);
  }

}
