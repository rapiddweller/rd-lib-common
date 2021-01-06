package com.rapiddweller.common.collection;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class CaseSensitiveOrderedNameMapTest {
    @Test
    public void testConstructor() {
        assertTrue((new CaseSensitiveOrderedNameMap<Object>()).isEmpty());
        assertTrue((new CaseSensitiveOrderedNameMap<Object>(new HashMap<String, Object>())).isEmpty());
    }
}

