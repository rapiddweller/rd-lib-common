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

package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link Escalation} class.
 * Created at 29.04.2008 18:51:28
 *
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class EscalationTest {

  @Test
  public void testEquals() {
    Escalation e1 = new Escalation("mess", "orig", "cause");
    Escalation e2 = new Escalation("other message", "orig", "cause");
    Escalation e3 = new Escalation("mess", "other orig", "cause");
    Escalation e4 = new Escalation("mess", "orig", "other cause");
    Escalation e5 = new Escalation("mess", "orig", null);
    assertTrue(e1.equals(e1));
    assertFalse(e1.equals(null));
    assertFalse(e1.equals("bla"));
    assertFalse(e1.equals(e2));
    assertFalse(e1.equals(e3));
    assertTrue(e1.equals(e4));
    assertTrue(e1.equals(e5));
    assertTrue(e5.equals(e1));
  }

}
