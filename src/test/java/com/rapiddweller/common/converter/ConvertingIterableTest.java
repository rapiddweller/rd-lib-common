package com.rapiddweller.common.converter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConvertingIterableTest {
    @Test
    public void testGetType() {
        ArrayList<Object> iterable = new ArrayList<Object>();
        Class<Object> actualType = (new ConvertingIterable<Object, Object>(iterable, new ToCollectionConverter()))
                .getType();
        assertSame(List.class, actualType);
    }

    @Test
    public void testIterator() {
        ArrayList<Object> iterable = new ArrayList<Object>();
        assertFalse((new ConvertingIterable<Object, Object>(iterable, new ToCollectionConverter())).iterator().hasNext());
    }
}

