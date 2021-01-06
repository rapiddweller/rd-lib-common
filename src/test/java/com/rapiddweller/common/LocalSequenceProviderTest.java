package com.rapiddweller.common;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LocalSequenceProviderTest {
    @Test
    public void testSetCached() {
        LocalSequenceProvider instance = LocalSequenceProvider.getInstance("foo.txt");
        instance.setCached(true);
        assertTrue(instance.isCached());
    }
}

