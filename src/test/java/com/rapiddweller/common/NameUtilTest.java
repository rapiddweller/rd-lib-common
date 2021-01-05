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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.filter.AcceptAllFilter;
import com.rapiddweller.common.filter.OrFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Tests the {@link NameUtil} class.
 * Created: 12.08.2010 11:47:03
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class NameUtilTest {

    Named AB = new X("AB");
    Named A_B = new X("A_B");
    Named AC = new X("AC");
    Named A_C = new X("A_C");

    @Test
    public void testGetNames() {
        assertTrue(NameUtil.<java.util.Collection<? extends Named>>getNames(new ArrayList<>()).isEmpty());
        assertEquals(1, NameUtil.getNames(new Named[]{new CharSet()}).length);
        assertTrue(NameUtil.<java.util.Collection<? extends Named>>getNames(new ArrayList<>()).isEmpty());
        assertEquals(1, NameUtil.getNames(new Named[]{new CharSet()}).length);
    }

    @Test
    public void testGetNamesAsArray() {
        assertEquals(0,
                NameUtil.<java.util.Collection<? extends Named>>getNamesAsArray(new ArrayList<>()).length);
        assertEquals(0,
                NameUtil.<java.util.Collection<? extends Named>>getNamesAsArray(new ArrayList<>()).length);
    }

    @Test
    public void testOrderByName() {
        Named[] array = new Named[]{A_C, AC, A_B, AB};
        NameUtil.orderByName(array);
        Named[] expected = new Named[]{A_B, A_C, AB, AC};
        assertTrue(Arrays.equals(expected, array));
    }

    @Test
    public void testOrderByName2() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        NameUtil.<Named>orderByName(namedList);
        assertTrue(namedList.isEmpty());
    }

    @Test
    public void testOrderByName3() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        NameUtil.<Named>orderByName(namedList);
        assertTrue(namedList.isEmpty());
    }

    @Test
    public void testIndexOf_list() {
        List<Named> list = CollectionUtil.toList(A_C, AC, A_B, AB);
        assertEquals(-1, NameUtil.indexOf("XY", list));
        assertEquals(0, NameUtil.indexOf("A_C", list));
        assertEquals(3, NameUtil.indexOf("AB", list));
    }

    @Test
    public void testIndexOf() {
        assertEquals(-1, NameUtil.indexOf("Name", new ArrayList<>()));
        assertEquals(-1, NameUtil.indexOf("Name", new Named[]{new CharSet()}));
        assertEquals(0, NameUtil.indexOf("Name", new Named[]{new CharSet("Name", 'A', 'A')}));
        assertEquals(-1, NameUtil.indexOf("Name", new ArrayList<>()));
        assertEquals(-1, NameUtil.indexOf("Name", new Named[]{new CharSet()}));
        assertEquals(0, NameUtil.indexOf("Name", new Named[]{new CharSet("Name", 'A', 'A')}));
    }

    @Test
    public void testIndexOf_array() {
        Named[] list = new Named[]{A_C, AC, A_B, AB};
        assertEquals(-1, NameUtil.indexOf("XY", list));
        assertEquals(0, NameUtil.indexOf("A_C", list));
        assertEquals(3, NameUtil.indexOf("AB", list));
    }

    @Test
    public void testFind() {
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        OrFilter<String> filter = new OrFilter<String>(orFilter, orFilter1, new OrFilter<String>(null, null, null));
        assertTrue(NameUtil.<Named>find(new ArrayList<Named>(), filter).isEmpty());
    }

    @Test
    public void testFind2() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(new CharSet());
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        assertEquals(1,
                NameUtil.<Named>find(namedList, new OrFilter<String>(new AcceptAllFilter<>(), orFilter, orFilter1))
                        .size());
    }

    @Test
    public void testFind3() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(new CharSet());
        assertTrue(NameUtil.<Named>find(namedList, new OrFilter<String>()).isEmpty());
    }

    @Test
    public void testFind4() {
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        OrFilter<String> filter = new OrFilter<String>(orFilter, orFilter1, new OrFilter<String>(null, null, null));
        assertTrue(NameUtil.<Named>find(new ArrayList<Named>(), filter).isEmpty());
    }

    @Test
    public void testFind5() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(new CharSet());
        OrFilter<String> orFilter = new OrFilter<String>(null, null, null);
        OrFilter<String> orFilter1 = new OrFilter<String>(null, null, null);
        assertEquals(1,
                NameUtil.<Named>find(namedList, new OrFilter<>(new AcceptAllFilter<>(), orFilter, orFilter1))
                        .size());
    }

    @Test
    public void testFind6() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(new CharSet());
        assertTrue(NameUtil.<Named>find(namedList, new OrFilter<>()).isEmpty());
    }

    @Test
    public void testFindByName() {
        assertNull(NameUtil.<Named>findByName("Name", new ArrayList<>()));
        assertNull(NameUtil.<Named>findByName("Name", new Named[]{new CharSet()}));
        assertNull(NameUtil.<Named>findByName("Name", new ArrayList<>()));
        assertNull(NameUtil.<Named>findByName("Name", new Named[]{new CharSet()}));
    }

    @Test
    public void testFindByName2() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(new CharSet());
        assertNull(NameUtil.<Named>findByName("Name", namedList));
    }

    @Test
    public void testFindByName3() {
        Person person = new Person("Name");
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(person);
        assertSame(person, NameUtil.<Named>findByName("Name", namedList));
    }

    @Test
    public void testFindByName4() {
        CharSet charSet = new CharSet("Name", 'A', 'A');
        assertSame(charSet, NameUtil.<Named>findByName("Name", new Named[]{charSet}));
    }

    @Test
    public void testFindByName5() {
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(new CharSet());
        assertNull(NameUtil.<Named>findByName("Name", namedList));
    }

    @Test
    public void testFindByName6() {
        Person person = new Person("Name");
        ArrayList<Named> namedList = new ArrayList<Named>();
        namedList.add(person);
        assertSame(person, NameUtil.<Named>findByName("Name", namedList));
    }

    @Test
    public void testFindByName7() {
        CharSet charSet = new CharSet("Name", 'A', 'A');
        assertSame(charSet, NameUtil.<Named>findByName("Name", new Named[]{charSet}));
    }

    private static final class X implements Named {

        private final String name;

        public X(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
