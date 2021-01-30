package com.rapiddweller.common.collection;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class CaseSensitiveOrderedNameMapTest {
    @Test
    public void testConstructor() {
        assertTrue((new CaseSensitiveOrderedNameMap<>()).isEmpty());
        assertTrue((new CaseSensitiveOrderedNameMap<>(new HashMap<>())).isEmpty());
    }
}

