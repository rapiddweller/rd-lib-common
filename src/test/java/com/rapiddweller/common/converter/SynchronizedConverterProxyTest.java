package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;

import com.rapiddweller.common.ConversionException;

import java.util.ArrayList;

import org.junit.Test;

public class SynchronizedConverterProxyTest {
    @Test
    public void testConvert() throws ConversionException {
        assertEquals(1, ((ArrayList) (new SynchronizedConverterProxy<Object, Object>(new ToCollectionConverter()))
                .convert("sourceValue")).size());
        assertEquals(1,
                ((ArrayList) (new SynchronizedConverterProxy<>(
                        new SynchronizedConverterProxy<Object, Object>(new ToCollectionConverter()))).convert("sourceValue"))
                        .size());
        assertEquals("sourceValue",
                (new SynchronizedConverterProxy<Object, Object>(new AnyConverter(Object.class))).convert("sourceValue"));
    }
}

