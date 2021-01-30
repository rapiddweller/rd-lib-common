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
package com.rapiddweller.common.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.condition.EqualsCondition;

import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.Filter;

import org.junit.Test;

/**
 * Tests the {@link FilterUtil} class.
 * Created at 04.05.2008 10:08:18
 *
 * @author Volker Bergmann
 * @since 0.5.3
 */
public class FilterUtilTest {

    @Test
    public void testMultiFilter() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertTrue(
                FilterUtil
                        .multiFilter(new ArrayList<>(),
                                new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null)))
                        .isEmpty());
    }

    @Test
    public void testMultiFilter2() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertEquals(1,
                FilterUtil
                        .multiFilter(objectList, new OrFilter<>(new AcceptAllFilter<>(), orFilter, orFilter1))
                        .size());
    }

    @Test
    public void testMultiFilter3() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        assertTrue(FilterUtil.multiFilter(objectList, new OrFilter<>()).isEmpty());
    }

    @Test
    public void testFilter() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        OrFilter<Object> filter = new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null));
        assertTrue(FilterUtil.filter(new ArrayList<>(), filter).isEmpty());
    }

    @Test
    public void testFilter2() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertEquals(1,
                FilterUtil.filter(objectList, new OrFilter<>(new AcceptAllFilter<>(), orFilter, orFilter1))
                        .size());
    }

    @Test
    public void testFilter3() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        assertTrue(FilterUtil.filter(objectList, new OrFilter<>()).isEmpty());
    }

    @Test
    public void testFilter4() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        assertEquals(1, FilterUtil.filter(objectList, null).size());
    }

    @Test
    public void testFilter5() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> FilterUtil.filter(new Object[]{},
                new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null))));
    }

    @Test
    public void testFilter6() {
        assertEquals(0, FilterUtil.filter(new Object[]{"items"}, new OrFilter<>()).length);
    }

    @Test
    public void test() {
        EvenFilter evenFilter = new EvenFilter();
        List<List<Integer>> groups = FilterUtil.filterGroups(
                new Integer[]{1, 2, 3},
                new InverseFilter<>(evenFilter), evenFilter);
        assertEquals(2, groups.size());
        assertEquals(CollectionUtil.toList(1, 3), groups.get(0));
        assertEquals(CollectionUtil.toList(2), groups.get(1));
    }

    @Test
    public void testAcceptedByAll() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertTrue(FilterUtil.acceptedByAll("candidate",
                new OrFilter<>(new IncludeExcludeFilter<>(), orFilter, orFilter1)));
    }

    @Test
    public void testAcceptedByAll2() {
        ConstantFilter<Object> constantFilter = new ConstantFilter<>(true);
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        assertTrue(FilterUtil.acceptedByAll("candidate",
                new OrFilter<>(constantFilter, orFilter, new OrFilter<>(null, null, null))));
    }

    @Test
    public void testAcceptedByAll3() {
        assertFalse(FilterUtil.acceptedByAll("candidate", new OrFilter<Object>()));
    }

    @Test
    public void testFindSingleMatch() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        OrFilter<Object> filter = new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null));
        assertNull(FilterUtil.findSingleMatch(new ArrayList<>(), filter));
    }

    @Test
    public void testFindSingleMatch2() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertEquals("e", FilterUtil.findSingleMatch(objectList,
                new OrFilter<>(new IncludeExcludeFilter<>(), orFilter, orFilter1)));
    }

    @Test
    public void testFindSingleMatch3() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        ConstantFilter<Object> constantFilter = new ConstantFilter<>(true);
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        assertEquals("e", FilterUtil.findSingleMatch(objectList,
                new OrFilter<>(constantFilter, orFilter, new OrFilter<>(null, null, null))));
    }

    @Test
    public void testFindSingleMatch4() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        assertNull(FilterUtil.findSingleMatch(objectList, new OrFilter<>()));
    }

    @Test
    public void testFindSingleMatch5() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertThrows(ConfigurationError.class, () -> FilterUtil.findSingleMatch(objectList,
                new OrFilter<>(new IncludeExcludeFilter<>(), orFilter, orFilter1)));
    }

    @Test
    public void testSplit() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertEquals(1,
                FilterUtil.split(objectList, new OrFilter<>(new AcceptAllFilter<>(), orFilter, orFilter1))
                        .getMatches()
                        .size());
    }

    @Test
    public void testSplit2() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        assertEquals(1, FilterUtil.split(objectList, new OrFilter<>()).getMismatches().size());
    }

    @Test
    public void testSplit3() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertEquals(1, FilterUtil
                .split(new Object[]{"items"}, new OrFilter<>(new AcceptAllFilter<>(), orFilter, orFilter1))
                .getMatches()
                .size());
    }

    @Test
    public void testSplit4() {
        assertEquals(1, FilterUtil.split(new Object[]{"items"}, new OrFilter<>()).getMismatches().size());
    }

    @Test
    public void testFilterGroups() {
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertEquals(1,
                FilterUtil
                        .filterGroups(new Object[]{},
                                new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null)))
                        .size());
    }

    @Test
    public void testFilterGroups3() {
        assertEquals(1, FilterUtil.filterGroups(new Object[]{"items"}, new OrFilter<>()).size());
    }

    @Test
    public void testFilterGroups4() {
        assertEquals(1,
                FilterUtil
                        .filterGroups(new Object[]{"items"},
                                new ConditionalFilter<>(new EqualsCondition<>("reference")))
                        .size());
    }

    public static class EvenFilter implements Filter<Integer> {
        @Override
        public boolean accept(Integer i) {
            return ((i % 2) == 0);
        }
    }

}
