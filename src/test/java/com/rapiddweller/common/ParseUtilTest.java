/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

import junit.framework.AssertionFailedError;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.ParsePosition;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link ParseUtil} class.
 * Created: 20.03.2005 16:32:47
 *
 * @author Volker Bergmann
 */
public class ParseUtilTest {

  private static final Logger logger = LoggerFactory.getLogger(ParseUtilTest.class);

  private static final double DELTA = 1e-4;

  @Test
  public void testParseWord() throws IOException {
    assertEquals("", ParseUtil.parseWord(new PushbackReader(Reader.nullReader())));
    assertEquals("S", ParseUtil.parseWord(new PushbackReader(new StringReader("S"))));
  }

  @Test
  public void testParseWord2() throws IOException {
    Reader nullReaderResult = Reader.nullReader();
    nullReaderResult.skip(0L);
    assertEquals("", ParseUtil.parseWord(new PushbackReader(nullReaderResult)));
  }

  @Test
  public void testParseWord3() throws IOException {
    PushbackReader pushbackReader = new PushbackReader(Reader.nullReader());
    pushbackReader.unread(0);
    assertEquals("", ParseUtil.parseWord(pushbackReader));
  }

  @Test
  public void testParseDoubleQuotedString() throws Exception {
    assertEquals("Blabla 123 !", ParseUtil.parseDoubleQuotedString(createReader("\"Blabla 123 !\"")));
  }

  @Test
  public void testParseNonNegativeIntegerReader() throws Exception {
    assertEquals(0, ParseUtil.parseNonNegativeInteger(createReader("0")));
    assertEquals(1, ParseUtil.parseNonNegativeInteger(createReader("1")));
    assertEquals(123, ParseUtil.parseNonNegativeInteger(createReader("123")));
  }

  @Test
  public void testParseNonNegativeIntegerString() throws Exception {
    assertEquals(0, ParseUtil.parseNonNegativeInteger("0", new ParsePosition(0)));
    assertEquals(1, ParseUtil.parseNonNegativeInteger("1", new ParsePosition(0)));
    assertEquals(123, ParseUtil.parseNonNegativeInteger("123", new ParsePosition(0)));
  }

  @Test
  public void testParseOptionalSign() throws IOException {
    assertFalse(ParseUtil.parseOptionalSign(new PushbackReader(Reader.nullReader())));
    assertFalse(ParseUtil.parseOptionalSign(new PushbackReader(new StringReader("S"))));
  }

  @Test
  public void testParseOptionalSign2() throws IOException {
    Reader nullReaderResult = Reader.nullReader();
    nullReaderResult.skip(0L);
    assertFalse(ParseUtil.parseOptionalSign(new PushbackReader(nullReaderResult)));
  }

  @Test
  public void testParseUnit() throws IOException {
    assertNull(ParseUtil.parseUnit(new PushbackReader(Reader.nullReader())));
    assertEquals("S", ParseUtil.parseUnit(new PushbackReader(new StringReader("S"))));
  }

  @Test
  public void testParseUnit2() throws IOException {
    Reader nullReaderResult = Reader.nullReader();
    nullReaderResult.skip(0L);
    assertNull(ParseUtil.parseUnit(new PushbackReader(nullReaderResult)));
  }

  @Test
  public void testParseUnit3() throws IOException {
    PushbackReader pushbackReader = new PushbackReader(Reader.nullReader());
    pushbackReader.unread(0);
    assertNull(ParseUtil.parseUnit(pushbackReader));
  }

  @Test
  public void testParseEstimated() throws IOException {
    assertFalse(ParseUtil.parseEstimated(new PushbackReader(Reader.nullReader())));
    assertFalse(ParseUtil.parseEstimated(new PushbackReader(new StringReader("S"))));
  }

  @Test
  public void testParseEstimated2() throws IOException {
    Reader nullReaderResult = Reader.nullReader();
    nullReaderResult.skip(0L);
    assertFalse(ParseUtil.parseEstimated(new PushbackReader(nullReaderResult)));
  }

  @Test
  public void testIsEmpty() {
    assertFalse(ParseUtil.isEmpty("object"));
    assertTrue(ParseUtil.isEmpty(null));
    assertTrue(ParseUtil.isEmpty(null));
    assertTrue(ParseUtil.isEmpty(""));
  }

  @Test
  public void testNextNonWhitespaceIndex() {
    assertEquals(1, ParseUtil.nextNonWhitespaceIndex("Source", 1));
    assertEquals(-1, ParseUtil.nextNonWhitespaceIndex("", 1));
  }

  @Test
  public void testParseInteger() throws Exception {
    assertEquals(1, ParseUtil.parseInteger(createReader("1")));
    assertEquals(0, ParseUtil.parseInteger(createReader("0")));
    assertEquals(-1, ParseUtil.parseInteger(createReader("-1")));
    assertEquals(123, ParseUtil.parseInteger(createReader("123")));
    assertEquals(-123, ParseUtil.parseInteger(createReader("-123")));
  }

  @Test
  public void testParseDecimal() throws Exception {
    assertEquals(1., ParseUtil.parseDecimal(createReader("1")), DELTA);
    assertEquals(1., ParseUtil.parseDecimal(createReader("1.")), DELTA);
    assertEquals(1., ParseUtil.parseDecimal(createReader("1.0")), DELTA);
    assertEquals(0., ParseUtil.parseDecimal(createReader("0")), DELTA);
    assertEquals(0., ParseUtil.parseDecimal(createReader("0.")), DELTA);
    assertEquals(0., ParseUtil.parseDecimal(createReader("0.0")), DELTA);
    assertEquals(-1., ParseUtil.parseDecimal(createReader("-1")), DELTA);
    assertEquals(-1., ParseUtil.parseDecimal(createReader("-1.")), DELTA);
    assertEquals(-1., ParseUtil.parseDecimal(createReader("-1.0")), DELTA);
    assertEquals(123., ParseUtil.parseDecimal(createReader("123")), DELTA);
    assertEquals(123., ParseUtil.parseDecimal(createReader("123.")), DELTA);
    assertEquals(123., ParseUtil.parseDecimal(createReader("123.0")), DELTA);
    assertEquals(123.45, ParseUtil.parseDecimal(createReader("123.45")), DELTA);
    assertEquals(-123., ParseUtil.parseDecimal(createReader("-123")), DELTA);
    assertEquals(-123., ParseUtil.parseDecimal(createReader("-123.")), DELTA);
    assertEquals(-123., ParseUtil.parseDecimal(createReader("-123.0")), DELTA);
    assertEquals(-123.45, ParseUtil.parseDecimal(createReader("-123.45")), DELTA);
  }

  @Test
  public void testParseOptionalPostfix() throws IOException {
    assertEquals(0.0, ParseUtil.parseOptionalPostfix(new PushbackReader(Reader.nullReader())), 0.0);
    assertEquals(0.0, ParseUtil.parseOptionalPostfix(new PushbackReader(new StringReader("S"))), 0.0);
  }

  @Test
  public void testParseOptionalPostfix2() throws IOException {
    Reader nullReaderResult = Reader.nullReader();
    nullReaderResult.skip(0L);
    assertEquals(0.0, ParseUtil.parseOptionalPostfix(new PushbackReader(nullReaderResult)), 0.0);
  }

  @Test
  public void testParseEmptyLineSeparatedFile() throws Exception {
    checkParseEmptyLineSeparatedFile(new String[0][0], "");
    checkParseEmptyLineSeparatedFile(new String[][] {{"a"}}, "a");
    checkParseEmptyLineSeparatedFile(new String[][] {{}, {"a", "b"}}, "", "a", "b");
    checkParseEmptyLineSeparatedFile(new String[][] {{"a", "b"}}, "a", "b");
    checkParseEmptyLineSeparatedFile(new String[][] {{"a", "b"}}, "a", "b", "");
    checkParseEmptyLineSeparatedFile(new String[][] {{"a", "b"}, {"c"}}, "a", "b", "", "c");
  }

  @Test
  public void test() {
    assertEqualArrays(ParseUtil.splitNumbers("abc"), "abc");
    assertEqualArrays(ParseUtil.splitNumbers("abc1"), "abc", BigInteger.ONE);
    assertEqualArrays(ParseUtil.splitNumbers("abc12"), "abc", new BigInteger("12"));
    assertEqualArrays(ParseUtil.splitNumbers("abc12xyz"), "abc", new BigInteger("12"), "xyz");
  }

  @Test
  public void testIsNMToken() {
    assertFalse(ParseUtil.isNMToken(null));
    assertFalse(ParseUtil.isNMToken(""));
    assertFalse(ParseUtil.isNMToken("1"));
    assertFalse(ParseUtil.isNMToken("?bla"));
    assertTrue(ParseUtil.isNMToken("x"));
    assertTrue(ParseUtil.isNMToken("_"));
    assertTrue(ParseUtil.isNMToken(":"));
    assertTrue(ParseUtil.isNMToken("_.-:"));
    assertFalse(ParseUtil.isNMToken("Test Name"));
    assertFalse(ParseUtil.isNMToken(null));
    assertTrue(ParseUtil.isNMToken("false"));
    assertFalse(ParseUtil.isNMToken(""));
    assertTrue(ParseUtil.isNMToken("java.lang.String"));
  }

  @Test
  public void testIsNMStartChar() {
    assertTrue(ParseUtil.isNMStartChar('A'));
    assertFalse(ParseUtil.isNMStartChar('\u0000'));
    assertTrue(ParseUtil.isNMStartChar(':'));
    assertTrue(ParseUtil.isNMStartChar('_'));
  }

  @Test
  public void testIsNMAfterStartChar() {
    assertTrue(ParseUtil.isNMAfterStartChar('A'));
    assertFalse(ParseUtil.isNMAfterStartChar('\u0000'));
    assertTrue(ParseUtil.isNMAfterStartChar('-'));
    assertTrue(ParseUtil.isNMAfterStartChar('.'));
    assertTrue(ParseUtil.isNMAfterStartChar(':'));
    assertTrue(ParseUtil.isNMAfterStartChar('_'));
  }

  @Test
  public void testIsHex() {
    checkHexChars();
    checkNonHexChars();
  }

  @Test
  public void testParseBoolean() {
    assertNull(ParseUtil.parseBoolean(null));
    assertTrue(ParseUtil.parseBoolean("true"));
    assertTrue(ParseUtil.parseBoolean("TRUE"));
    assertTrue(ParseUtil.parseBoolean("True"));
    assertTrue(ParseUtil.parseBoolean(" True ", true));
    assertFalse(ParseUtil.parseBoolean("false"));
    assertFalse(ParseUtil.parseBoolean("FALSE"));
    assertFalse(ParseUtil.parseBoolean("False"));
    assertFalse(ParseUtil.parseBoolean(" False ", true));
    assertThrows(SyntaxError.class, () -> ParseUtil.parseBoolean("S"));
    assertTrue(ParseUtil.parseBoolean("true"));
    assertNull(ParseUtil.parseBoolean(null));
    assertFalse(ParseUtil.parseBoolean("false"));
    assertThrows(SyntaxError.class, () -> ParseUtil.parseBoolean("S", true));
    assertTrue(ParseUtil.parseBoolean("true", true));
    assertNull(ParseUtil.parseBoolean(null, true));
    assertFalse(ParseUtil.parseBoolean("false", true));
    assertThrows(SyntaxError.class, () -> ParseUtil.parseBoolean("S", false));
  }

  @Test(expected = SyntaxError.class)
  public void testParseBoolean_illegal() {
    assertNull(ParseUtil.parseBoolean("nix"));
  }

  @Test(expected = SyntaxError.class)
  public void testParseBoolean_empty() {
    assertNull(ParseUtil.parseBoolean("  "));
  }

  @Test(expected = SyntaxError.class)
  public void testParseBoolean_unaccepted_whitespace() {
    assertNull(ParseUtil.parseBoolean(" true "));
  }

  // implementation --------------------------------------------------------------------------------------------------

  @SafeVarargs
  private static <T> void assertEqualArrays(T[] found, T... expected) {
    if (!Arrays.deepEquals(expected, found)) {
      throw new AssertionFailedError();
    }
  }

  private static void checkParseEmptyLineSeparatedFile(String[][] expectedOutput, String... input) throws IOException {
    String[][] output = ParseUtil.parseEmptyLineSeparatedFile(createReader(input));
    logger.debug(Arrays.deepToString(expectedOutput) + " <-> " + Arrays.deepToString(output));
    assertTrue(Arrays.deepEquals(expectedOutput, output));
  }

  @Test
  public void testParseEmptyLineSeparatedFile2() throws IOException {
    assertEquals(0, ParseUtil.parseEmptyLineSeparatedFile(Reader.nullReader()).length);
  }

  @Test
  public void testParseEmptyLineSeparatedFile3() throws IOException {
    assertEquals(1, ParseUtil.parseEmptyLineSeparatedFile(new StringReader("S")).length);
  }

  @Test
  public void testSplitNumbers() {
    assertEquals(1, ParseUtil.splitNumbers("Text").length);
    assertEquals(1, ParseUtil.splitNumbers("false").length);
    assertEquals(1, ParseUtil.splitNumbers("java.lang.String").length);
  }

  @Test
  public void testIsNonNegativeNumber() {
    assertFalse(ParseUtil.isNonNegativeNumber("Text"));
    assertTrue(ParseUtil.isNonNegativeNumber(""));
  }

  @Test
  public void testFrom() {
    assertNull(ParseUtil.from("S", "Separator"));
    assertEquals("S", ParseUtil.from("S", ""));
    assertEquals("Not Found Value", ParseUtil.from("S", "Separator", "Not Found Value"));
    assertEquals("S", ParseUtil.from("S", "", "Not Found Value"));
  }

  @Test
  public void testBefore() {
    assertEquals("S", ParseUtil.before("S", "Separator"));
    assertEquals("", ParseUtil.before("S", ""));
  }

  @Test
  public void testIsHex10() {
    assertFalse(ParseUtil.isHex("false"));
  }

  @Test
  public void testIsHex2() {
    assertTrue(ParseUtil.isHex('A'));
  }

  @Test
  public void testIsHex3() {
    assertFalse(ParseUtil.isHex('\u0000'));
  }

  @Test
  public void testIsHex4() {
    assertTrue(ParseUtil.isHex('0'));
  }

  @Test
  public void testIsHex5() {
    assertTrue(ParseUtil.isHex('a'));
  }

  @Test
  public void testIsHex6() {
    assertFalse(ParseUtil.isHex('Z'));
  }

  @Test
  public void testIsHex7() {
    assertFalse(ParseUtil.isHex('z'));
  }

  @Test
  public void testIsHex8() {
    assertTrue(ParseUtil.isHex("0123456789ABCDEF"));
  }

  @Test
  public void testIsHex9() {
    assertFalse(ParseUtil.isHex("S"));
  }

  @Test
  public void testParseAssignment() {
    assertNull(ParseUtil.parseAssignment("Line", "Operator", true));
    assertNull(ParseUtil.parseAssignment(null, "Operator", true));
    assertNull(ParseUtil.parseAssignment("Line", "false", true));
    assertNull(ParseUtil.parseAssignment("Line", "", true));
    assertEquals(2, ParseUtil.parseAssignment("Line", "", false).length);
    assertEquals(2, ParseUtil.parseAssignment("", "", false).length);
  }

  private static PushbackReader createReader(String... lines) {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      buffer.append(line);
      if (i < lines.length - 1) {
        buffer.append(SystemInfo.getLineSeparator());
      }
    }
    StringReader reader = new StringReader(buffer.toString());
    return new PushbackReader(reader);
  }

  private static void checkHexChars() {
    for (int i = 0; i < "0123456789abcdefABCDEF".length(); i++) {
      assertTrue(ParseUtil.isHex("0123456789abcdefABCDEF".charAt(i)));
    }
  }

  private static void checkNonHexChars() {
    for (int i = 0; i < "gG!%-.".length(); i++) {
      assertFalse(ParseUtil.isHex("gG!%-.".charAt(i)));
    }
  }
}
