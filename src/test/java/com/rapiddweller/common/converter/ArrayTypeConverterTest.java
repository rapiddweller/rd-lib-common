package com.rapiddweller.common.converter;

import com.rapiddweller.common.Capitalization;
import com.rapiddweller.common.Converter;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ArrayTypeConverterTest {
  @Test
  public void testConstructor() {
    Class<Object> targetArrayComponentType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<Object> forNameResult1 = Object.class;
    ArrayTypeConverter<Object> actualArrayTypeConverter = new ArrayTypeConverter<>(targetArrayComponentType,
        forNameResult, forNameResult1, Object.class);
    assertTrue(actualArrayTypeConverter.isParallelizable());
    Class<Object[]> targetType = actualArrayTypeConverter.getTargetType();
    assertEquals("[Ljava.lang.Object;", targetType.getName());
    Converter<Object, Object>[] components = actualArrayTypeConverter.getComponents();
    assertEquals(3, components.length);
    Converter<Object, Object> converter = components[0];
    Converter<Object, Object> converter1 = components[1];
    Converter<Object, Object> converter2 = components[2];
    assertSame(targetType, actualArrayTypeConverter.getSourceType());
    NumberFormatter actualNumberFormatter = ((AnyConverter) converter2).decimalConverter;
    assertEquals("yyyy-MM-dd'T'HH:mm:ss", ((AnyConverter) converter).getDateTimePattern());
    assertNull(actualNumberFormatter);
    assertNull(((AnyConverter) converter2).integralConverter);
    assertNull(((AnyConverter) converter2).getCharQuote());
    assertEquals("AnyConverter(Object)", converter2.toString());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter2).getDateCapitalization());
    assertNull(((AnyConverter) converter2).getStringQuote());
    assertNull(((AnyConverter) converter1).integralConverter);
    assertEquals("yyyy-MM-dd'T'HH:mm:ss", ((AnyConverter) converter1).getDateTimePattern());
    assertEquals("", ((AnyConverter) converter1).getNullString());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter1).getDateTimeCapitalization());
    assertEquals("yyyy-MM-dd", ((AnyConverter) converter1).getDatePattern());
    assertEquals("HH:mm:ss", ((AnyConverter) converter1).getTimePattern());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter1).getTimestampCapitalization());
    assertEquals("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS", ((AnyConverter) converter1).getTimestampPattern());
    assertNull(((AnyConverter) converter1).getCharQuote());
    assertEquals("AnyConverter(Object)", converter1.toString());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter1).getDateCapitalization());
    assertNull(((AnyConverter) converter1).getStringQuote());
    assertNull(((AnyConverter) converter).integralConverter);
    assertEquals("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS", ((AnyConverter) converter).getTimestampPattern());
    assertEquals("", ((AnyConverter) converter).getNullString());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter).getDateTimeCapitalization());
    assertEquals("yyyy-MM-dd", ((AnyConverter) converter).getDatePattern());
    assertEquals("HH:mm:ss", ((AnyConverter) converter).getTimePattern());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter).getTimestampCapitalization());
    assertEquals(Capitalization.mixed, ((AnyConverter) converter).getDateCapitalization());
    assertNull(((AnyConverter) converter).getCharQuote());
    assertEquals("AnyConverter(Object)", converter.toString());
    assertNull(((AnyConverter) converter).decimalConverter);
    assertNull(((AnyConverter) converter).getStringQuote());
    assertNull(((AnyConverter) converter1).decimalConverter);
  }

  @Test
  public void testConvert() {
    assertEquals(1, ArrayTypeConverter.convert(new Object[] {"args"}, Object.class).length);
    assertEquals(1, ArrayTypeConverter.convert(
        new Object[] {new ConversionTypes(new ToCollectionConverter(Collection.class))}, Object.class).length);
    assertEquals(1, ArrayTypeConverter.convert(new Object[] {"args"}, new Class[] {Object.class}).length);
    assertEquals(0, ArrayTypeConverter.convert(new Object[] {}, new Class[] {null}).length);
  }

  @Test
  public void testToString() {
    Class<Object> targetArrayComponentType = Object.class;
    Class<?> forNameResult = Object.class;
    Class<?> forNameResult1 = Object.class;
    assertEquals("ArrayTypeConverter[class [Ljava.lang.Object;]",
        (new ArrayTypeConverter<>(targetArrayComponentType, forNameResult, forNameResult1, Object.class))
            .toString());
  }
}

