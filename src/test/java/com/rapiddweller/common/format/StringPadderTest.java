package com.rapiddweller.common.format;

import com.rapiddweller.common.ConversionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class StringPadderTest {
  @Test
  public void testConstructor() {
    StringPadder actualStringPadder = new StringPadder(3, Alignment.LEFT, 'A');
    assertEquals('A', actualStringPadder.getPadChar());
    assertEquals(3, actualStringPadder.getLength());
    Class<?> expectedTargetType = String.class;
    Class<String> targetType = actualStringPadder.getTargetType();
    assertSame(expectedTargetType, targetType);
    assertSame(targetType, actualStringPadder.getSourceType());
    assertEquals(Alignment.LEFT, actualStringPadder.getAlignment());
  }

  @Test
  public void testConstructor2() {
    assertThrows(IllegalArgumentException.class, () -> new StringPadder(0, Alignment.LEFT, 'A'));
  }

  @Test
  public void testConstructor3() {
    assertThrows(IllegalArgumentException.class, () -> new StringPadder(3, Alignment.LEFT, '\u0000'));
  }

  @Test
  public void testConvert() throws ConversionException {
    assertThrows(IllegalArgumentException.class, () -> (new StringPadder(3, Alignment.LEFT, 'A')).convert("Text"));
    assertEquals("AAA", (new StringPadder(3, Alignment.LEFT, 'A')).convert(""));
    assertEquals("AAA", (new StringPadder(3, Alignment.RIGHT, 'A')).convert(""));
    assertEquals("AAA", (new StringPadder(3, Alignment.CENTER, 'A')).convert(""));
    assertEquals("000", (new StringPadder(3, Alignment.RIGHT, '0')).convert(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegative() {
    assertEquals(1129653923, (new StringPadder(-1, Alignment.LEFT, 'A')).hashCode());
  }

  @Test
  public void testEquals() {
    assertNotEquals("obj", (new StringPadder(3, Alignment.LEFT, 'A')));
    assertNotEquals(null, (new StringPadder(3, Alignment.LEFT, 'A')));
  }

  @Test
  public void testToString() {
    assertEquals("3lA", (new StringPadder(3, Alignment.LEFT, 'A')).toString());
  }
}

