package com.rapiddweller.common.converter;

import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.Converter;
import org.junit.Test;

public class MultiConverterWrapperTest {
    @Test
    public void testSetComponents() {
        Class<Object> sourceComponentType = Object.class;
        Class<Object> targetComponentType = Object.class;
        ToCollectionConverter toCollectionConverter = new ToCollectionConverter();
        ToCollectionConverter toCollectionConverter1 = new ToCollectionConverter();
        ArrayConverter<Object, Object> arrayConverter = new ArrayConverter<Object, Object>(sourceComponentType,
                targetComponentType, toCollectionConverter, toCollectionConverter1, new ToCollectionConverter());
        ToCollectionConverter toCollectionConverter2 = new ToCollectionConverter();
        ToCollectionConverter toCollectionConverter3 = new ToCollectionConverter();
        arrayConverter
                .setComponents(new Converter[]{toCollectionConverter2, toCollectionConverter3, new ToCollectionConverter()});
        assertTrue(arrayConverter.isParallelizable());
    }
}

