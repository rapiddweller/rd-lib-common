package com.rapiddweller.common.accessor;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.Accessor;

import java.util.HashMap;

import org.junit.Test;

public class AccessorMapAccessorTest {
    @Test
    public void testGetDependencies() {
        assertTrue((new AccessorMapAccessor(new HashMap<>(), "key")).getDependencies().isEmpty());
    }

    @Test
    public void testGetAccessor() {
        assertNull((new AccessorMapAccessor(new HashMap<>(), "key")).getAccessor());
    }
}

