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

package com.rapiddweller.common.debug;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ResourceMonitor}.
 * Created: 14.04.2011 17:25:03
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class ResourceMonitorTest {

  @Test
  public void testAccess() {
    ResourceMonitor monitor = new ResourceMonitor();
    Object x = new Object();
    monitor.register(x);
    assertEquals(1, monitor.getRegistrations().size());
    monitor.unregister(x);
    assertEquals(0, monitor.getRegistrations().size());
  }

  @Test
  public void testAssert_non_critical() {
    ResourceMonitor monitor = new ResourceMonitor();
    Object x = new Object();
    monitor.register(x);
    monitor.assertNoRegistrations(false);
  }

  @Test(expected = IllegalStateException.class)
  public void testAssert_critical() {
    ResourceMonitor monitor = new ResourceMonitor();
    Object x = new Object();
    monitor.register(x);
    monitor.assertNoRegistrations(true);
  }

}
