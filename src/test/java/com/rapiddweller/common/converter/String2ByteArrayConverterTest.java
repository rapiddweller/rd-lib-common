package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class String2ByteArrayConverterTest {
  @Test
  public void testConstructor() {
    String2ByteArrayConverter actualString2ByteArrayConverter = new String2ByteArrayConverter();
    assertEquals("[B", actualString2ByteArrayConverter.getTargetType().getName());
    Class<String> expectedSourceType = actualString2ByteArrayConverter.sourceType;
    assertSame(expectedSourceType, actualString2ByteArrayConverter.getSourceType());
  }

  @Test
  public void testConvert() throws ConversionException {
    byte[] actualConvertResult = (new String2ByteArrayConverter()).convert("Source Value");
    assertEquals(12, actualConvertResult.length);
    assertEquals((byte) 83, actualConvertResult[0]);
    assertEquals((byte) 111, actualConvertResult[1]);
    assertEquals((byte) 117, actualConvertResult[2]);
    assertEquals((byte) 114, actualConvertResult[3]);
    assertEquals((byte) 99, actualConvertResult[4]);
    assertEquals((byte) 101, actualConvertResult[5]);
    assertEquals((byte) 32, actualConvertResult[6]);
    assertEquals((byte) 86, actualConvertResult[7]);
    assertEquals((byte) 97, actualConvertResult[8]);
    assertEquals((byte) 108, actualConvertResult[9]);
    assertEquals((byte) 117, actualConvertResult[10]);
    assertEquals((byte) 101, actualConvertResult[11]);
  }

  @Test
  public void testConvert2() throws ConversionException {
    assertThrows(ConfigurationError.class, () -> (new String2ByteArrayConverter("Encoding")).convert("Source Value"));
  }
}

