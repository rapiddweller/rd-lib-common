package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class BooleanMapperTest {
    @Test
    public void testConstructor() {
        BooleanMapper<Object> actualBooleanMapper = new BooleanMapper<Object>();
        Class<Object> expectedTargetType = actualBooleanMapper.targetType;
        assertSame(expectedTargetType, actualBooleanMapper.getTargetType());
        Class<Boolean> expectedSourceType = actualBooleanMapper.sourceType;
        assertSame(expectedSourceType, actualBooleanMapper.getSourceType());
    }

    @Test
    public void testConvert() throws ConversionException {
        assertEquals("true", (new BooleanMapper<Object>()).convert(true));
        assertEquals("false", (new BooleanMapper<Object>()).convert(false));
        assertNull((new BooleanMapper<Object>()).convert(null));
    }

    @Test
    public void testConstructor2() {
        BooleanMapper<Object> actualBooleanMapper = new BooleanMapper<Object>("trueValue", "falseValue", "nullValue");
        Class<Object> expectedTargetType = actualBooleanMapper.targetType;
        assertSame(expectedTargetType, actualBooleanMapper.getTargetType());
        Class<Boolean> expectedSourceType = actualBooleanMapper.sourceType;
        assertSame(expectedSourceType, actualBooleanMapper.getSourceType());
    }
}

