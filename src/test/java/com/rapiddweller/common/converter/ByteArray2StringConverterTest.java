package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ConversionException;
import org.junit.Test;

public class ByteArray2StringConverterTest {
    @Test
    public void testConstructor() {
        ByteArray2StringConverter actualByteArray2StringConverter = new ByteArray2StringConverter();
        Class<String> expectedTargetType = actualByteArray2StringConverter.targetType;
        assertSame(expectedTargetType, actualByteArray2StringConverter.getTargetType());
        assertEquals("[B", actualByteArray2StringConverter.getSourceType().getName());
    }

    @Test
    public void testConvert() throws ConversionException {
        assertEquals("AAAAAAAAAAAAAAAAAAAAAAAA", (new ByteArray2StringConverter()).convert(
                new byte[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65}));
        assertThrows(ConfigurationError.class, () -> (new ByteArray2StringConverter("Encoding")).convert(
                new byte[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65}));
    }
}

