package com.rapiddweller.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.bean.PropertyAccessor;
import org.junit.Test;

public class PropertyExtractorTest {
    @Test
    public void testConstructor() {
        PropertyExtractor<Object, Object> actualPropertyExtractor = new PropertyExtractor<>(null,
                "Property Name", Object.class);
        PropertyAccessor<Object, Object> propertyAccessor = actualPropertyExtractor.accessor;
        assertTrue(propertyAccessor instanceof com.rapiddweller.common.bean.UntypedPropertyAccessor);
        Class<Object> expectedTargetType = actualPropertyExtractor.targetType;
        assertSame(expectedTargetType, actualPropertyExtractor.getTargetType());
        assertNull(actualPropertyExtractor.getSourceType());
        assertEquals("Property Name", propertyAccessor.getPropertyName());
        assertNull(propertyAccessor.getValueType());
    }
}

