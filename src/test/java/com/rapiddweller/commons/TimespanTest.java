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
package com.rapiddweller.commons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import com.rapiddweller.commons.Timespan;
import com.rapiddweller.commons.TimeUtil;

/**
 * Tests the {@link Timespan} class.
 * Created: 17.02.2005 21:29:24
 * @author Volker Bergmann
 */
public class TimespanTest {

	@Test
    public void testDuration() {
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + Period.DAY.getMillis());
        Timespan timespan = new Timespan(now, tomorrow);
        assertEquals((Long)Period.DAY.getMillis(), timespan.duration());
    }

	@Test
    public void testContains() {
        Date d1 = TimeUtil.date(2005, 0, 1);
        Date d2 = TimeUtil.date(2005, 0, 2);
        Date d3 = TimeUtil.date(2005, 0, 3);
        Date d4 = TimeUtil.date(2005, 0, 4);

        Timespan l  = new Timespan(d1, d4);
        Timespan m1 = new Timespan(d1, d3);
        Timespan m2 = new Timespan(d2, d4);
        Timespan s2 = new Timespan(d2, d3);

        assertTrue(l.contains(l));
        assertTrue(l.contains(m1));
        assertTrue(l.contains(m2));
        assertTrue(l.contains(s2));
        assertFalse(m1.contains(l));
        assertFalse(m2.contains(l));
        assertFalse(s2.contains(l));
    }

	@Test
    public void testOverlaps() {
        Date d1 = TimeUtil.date(2005, 0, 1);
        Date d2 = TimeUtil.date(2005, 0, 2);
        Date d3 = TimeUtil.date(2005, 0, 3);
        Date d4 = TimeUtil.date(2005, 0, 4);

        Timespan l  = new Timespan(d1, d4);
        Timespan m1 = new Timespan(d1, d3);
        Timespan m2 = new Timespan(d2, d4);
        Timespan s1 = new Timespan(d1, d2);
        Timespan s2 = new Timespan(d2, d3);
        Timespan s3 = new Timespan(d3, d4);

        assertTrue(l.overlaps(l));
        assertTrue(l.overlaps(m1));
        assertTrue(l.overlaps(m2));
        assertTrue(l.overlaps(s2));
        assertTrue(m1.overlaps(l));
        assertTrue(m2.overlaps(l));
        assertTrue(s2.overlaps(l));
        assertTrue(m1.overlaps(m2));
        assertTrue(m2.overlaps(m1));
        assertFalse(s1.overlaps(s2));
        assertFalse(s2.overlaps(s1));
        assertFalse(s1.overlaps(s3));
        assertFalse(s3.overlaps(s1));
    }
    
	@Test
    public void testUnite() {
        Date d1 = TimeUtil.date(2005, 0, 1);
        Date d2 = TimeUtil.date(2005, 0, 2);
        Date d3 = TimeUtil.date(2005, 0, 3);
        Date d4 = TimeUtil.date(2005, 0, 4);
        Timespan t12 = new Timespan(d1, d2);
        Timespan t34 = new Timespan(d3, d4);
        Timespan t14 = new Timespan(d1, d4);
        assertEquals(t14, Timespan.unite(t14, t14));
        assertEquals(t14, Timespan.unite(t12, t34));
        Date now = new Date();
        Timespan future = new Timespan(now, null);
        Timespan past = new Timespan(null, now);
        Timespan always = new Timespan(null, null);
        assertEquals(always, Timespan.unite(past, future));
    }
    
	@Test
    public void testEquals() {
    	Timespan always = new Timespan(null, null);
    	assertFalse(always.equals(null));
    	assertFalse(always.equals(""));
    	assertTrue(always.equals(always));
    	Date now = new Date();
    	Timespan future = new Timespan(now, null);
    	assertFalse(always.equals(future));
    	assertFalse(future.equals(always));
    }
	
}

