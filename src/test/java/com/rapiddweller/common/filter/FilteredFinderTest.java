package com.rapiddweller.common.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.visitor.CollectionElement;

import java.util.ArrayList;

import org.junit.Test;

public class FilteredFinderTest {
    @Test
    public void testFind() {
        CollectionElement<Object> root = new CollectionElement<>(new ArrayList<>());
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        assertTrue(FilteredFinder
                .find(root, new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null)))
                .isEmpty());
    }

    @Test
    public void testFind2() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
        OrFilter<Object> filter = new OrFilter<>(new AcceptAllFilter<>(), orFilter, orFilter1);
        assertEquals(1, FilteredFinder.find(new CollectionElement<>(objectList), filter).size());
    }
}

