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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the Period class.
 * Created at 02.05.2008 15:39:31
 *
 * @author Volker Bergmann
 * @since 0.4.3
 */
public class PeriodTest {

    @Test
    public void testGetInstances() {
        assertEquals(9, Period.getInstances().size());
    }

    @Test
    public void testEquals() {
        Period second = Period.SECOND;
        assertFalse(second.equals(null));
        assertFalse(second.equals(""));
        assertTrue(second.equals(second));
        assertFalse(second.equals(Period.MINUTE));
    }

    @Test
    public void testMinInstance() {
        Period actualMinInstanceResult = Period.minInstance();
        assertSame(Period.MILLISECOND, actualMinInstanceResult);
    }

    @Test
    public void testMaxInstance() {
        Period actualMaxInstanceResult = Period.maxInstance();
        assertSame(Period.YEAR, actualMaxInstanceResult);
    }
}
