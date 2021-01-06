package com.rapiddweller.common.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.visitor.CollectionElement;

import java.util.ArrayList;

import org.junit.Test;

public class FilteredFinderTest {
    @Test
    public void testFind() {
        CollectionElement<Object> root = new CollectionElement<Object>(new ArrayList<Object>());
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        assertTrue(FilteredFinder
                .<Object>find(root, new OrFilter<Object>(orFilter, orFilter1, new OrFilter<Object>(null, null, null)))
                .isEmpty());
    }

    @Test
    public void testFind2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        OrFilter<Object> filter = new OrFilter<Object>(new AcceptAllFilter<Object>(), orFilter, orFilter1);
        assertEquals(1, FilteredFinder.<Object>find(new CollectionElement<Object>(objectList), filter).size());
    }
}

