/*
 * Copyright (C) 2004-2022 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import com.rapiddweller.common.accessor.FeatureAccessor;
import com.rapiddweller.common.exception.IllegalArgumentError;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the {@link StringUtil} class.
 * Created: 21.07.2006 17:31:42
 * @author Volker Bergmann
 * @since 0.1
 */
public class StringUtilTest {

  @Test
  public void testShortOrdinal() {
    assertEquals("10th", StringUtil.shortOrdinal(10));
    assertEquals("1st", StringUtil.shortOrdinal(1));
    assertEquals("2nd", StringUtil.shortOrdinal(2));
    assertEquals("3rd", StringUtil.shortOrdinal(3));
  }

  @Test
  public void testEmpty() {
    assertTrue(StringUtil.isEmpty(null));
    assertTrue(StringUtil.isEmpty(""));
    assertFalse(StringUtil.isEmpty(" "));
    assertFalse(StringUtil.isEmpty("null"));
  }

  @Test
  public void testSuffix() {
    assertNull(StringUtil.suffix("a", '.'));
    assertNull(StringUtil.suffix("a.", '.'));
    assertEquals("b", StringUtil.suffix("a.b", '.'));
    assertEquals("b", StringUtil.suffix(".b", '.'));
    assertNull(StringUtil.suffix(null, '.'));
    assertNull(StringUtil.suffix("Name", 'A'));
    assertNull(StringUtil.suffix(null, 'A'));
    assertEquals("me", StringUtil.suffix("Name", 'a'));
  }

  @Test
  public void testLastToken() {
    assertEquals("a", StringUtil.lastToken("a", ','));
    assertNull(StringUtil.lastToken("a,", ','));
    assertEquals("b", StringUtil.lastToken("a,b", ','));
    assertEquals("b", StringUtil.lastToken(",b", ','));
    assertNull(StringUtil.lastToken(null, ','));
    assertEquals("Name", StringUtil.lastToken("Name", 'A'));
    assertNull(StringUtil.lastToken(null, 'A'));
    assertEquals("me", StringUtil.lastToken("Name", 'a'));
  }

  @Test
  public void testEndsWithSequence() {
    assertTrue(ArrayUtil.endsWithSequence(new String[] {"a", "b", "c"}, new String[] {"c"}));
    assertTrue(ArrayUtil.endsWithSequence(new String[] {"a", "b", "c"}, new String[] {"b", "c"}));
    assertTrue(ArrayUtil.endsWithSequence(new String[] {"a", "b", "c"}, new String[] {"a", "b", "c"}));
    assertFalse(ArrayUtil.endsWithSequence(new String[] {"a", "b", "c"}, new String[] {"b", "a"}));
    assertFalse(ArrayUtil.endsWithSequence(new String[] {"a", "b", "c"}, new String[] {"a", "b"}));
  }

  @Test
  public void testTokenize() {
    assertNull(StringUtil.tokenize(null, ','));
    assertArrayEquals(new String[] {""}, StringUtil.tokenize("", ','));
    assertArrayEquals(new String[] {"a", "b", ""}, StringUtil.tokenize("a,b,", ','));
    assertArrayEquals(new String[] {"", "b", "c"}, StringUtil.tokenize(",b,c", ','));
    assertArrayEquals(new String[] {"", "b", ""}, StringUtil.tokenize(",b,", ','));
    assertArrayEquals(new String[] {"a", "b", "c"}, StringUtil.tokenize("a,b,c", ','));
    assertArrayEquals(new String[] {"a", "b", "c"}, StringUtil.tokenize("a.b.c", '.'));
    assertArrayEquals(new String[] {"a,b,c"}, StringUtil.tokenize("a,b,c", '.'));
    assertEquals(1, StringUtil.tokenize("Text", 'A').length);
    assertNull(StringUtil.tokenize(null, 'A'));
    assertEquals(1, StringUtil.tokenize("", 'A').length);
    assertEquals(2, StringUtil.tokenize("\\'", '\\').length);
    assertEquals(4, StringUtil.tokenize("java.lang.String", 'a').length);
  }

  @Test
  public void testSplitAndTrim() {
    assertEquals(1, StringUtil.splitAndTrim("List", 'A').length);
    assertEquals(1, StringUtil.splitAndTrim("", 'A').length);
    assertEquals(1, StringUtil.splitAndTrim("'", 'A').length);
    assertEquals(1, StringUtil.splitAndTrim("List", '*').length);
  }

  @Test
  public void testSplit() {
    assertEquals(1, StringUtil.split("List", 'A').length);
    assertEquals(1, StringUtil.split("List", '*').length);
  }

  @Test
  public void testSplitMultiRowCells() {
    assertArrayEquals(new String[][] {{ "x", "y", "z" }}, StringUtil.splitMultiRowCells(new String[] { "x", "y", "z" }));
    assertArrayEquals(new String[][] {{ "", "", "" }}, StringUtil.splitMultiRowCells(new String[] { "", "", "" }));
    assertArrayEquals(new String[][] {{ "", "", "" }}, StringUtil.splitMultiRowCells(new String[] { null, null, null }));
    assertArrayEquals(new String[][] {
        { "x", "y", "z" },
        { "", "var", "r2" },
        { "", "", "r3" }
    }, StringUtil.splitMultiRowCells(new String[] { "x", "y\nvar", "z\nr2\nr3" }));
  }


  @Test
  public void testNormalize() {
    assertEquals("#Abc!", StringUtil.normalize("#Abc!"));
    assertEquals("S", StringUtil.normalize("S"));
  }

  @Test
  public void testIncrement() {
    assertEquals("0", StringUtil.increment(null));
    assertEquals("0", StringUtil.increment(""));
    assertEquals("1", StringUtil.increment("0"));
    assertEquals("a", StringUtil.increment("9"));
    assertEquals("b", StringUtil.increment("a"));
    assertEquals("10", StringUtil.increment("z"));
    assertEquals("11", StringUtil.increment("10"));
    assertEquals("12", StringUtil.increment("11"));
    assertEquals("20", StringUtil.increment("1z"));
    assertEquals("100", StringUtil.increment("zz"));
    assertEquals("Texu", StringUtil.increment("Text"));
    assertEquals("0", StringUtil.increment(null));
    assertEquals("0", StringUtil.increment(""));
    assertEquals(4, StringUtil.increment(new char[] {'A', 'A', 'A', 'A'}, 1).length);
    assertEquals(4, StringUtil.increment(new char[] {'A', '9', 'A', 'A'}, 1).length);
    assertEquals(4, StringUtil.increment(new char[] {'A', 'Z', 'A', 'A'}, 1).length);
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> StringUtil.increment(new char[] {}, 1));
    assertEquals(5, StringUtil.increment(new char[] {'Z', 'Z', 'A', 'A'}, 1).length);
  }

  @Test
  public void testIsWhitespace() {
    assertTrue(StringUtil.isWhitespace(' '));
    assertFalse(StringUtil.isWhitespace('\u0000'));
  }

  @Test
  public void testNormalizeSpace() {
    assertEquals("abc", StringUtil.normalizeSpace("abc"));
    assertEquals("", StringUtil.normalizeSpace(""));
    assertEquals("", StringUtil.normalizeSpace(" "));
    assertEquals("", StringUtil.normalizeSpace("\r\n"));
    assertEquals("abc", StringUtil.normalizeSpace(" abc "));
    assertEquals("abc def", StringUtil.normalizeSpace("abc def"));
    assertEquals("abc def", StringUtil.normalizeSpace(" abc \r\n def \r\n"));
    assertEquals("abc def", StringUtil.normalizeSpace("\n\tabc\n\tdef\r\t"));
    assertEquals("S", StringUtil.normalizeSpace("S"));
    assertNull(StringUtil.normalizeSpace(null));
    assertEquals("", StringUtil.normalizeSpace(""));
    assertEquals("\\'", StringUtil.normalizeSpace("\\'"));
  }

  @Test
  public void testTrimEnd() {
    assertNull(StringUtil.trimEnd(null));
    assertEquals("", StringUtil.trimEnd(""));
    assertEquals("", StringUtil.trimEnd(" \r\n"));
    assertEquals("abc", StringUtil.trimEnd("abc"));
    assertEquals("abc", StringUtil.trimEnd("abc "));
    assertEquals(" abc", StringUtil.trimEnd(" abc"));
    assertEquals(" abc", StringUtil.trimEnd(" abc "));
    assertEquals("S", StringUtil.trimEnd("S"));
    assertNull(StringUtil.trimEnd(null));
    assertEquals("", StringUtil.trimEnd(""));
  }

  @Test
  public void testCountChars() {
    assertEquals(0, StringUtil.countChars("S", 'A'));
    assertEquals(1, StringUtil.countChars("S", 'S'));
  }

  @Test
  public void testToArray() {
    assertEquals(0, StringUtil.toArray(new ArrayList<>()).length);
  }

  @Test
  public void testToArray2() {
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("E");
    assertEquals(1, StringUtil.toArray(stringList).length);
  }

  @Test
  public void testToArrayArray() {
    assertEquals(0, StringUtil.toArrayArray(new ArrayList<>()).length);
  }

  @Test
  public void testToArrayArray2() {
    ArrayList<List<String>> listList = new ArrayList<>();
    listList.add(new ArrayList<>());
    assertEquals(1, StringUtil.toArrayArray(listList).length);
  }

  @Test
  public void testToArrayArray3() {
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("E");
    ArrayList<List<String>> listList = new ArrayList<>();
    listList.add(stringList);
    assertEquals(1, StringUtil.toArrayArray(listList).length);
  }

  @Test
  public void testPadRight() {
    assertEquals("", StringUtil.padRight(null, 0, ' '));
    assertEquals(" ", StringUtil.padRight(null, 1, ' '));
    assertEquals("", StringUtil.padRight("", 0, ' '));
    assertEquals(" ", StringUtil.padRight("", 1, ' '));
    assertEquals("ab", StringUtil.padRight("ab", 2, ' '));
    assertEquals("ab ", StringUtil.padRight("ab", 3, ' '));
    assertEquals("ab    ", StringUtil.padRight("ab", 6, ' '));
    try {
      StringUtil.padRight("abcde", 2, ' ');
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentError e) {
      // this is expected
    }
  }

  @Test
  public void testFill() {
    assertEquals(4, StringUtil.fill(new char[] {'A', 'A', 'A', 'A'}, 1, 1, 'A').length);
    assertEquals(4, StringUtil.fill(new char[] {'A', 'A', 'A', 'A'}, 0, 1, 'A').length);
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> StringUtil.fill(new char[] {'A', 'A', 'A', 'A'}, -1, 1, 'A'));
  }

  @Test
  public void testGetChars() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> StringUtil.getChars(1, 1, "Text", new char[] {'A', 'A', 'A', 'A'}, 1));
    assertEquals(4, StringUtil.getChars(1, 1, "", new char[] {'A', 'A', 'A', 'A'}, 1).length);
    assertEquals(4, StringUtil.getChars(1, 1, "java.lang.String", new char[] {'A', 'A', 'A', 'A'}, 1).length);
    assertEquals(1, StringUtil.getChars("S").length);
  }

  @Test
  public void testGetChars2() {
    char[] actualChars = StringUtil.getChars("java.lang.String");
    assertEquals(16, actualChars.length);
    assertEquals('j', actualChars[0]);
    assertEquals('a', actualChars[1]);
    assertEquals('v', actualChars[2]);
    assertEquals('a', actualChars[3]);
    assertEquals('.', actualChars[4]);
    assertEquals('l', actualChars[5]);
    assertEquals('S', actualChars[10]);
    assertEquals('t', actualChars[11]);
    assertEquals('r', actualChars[12]);
    assertEquals('i', actualChars[13]);
    assertEquals('n', actualChars[14]);
    assertEquals('g', actualChars[15]);
  }

  @Test
  public void testPadString() {
    assertEquals("AAA", StringUtil.padString('A', 3));
    assertThrows(IllegalArgumentError.class, () -> StringUtil.padString('A', -1));
  }

  @Test
  public void testPadLeft() {
    assertEquals("", StringUtil.padLeft(null, 0, ' '));
    assertEquals(" ", StringUtil.padLeft(null, 1, ' '));
    assertEquals("", StringUtil.padLeft("", 0, ' '));
    assertEquals(" ", StringUtil.padLeft("", 1, ' '));
    assertEquals("ab", StringUtil.padLeft("ab", 2, ' '));
    assertEquals(" ab", StringUtil.padLeft("ab", 3, ' '));
    assertEquals("    ab", StringUtil.padLeft("ab", 6, ' '));
    try {
      StringUtil.padLeft("abcde", 2, ' ');
      fail("IllegalArgumentException expected");
    } catch (IllegalArgumentError e) {
      // this is expected
    }
  }

  @Test
  public void testPadLeft2() {
    assertThrows(IllegalArgumentError.class, () -> StringUtil.padLeft("Text", 3, 'A'));
  }

  @Test
  public void testPadLeft3() {
    assertEquals("AAA", StringUtil.padLeft(null, 3, 'A'));
  }

  @Test
  public void testPadLeft4() {
    assertEquals("AA'", StringUtil.padLeft("'", 3, 'A'));
  }

  @Test
  public void testPadLeft5() {
    assertEquals("AAAAAAAAAAAAAAAAAAAA", StringUtil.padLeft(null, 20, 'A'));
  }

  @Test
  public void testPadLeft6() {
    assertEquals("                    ", StringUtil.padLeft(null, 20, ' '));
  }

  @Test
  public void testPadLeft7() {
    assertEquals("AAAAjava.lang.String", StringUtil.padLeft("java.lang.String", 20, 'A'));
  }

  @Test
  public void testTrimRight() {
    assertNull(StringUtil.trimRight(null, '0'));
    assertEquals("", StringUtil.trimRight("", '0'));
    assertEquals("", StringUtil.trimRight("0", '0'));
    assertEquals("1", StringUtil.trimRight("1", '0'));
    assertEquals("1", StringUtil.trimRight("10", '0'));
    assertEquals("1", StringUtil.trimRight("1000", '0'));
    assertEquals("01", StringUtil.trimRight("0100", '0'));
    assertEquals("Source", StringUtil.trimRight("Source"));
    assertNull(StringUtil.trimRight(null));
    assertEquals("", StringUtil.trimRight(""));
    assertEquals("Source", StringUtil.trimRight("Source", 'A'));
    assertNull(StringUtil.trimRight(null, 'A'));
    assertEquals("", StringUtil.trimRight("", 'A'));
    assertEquals("Sourc", StringUtil.trimRight("Source", 'e'));
  }

  @Test
  public void testTrimLeft() {
    assertNull(StringUtil.trimLeft(null, '0'));
    assertEquals("", StringUtil.trimLeft("", '0'));
    assertEquals("", StringUtil.trimLeft("0", '0'));
    assertEquals("1", StringUtil.trimLeft("1", '0'));
    assertEquals("1", StringUtil.trimLeft("01", '0'));
    assertEquals("1", StringUtil.trimLeft("001", '0'));
    assertEquals("100", StringUtil.trimLeft("0100", '0'));
    assertEquals("Source", StringUtil.trimLeft("Source", 'A'));
    assertNull(StringUtil.trimLeft(null, 'A'));
    assertEquals("", StringUtil.trimLeft("", 'A'));
    assertEquals("ource", StringUtil.trimLeft("Source", 'S'));
  }

  @Test
  public void testTrim() {
    assertNull(StringUtil.trim(null, '0'));
    assertEquals("", StringUtil.trim("", '0'));
    assertEquals("", StringUtil.trim("0", '0'));
    assertEquals("1", StringUtil.trim("1", '0'));
    assertEquals("1", StringUtil.trim("10", '0'));
    assertEquals("1", StringUtil.trim("100", '0'));
    assertEquals("1", StringUtil.trim("00100", '0'));
    assertEquals("S", StringUtil.trim("S"));
    assertNull(StringUtil.trim(null));
    assertEquals("", StringUtil.trim(""));
    assertEquals("\\'", StringUtil.trim("\\'"));
    assertEquals("Source", StringUtil.trim("Source", 'A'));
    assertNull(StringUtil.trim(null, 'A'));
    assertEquals("", StringUtil.trim("", 'A'));
    assertEquals("'", StringUtil.trim("'", 'A'));
    assertEquals("ource", StringUtil.trim("Source", 'S'));
    assertEquals("Sourc", StringUtil.trim("Source", 'e'));
  }

  @Test
  public void testTrimAll() {
    assertEquals(1, StringUtil.trimAll(new String[] {"Array"}).length);
    assertEquals(1, StringUtil.trimAll(new String[] {null}).length);
    assertEquals(1, StringUtil.trimAll(new String[] {""}).length);
    assertEquals(1, StringUtil.trimAll(new String[] {"'"}).length);
    assertEquals(0, StringUtil.trimAll(new String[] {}).length);
  }

  @Test
  public void testToLowerCase() {
    assertEquals(1, StringUtil.toLowerCase(new String[] {"Src"}).length);
  }

  @Test
  public void testLineSeparator() {
    assertEquals("\n", StringUtil.lineSeparator());
  }

  @Test
  public void testIndexOfIgnoreCase() {
    assertEquals(-1, StringUtil.indexOfIgnoreCase("Searched", "2020-03-01"));
    assertEquals(0, StringUtil.indexOfIgnoreCase("", ""));
  }

  @Test
  public void testLastChar() {
    assertEquals('d', StringUtil.lastChar("Word"));
  }

  @Test
  public void testContains() {
    assertFalse(StringUtil.contains("S", 'A'));
    assertTrue(StringUtil.contains("\\'", '\\'));
  }

  @Test
  public void testRemove() {
    assertEquals("S", StringUtil.remove("S", "Chars"));
    assertNull(StringUtil.remove(null, "Chars"));
    assertEquals("S", StringUtil.remove("S", ""));
    assertEquals("jv.lng.Sting", StringUtil.remove("java.lang.String", "Chars"));
  }

  @Test
  public void testNullToEmpty() {
    assertEquals("Text", StringUtil.nullToEmpty("Text"));
    assertEquals("", StringUtil.nullToEmpty(null));
  }

  @Test
  public void testSubstituteNull() {
    assertEquals("value", StringUtil.substituteNull("value", "Substitution"));
    assertEquals("Substitution", StringUtil.substituteNull(null, "Substitution"));
  }

  @Test
  public void testCapitalize() {
    assertEquals("Text", StringUtil.capitalize("Text"));
  }

  @Test
  public void testUncapitalize() {
    assertEquals("text", StringUtil.uncapitalize("Text"));
  }

  @Test
  public void testSplitOnFirstSeparator2() {
    assertEquals(2, StringUtil.splitOnFirstSeparator("Path", 'A').length);
  }

  @Test
  public void testSplitOnFirstSeparator3() {
    assertEquals(2, StringUtil.splitOnFirstSeparator(null, 'A').length);
  }

  @Test
  public void testSplitOnFirstSeparator4() {
    assertEquals(2, StringUtil.splitOnFirstSeparator("Path", 'a').length);
  }

  @Test
  public void testSplitOnFirstSeparator5() {
    assertEquals(2, StringUtil.splitOnFirstSeparator("\\'", '\\').length);
  }

  @Test
  public void testSplitOnLastSeparator2() {
    assertEquals(2, StringUtil.splitOnLastSeparator("Path", 'A').length);
  }

  @Test
  public void testSplitOnLastSeparator3() {
    assertEquals(2, StringUtil.splitOnLastSeparator(null, 'A').length);
  }

  @Test
  public void testSplitOnLastSeparator4() {
    assertEquals(2, StringUtil.splitOnLastSeparator("Path", 'a').length);
  }

  @Test
  public void testSplitOnLastSeparator5() {
    assertEquals(2, StringUtil.splitOnLastSeparator("\\'", '\\').length);
  }

  @Test
  public void testSplitAroundSeparator() {
    assertEquals(2, StringUtil.splitAroundSeparator("Path", 1).length);
    assertEquals(2, StringUtil.splitAroundSeparator("Path", 0).length);
    assertEquals(2, StringUtil.splitAroundSeparator("Path", 3).length);
    assertEquals(2, StringUtil.splitAroundSeparator("Path", -1).length);
  }

  @Test
  public void testStartsWithIgnoreCase() {
    assertFalse(StringUtil.startsWithIgnoreCase("", null));
    assertTrue(StringUtil.startsWithIgnoreCase(null, null));
    assertFalse(StringUtil.startsWithIgnoreCase(null, ""));
    assertTrue(StringUtil.startsWithIgnoreCase("", ""));
    assertTrue(StringUtil.startsWithIgnoreCase("A", ""));
    assertTrue(StringUtil.startsWithIgnoreCase("Alice", "Alice"));
    assertTrue(StringUtil.startsWithIgnoreCase("Alice", "aLICE"));
    assertTrue(StringUtil.startsWithIgnoreCase("Alice", "Ali"));
    assertTrue(StringUtil.startsWithIgnoreCase("Alice", "aLI"));
    assertTrue(StringUtil.startsWithIgnoreCase("aLICE", "Ali"));
    assertFalse(StringUtil.startsWithIgnoreCase("Ali", "Alice"));
    assertFalse(StringUtil.startsWithIgnoreCase("Text", "Prefix"));
    assertFalse(StringUtil.startsWithIgnoreCase(null, "Prefix"));
    assertFalse(StringUtil.startsWithIgnoreCase("Text", null));
    assertTrue(StringUtil.startsWithIgnoreCase(null, null));
  }

  @Test
  public void testEndsWithIgnoreCase() {
    assertFalse(StringUtil.endsWithIgnoreCase("", null));
    assertTrue(StringUtil.endsWithIgnoreCase(null, null));
    assertFalse(StringUtil.endsWithIgnoreCase(null, ""));
    assertTrue(StringUtil.endsWithIgnoreCase("", ""));
    assertTrue(StringUtil.endsWithIgnoreCase("A", ""));
    assertTrue(StringUtil.endsWithIgnoreCase("Alice", "Alice"));
    assertTrue(StringUtil.endsWithIgnoreCase("Alice", "aLICE"));
    assertTrue(StringUtil.endsWithIgnoreCase("Alice", "ice"));
    assertTrue(StringUtil.endsWithIgnoreCase("Alice", "ICE"));
    assertTrue(StringUtil.endsWithIgnoreCase("aLICE", "ice"));
    assertFalse(StringUtil.endsWithIgnoreCase("ice", "Alice"));
    assertFalse(StringUtil.endsWithIgnoreCase("Text", "Suffix"));
    assertFalse(StringUtil.endsWithIgnoreCase(null, "Suffix"));
    assertFalse(StringUtil.endsWithIgnoreCase("Text", null));
    assertTrue(StringUtil.endsWithIgnoreCase(null, null));
  }

  @Test
  public void testNormalizeName() {
    assertNull(StringUtil.normalizeName(null));
    assertEquals("", StringUtil.normalizeName(""));
    assertEquals("", StringUtil.normalizeName("   \t \r \n   "));
    assertEquals("Alice", StringUtil.normalizeName("Alice"));
    assertEquals("Alice", StringUtil.normalizeName("ALICE"));
    assertEquals("Alice", StringUtil.normalizeName("alice"));
    assertEquals("Alice", StringUtil.normalizeName("aLICE"));
    assertEquals("Alice", StringUtil.normalizeName(" \r\n\tAlice"));
    assertEquals("Alice", StringUtil.normalizeName(" \r\n\tALICE"));
    assertEquals("Alice", StringUtil.normalizeName(" \r\n\talice"));
    assertEquals("Alice", StringUtil.normalizeName(" \r\n\taLICE"));
    assertEquals("Alice Smith", StringUtil.normalizeName(" \r\n\taLICE \r sMITH \n\t "));
    assertEquals("Karl Heinz", StringUtil.normalizeName("karl    heinz"));
    assertEquals("Hans-Georg", StringUtil.normalizeName("hans-georg"));
    assertEquals("Hans - Georg", StringUtil.normalizeName("hans     -    georg"));
    assertEquals("O'Hara", StringUtil.normalizeName("o'hara"));
    assertEquals("Name", StringUtil.normalizeName("Name"));
    assertNull(StringUtil.normalizeName(null));
    assertEquals("", StringUtil.normalizeName(""));
    assertEquals("'", StringUtil.normalizeName("'"));
    assertEquals("\\F", StringUtil.normalizeName("\\f"));
  }

  @Test
  public void testSplitOnFirstSeparator() {
    checkSplitFirst(null, null, null);
    checkSplitFirst("x", null, "x");
    checkSplitFirst("x", "", "x=");
    checkSplitFirst("", "y", "=y");
    checkSplitFirst("x", "y", "x=y");
    checkSplitFirst("x", "(y=z)", "x=(y=z)");
  }

  @Test
  public void testSplitOnLastSeparator() {
    checkSplitLast(null, null, null);
    checkSplitLast(null, "c", "c");
    checkSplitLast("b", "", "b.");
    checkSplitLast("", "c", ".c");
    checkSplitLast("b", "c", "b.c");
    checkSplitLast("a.b", "c", "a.b.c");
  }

  @Test
  public void testConcat() {
    assertEquals("", StringUtil.concat('.', (String[]) null));
    assertEquals("", StringUtil.concat('.', null, null));
    assertEquals("A", StringUtil.concat('.', "A"));
    assertEquals("A", StringUtil.concat('.', null, "A"));
    assertEquals("A", StringUtil.concat('.', "A", null));
    assertEquals("A.B", StringUtil.concat('.', "A", null, "B"));
    assertEquals("A-B", StringUtil.concat('-', "A", null, "B"));
    assertEquals("AB", StringUtil.concat(null, "A", null, "B"));
    assertEquals("Parts", StringUtil.concat('A', "Parts"));
    assertEquals("", StringUtil.concat('A', null));
    assertEquals("", StringUtil.concat('A', ""));
    assertEquals("", StringUtil.concat('A', null));
    assertEquals("Parts\u0000Parts", StringUtil.concat('\u0000', "Parts", "Parts"));
    assertEquals("PartsParts", StringUtil.concat(null, "Parts", "Parts"));
  }

  @Test
  public void testEqualsIgnoreCase() {
    assertFalse(StringUtil.equalsIgnoreCase("S1", "S2"));
    assertFalse(StringUtil.equalsIgnoreCase(null, "S2"));
    assertTrue(StringUtil.equalsIgnoreCase((String) null, null));
    assertFalse(StringUtil.equalsIgnoreCase(new String[] {"A1"}, new String[] {"A2"}));
    assertFalse(StringUtil.equalsIgnoreCase(new String[] {null}, new String[] {"A2"}));
    assertFalse(StringUtil.equalsIgnoreCase(new String[] {}, new String[] {"A2"}));
    assertFalse(StringUtil.equalsIgnoreCase(new String[] {"A1"}, new String[] {}));
    assertTrue(StringUtil.equalsIgnoreCase(new String[] {null}, new String[] {null}));
  }

  @Test
  public void testEqualsIgnoreCase2() {
    HashSet<String> set1 = new HashSet<>();
    assertTrue(StringUtil.equalsIgnoreCase(set1, new HashSet<>()));
  }

  @Test
  public void testEqualsIgnoreCase3() {
    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("E");
    assertFalse(StringUtil.equalsIgnoreCase(stringSet, new HashSet<>()));
  }

  @Test
  public void testEqualsIgnoreCase4() {
    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("E");
    HashSet<String> stringSet1 = new HashSet<>();
    stringSet1.add("E");
    assertTrue(StringUtil.equalsIgnoreCase(stringSet, stringSet1));
  }

  @Test
  public void testEqualsIgnoreCase5() {
    HashSet<String> stringSet = new HashSet<>();
    stringSet.add(null);
    HashSet<String> stringSet1 = new HashSet<>();
    stringSet1.add("E");
    assertFalse(StringUtil.equalsIgnoreCase(stringSet, stringSet1));
  }

  @Test
  public void testEqualsIgnoreCase6() {
    HashSet<String> stringSet = new HashSet<>();
    stringSet.add(null);
    HashSet<String> stringSet1 = new HashSet<>();
    stringSet1.add(null);
    assertTrue(StringUtil.equalsIgnoreCase(stringSet, stringSet1));
  }

  @Test
  public void testContainsIgnoreCase() {
    assertFalse(StringUtil.containsIgnoreCase("Searched Word", new ArrayList<>()));
    assertFalse(StringUtil.containsIgnoreCase("Searched Word", new String[] {"Words"}));
    assertTrue(StringUtil.containsIgnoreCase("", new String[] {""}));
  }

  @Test
  public void testContainsIgnoreCase2() {
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("E");
    assertFalse(StringUtil.containsIgnoreCase("Searched Word", stringList));
  }

  @Test
  public void testContainsIgnoreCase3() {
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("");
    assertTrue(StringUtil.containsIgnoreCase("", stringList));
  }

  @Test
  public void testReplaceTokens() {
    assertEquals("A(alpha,bravo)", StringUtil.replaceTokens("A(XX,XX)", "XX", "alpha", "bravo"));
    assertEquals("Src", StringUtil.replaceTokens("Src", "ABC123", "value"));
    assertEquals("", StringUtil.replaceTokens("", "ABC123", "value"));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> StringUtil.replaceTokens("Src", "", "value"));
  }

  @Test
  public void testEscape() {
    assertNull(StringUtil.escape(null));
    assertEquals("", StringUtil.escape(""));
    assertEquals("ABCD", StringUtil.escape("ABCD"));
    assertEquals("'\"A\\rB\\nC\\tD\"'", StringUtil.escape("'\"A\rB\nC\tD\"'"));
    assertEquals("\\'\"A\\rB\\nC\\tD\"\\'", StringUtil.escape("'\"A\rB\nC\tD\"'", true, false));
    assertEquals("'\\\"A\\rB\\nC\\tD\\\"'", StringUtil.escape("'\"A\rB\nC\tD\"'", false, true));
    assertEquals("\\'\\\"A\\rB\\nC\\tD\\\"\\'", StringUtil.escape("'\"A\rB\nC\tD\"'", true, true));
    assertEquals("\\f\\u000B", StringUtil.escape("\f\u000B"));
    assertEquals("\\u0007\\u0008", StringUtil.escape("\u0007\u0008")); // Non-Java escapes
    assertEquals("Text", StringUtil.escape("Text"));
    assertNull(StringUtil.escape(null));
    assertEquals("Text", StringUtil.escape("Text", true, true));
    assertNull(StringUtil.escape(null, true, true));
    assertEquals("Text", StringUtil.escape("Text", false, true));
    assertEquals("Text", StringUtil.escape("Text", true, false));
  }

  @Test
  public void testUnescape() {
    assertNull(StringUtil.unescape(null));
    assertEquals("", StringUtil.unescape(""));
    assertEquals("'\"", StringUtil.unescape("\\'\\\"")); // Java quote escapes
    assertEquals("ABCD", StringUtil.unescape("ABCD"));
    assertEquals("'A\rB\nC\tD\"", StringUtil.unescape("\\'A\\rB\\nC\\tD\\\""));
    assertEquals("C:\\temp", StringUtil.unescape("C:\\\\temp")); // testing bug #2879250
    assertEquals("\f\u000Bxxx", StringUtil.unescape("\\f\\u000Bxxx"));
    assertEquals("\u0007\u0008", StringUtil.unescape("\\a\\b")); // Non-Java escapes
    assertEquals("Text", StringUtil.unescape("Text"));
    assertNull(StringUtil.unescape(null));
    assertEquals("'", StringUtil.unescape("\\'"));
    assertEquals("\f", StringUtil.unescape("\\f"));
  }

  @Test
  public void testEmptyToNull() {
    assertEquals(" a ", StringUtil.emptyToNull(" a "));
    assertNull(StringUtil.emptyToNull(null));
    assertNull(StringUtil.emptyToNull(""));
    assertNull(StringUtil.emptyToNull("   "));
    assertEquals("S", StringUtil.emptyToNull("S"));
    assertNull(StringUtil.emptyToNull(null));
    assertNull(StringUtil.emptyToNull(""));
    assertEquals("\\'", StringUtil.emptyToNull("\\'"));
  }

  @Test
  public void testTrimmedEmptyToNull() {
    assertEquals("S", StringUtil.trimmedEmptyToNull("S"));
    assertNull(StringUtil.trimmedEmptyToNull(null));
    assertNull(StringUtil.trimmedEmptyToNull(""));
    assertEquals("\\'", StringUtil.trimmedEmptyToNull("\\'"));
  }

  @Test
  public void testRemoveSection() {
    // check valid settings
    assertEquals("123789", StringUtil.removeSection("123456789", "45", "56"));
    assertEquals("123789", StringUtil.removeSection("123456789", "456", "56"));
    assertEquals("13", StringUtil.removeSection("123", "2", "2"));
    assertEquals("23", StringUtil.removeSection("123", "1", "1"));
    assertEquals("12", StringUtil.removeSection("123", "3", "3"));
    // check invalid setups - they leave the string unmodified
    assertEquals("123456789", StringUtil.removeSection("123456789", "32", "56"));
    assertEquals("123456789", StringUtil.removeSection("123456789", "45", "34"));
    assertEquals("Text", StringUtil.removeSection("Text", "Begin Mark", "End Mark"));
    assertNull(StringUtil.removeSection(null, "Begin Mark", "End Mark"));
    assertEquals("", StringUtil.removeSection("", "Begin Mark", "End Mark"));
    assertEquals("Text", StringUtil.removeSection("Text", "", "End Mark"));
    assertEquals("Text", StringUtil.removeSection("Text", "Begin Mark", ""));
    assertEquals("Text", StringUtil.removeSection("Text", "", ""));
    assertEquals("'", StringUtil.removeSection("'", "'", ""));
  }

  @Test
  public void testNormalizeLineSeparators() {
    // no line sep
    assertNull(StringUtil.normalizeLineSeparators(null, "\r\n"));
    assertEquals("", StringUtil.normalizeLineSeparators("", "\r\n"));
    assertEquals("abc", StringUtil.normalizeLineSeparators("abc", "\r\n"));
    // \r\n
    assertEquals("\r\n", StringUtil.normalizeLineSeparators("\r", "\r\n"));
    assertEquals("\r\n", StringUtil.normalizeLineSeparators("\n", "\r\n"));
    assertEquals("\r\n", StringUtil.normalizeLineSeparators("\r\n", "\r\n"));
    assertEquals("\r\nx\r\n", StringUtil.normalizeLineSeparators("\r\nx\r\n", "\r\n"));
    assertEquals("x\r\ny", StringUtil.normalizeLineSeparators("x\r\ny", "\r\n"));
    // \r
    assertEquals("\r", StringUtil.normalizeLineSeparators("\r", "\r"));
    assertEquals("\r", StringUtil.normalizeLineSeparators("\n", "\r"));
    assertEquals("\r", StringUtil.normalizeLineSeparators("\r\n", "\r"));
    assertEquals("\rx\r", StringUtil.normalizeLineSeparators("\r\nx\r\n", "\r"));
    assertEquals("x\ry", StringUtil.normalizeLineSeparators("x\r\ny", "\r"));
    // \n
    assertEquals("\n", StringUtil.normalizeLineSeparators("\r", "\n"));
    assertEquals("\n", StringUtil.normalizeLineSeparators("\n", "\n"));
    assertEquals("\n", StringUtil.normalizeLineSeparators("\r\n", "\n"));
    assertEquals("\nx\n", StringUtil.normalizeLineSeparators("\r\nx\r\n", "\n"));
    assertEquals("x\ny", StringUtil.normalizeLineSeparators("x\r\ny", "\n"));
    assertEquals("Text", StringUtil.normalizeLineSeparators("Text", "Line Separator"));
    assertNull(StringUtil.normalizeLineSeparators(null, "Line Separator"));
    assertEquals("", StringUtil.normalizeLineSeparators("", "Line Separator"));
  }

  @Test
  public void testExtract() {
    assertEquals("b", StringUtil.extract("a[b]c", "[", "]"));
    assertEquals("b", StringUtil.extract("a-b-c", "-", "-"));
    assertEquals("a[b", StringUtil.extract("a[b]c", null, "]"));
    assertEquals("b]c", StringUtil.extract("a[b]c", "[", null));
    assertNull(StringUtil.extract("Text", "Begin Mark", "End Mark"));
    assertNull(StringUtil.extract("", "Begin Mark", "End Mark"));
    assertNull(StringUtil.extract("Text", null, "End Mark"));
    assertNull(StringUtil.extract("Text", "Begin Mark", ""));
    assertNull(StringUtil.extract("'", null, "End Mark"));
    assertEquals("Text", StringUtil.extract("Text", null, null));
    assertEquals("T", StringUtil.extract("Text", null, ""));
  }

  @Test
  public void testBuildPhrase() {
    assertEquals("", StringUtil.buildPhrase());
    assertEquals("", StringUtil.buildPhrase((String[]) null));
    assertEquals("", StringUtil.buildPhrase(""));
    assertEquals("Test", StringUtil.buildPhrase("Test"));
    assertEquals("Test this", StringUtil.buildPhrase("Test", "this"));
    assertEquals("Test this", StringUtil.buildPhrase("Test", null, "this", null));
    assertEquals("Parts", StringUtil.buildPhrase("Parts"));
    assertEquals("", StringUtil.buildPhrase(null));
    assertEquals("", StringUtil.buildPhrase(""));
    assertEquals("", StringUtil.buildPhrase(null));
    assertEquals("Parts Parts", StringUtil.buildPhrase("Parts", "Parts"));
  }

  @Test
  public void testTrimLineSeparators() {
    assertNull(StringUtil.trimLineSeparators(null));
    assertEquals("", StringUtil.trimLineSeparators(""));
    assertEquals("alpha", StringUtil.trimLineSeparators("alpha"));
    assertEquals("alpha", StringUtil.trimLineSeparators("\ralpha\n"));
    assertEquals("alpha\nbeta", StringUtil.trimLineSeparators("\r\nalpha\nbeta\n\r"));
    assertEquals("Text", StringUtil.trimLineSeparators("Text"));
    assertNull(StringUtil.trimLineSeparators(null));
    assertEquals("", StringUtil.trimLineSeparators(""));
  }

  @Test
  public void testRemoveEmptyLines() {
    assertNull(StringUtil.removeEmptyLines(null));
    assertEquals("", StringUtil.removeEmptyLines(""));
    assertEquals("alpha", StringUtil.removeEmptyLines("alpha"));
    assertEquals("alpha\nbeta", StringUtil.removeEmptyLines("alpha\nbeta"));
    assertEquals("alpha\nbeta", StringUtil.removeEmptyLines("alpha\n\n\nbeta"));
    assertEquals("alpha\nbeta", StringUtil.removeEmptyLines("\nalpha\n\n\nbeta\n"));
    assertEquals("", StringUtil.removeEmptyLines(""));
    assertEquals("Text", StringUtil.removeEmptyLines("Text"));
    assertNull(StringUtil.removeEmptyLines(null));
    assertEquals("", StringUtil.removeEmptyLines(""));
  }

  @Test
  public void testSplitLines_withoutLinefeed() {
    assertNull(StringUtil.splitLines(null));
    assertEquals(CollectionUtil.toList(""), StringUtil.splitLines(""));
    assertEquals(CollectionUtil.toList("alpha"), StringUtil.splitLines("alpha"));
  }

  @Test
  public void testSplitLines() {
    List<String> actualSplitLinesResult = StringUtil.splitLines("Text");
    assertEquals(1, actualSplitLinesResult.size());
    assertEquals("Text", actualSplitLinesResult.get(0));
  }

  @Test
  public void testSplitLines2() {
    assertNull(StringUtil.splitLines(null));
  }

  @Test
  public void testSplitLines3() {
    List<String> actualSplitLinesResult = StringUtil.splitLines("");
    assertEquals(1, actualSplitLinesResult.size());
    assertEquals("", actualSplitLinesResult.get(0));
  }

  @Test
  public void testSplitLinesMac() {
    assertEquals(CollectionUtil.toList("alpha", "beta"), StringUtil.splitLines("alpha\nbeta"));
    assertEquals(CollectionUtil.toList("", "alpha", "beta", ""), StringUtil.splitLines("\nalpha\nbeta\n"));
    assertEquals(CollectionUtil.toList("", "alpha", "beta", ""), StringUtil.splitLines("\nalpha\nbeta\n"));
    assertEquals(CollectionUtil.toList("", "alpha", "", "beta", ""), StringUtil.splitLines("\nalpha\n\nbeta\n"));
  }

  @Test
  public void testSplitLinesWin() {
    assertEquals(CollectionUtil.toList("alpha", "beta"), StringUtil.splitLines("alpha\r\nbeta"));
    assertEquals(CollectionUtil.toList("", "alpha", "beta", ""), StringUtil.splitLines("\r\nalpha\r\nbeta\r\n"));
    assertEquals(CollectionUtil.toList("", "alpha", "beta", ""), StringUtil.splitLines("\r\nalpha\r\nbeta\r\n"));
    assertEquals(CollectionUtil.toList("", "alpha", "", "beta", ""), StringUtil.splitLines("\r\nalpha\r\n\r\nbeta\r\n"));
  }

  @Test
  public void testIsLineSeparatorChar() {
    assertTrue(StringUtil.isLineSeparatorChar('\r'));
    assertTrue(StringUtil.isLineSeparatorChar('\n'));
    assertFalse(StringUtil.isLineSeparatorChar('x'));
    assertFalse(StringUtil.isLineSeparatorChar('A'));
    assertTrue(StringUtil.isLineSeparatorChar('\r'));
    assertTrue(StringUtil.isLineSeparatorChar('\n'));
  }

  @Test
  public void testQuoteIfNotNull() {
    assertEquals("'Text'", StringUtil.quoteIfNotNull("Text"));
    assertNull(StringUtil.quoteIfNotNull(null));
  }

  @Test
  public void testGetLeadingWhitespace() {
    assertEquals("", StringUtil.getLeadingWhitespace(""));
    assertEquals("", StringUtil.getLeadingWhitespace("x"));
    assertEquals(" ", StringUtil.getLeadingWhitespace(" "));
    assertEquals(" ", StringUtil.getLeadingWhitespace(" !"));
    assertEquals("\t \t", StringUtil.getLeadingWhitespace("\t \t"));
    assertEquals("\t \t", StringUtil.getLeadingWhitespace("\t \t_"));
    assertEquals("", StringUtil.getLeadingWhitespace("Line"));
    assertEquals("", StringUtil.getLeadingWhitespace(""));
  }

  @Test
  public void testLimitLength() {
    // normal cases
    assertEquals("123456789ABC", StringUtil.limitLength("123456789ABC", 100));
    assertEquals("123456789", StringUtil.limitLength("123456789ABC", 9));
    assertEquals("12", StringUtil.limitLength("123456789ABC", 2));
    assertEquals("1", StringUtil.limitLength("123456789ABC", 1));
    // special cases
    assertEquals("", StringUtil.limitLength("123456789ABC", 0));
    assertEquals("", StringUtil.limitLength("", 100));
    assertEquals("", StringUtil.limitLength("", 0));
    assertNull(StringUtil.limitLength(null, 100));
    assertEquals("Tex", StringUtil.limitLength("Text", 3));
    assertNull(StringUtil.limitLength(null, 3));
    assertEquals("", StringUtil.limitLength("", 3));
  }

  @Test
  public void testLimitLengthWithEllipsis() {
    // normal cases
    assertEquals("123456789ABC", StringUtil.limitLengthWithEllipsis("123456789ABC", 100));
    assertEquals("123456...", StringUtil.limitLengthWithEllipsis("123456789ABC", 9));
    assertEquals("1...", StringUtil.limitLengthWithEllipsis("123456789ABC", 4));
    assertEquals("1..", StringUtil.limitLengthWithEllipsis("123456789ABC", 3));
    assertEquals("1.", StringUtil.limitLengthWithEllipsis("123456789ABC", 2));
    // special cases
    assertEquals(".", StringUtil.limitLengthWithEllipsis("123456789ABC", 1));
    assertEquals("", StringUtil.limitLengthWithEllipsis("123456789ABC", 0));
    assertEquals("", StringUtil.limitLengthWithEllipsis("", 100));
    assertEquals("", StringUtil.limitLengthWithEllipsis("", 0));
    assertNull(StringUtil.limitLengthWithEllipsis(null, 100));
    assertEquals("T..", StringUtil.limitLengthWithEllipsis("Text", 3));
    assertNull(StringUtil.limitLengthWithEllipsis(null, 3));
    assertEquals("", StringUtil.limitLengthWithEllipsis("", 3));
    assertEquals("", StringUtil.limitLengthWithEllipsis("Text", 0));
    assertEquals(".", StringUtil.limitLengthWithEllipsis("Text", 1));
    assertEquals("j...", StringUtil.limitLengthWithEllipsis("java.lang.String", 4));
  }

  @Test
  public void testReplaceOptionalSuffix() {
    assertEquals("Text", StringUtil.replaceOptionalSuffix("Text", "Suffix", "Replacement"));
    assertEquals("TextReplacement", StringUtil.replaceOptionalSuffix("Text", "", "Replacement"));
  }

  @Test
  public void testRemoveSuffixIfPresent() {
    assertEquals("Name", StringUtil.removeSuffixIfPresent("Suffix", "Name"));
    assertEquals("Name", StringUtil.removeSuffixIfPresent("", "Name"));
    assertNull(StringUtil.removeSuffixIfPresent("Suffix", null));
  }

  @Test
  public void testSubstringAfter() {
    assertNull(StringUtil.substringAfter("Marker", "S"));
    assertEquals("S", StringUtil.substringAfter("", "S"));
  }

  @Test
  public void testSubstringBefore() {
    assertNull(StringUtil.substringBefore("Marker", "S"));
    assertEquals("", StringUtil.substringBefore("", "S"));
  }

  @Test
  public void testMaxLength() {
    List<Person> list = new ArrayList<>();
    list.add(new Person("Alice", 23));
    list.add(new Person("Bob", 34));
    assertEquals(5, StringUtil.maxLength(list, new FeatureAccessor<>("name"), null));
  }

  @Test
  public void testReplace() {
    String text = "Alice, bob and Charly";
    // test matching caps
    Map<String, String> replacements = CollectionUtil.buildMap("Alice", "Annabell", "Bob", "bertie", "Charly", "Curt");
    assertEquals("Annabell, bob and Curt", StringUtil.replace(text, replacements));
    // test other caps
    Map<String, String> lcReplacements = CollectionUtil.buildMap("alice", "Annabell", "bob", "bertie", "charly", "Curt");
    assertEquals("Alice, bertie and Charly", StringUtil.replace(text, lcReplacements));
  }

  @Test
  public void testReplaceIgnoreCase() {
    String text = "Alice, Bob and Charly";
    Map replacements = CollectionUtil.buildMap("ALICE", "Annabell", "BoB", "bertie", "ChArlY", "Curt");
    // test retaining caps
    assertEquals("Annabell, bertie and Curt", StringUtil.replaceIgnoreCase(text, replacements, true));
    // test replacing caps
    assertEquals("Annabell, bertie and Curt", StringUtil.replaceIgnoreCase(text, replacements, false));
    // test lower case
    assertEquals("annabell, bertie and curt", StringUtil.replaceIgnoreCase(text.toLowerCase(), replacements, true));
    // test upper case
    assertEquals("ANNABELL, BERTIE AND CURT", StringUtil.replaceIgnoreCase(text.toUpperCase(), replacements, true));
  }

  @Test(expected = IllegalArgumentError.class)
  public void testWithLineBreaks_maxWidth_zero() {
    StringUtil.withLineBreaks("Alice Bob Charly", 0);
  }

  @Test
  public void testWithLineBreaks() {
    assertEquals("Alice Bob Charly", StringUtil.withLineBreaks("Alice Bob Charly", 100));
    /* TODO make this work
    assertEquals("Alice Bob \nCharly", StringUtil.withLineBreaks("Alice Bob Charly", 9));
    assertEquals("Alice \nBob \nCharly", StringUtil.withLineBreaks("Alice Bob Charly", 8));
    */
  }

  // helpers ---------------------------------------------------------------------------------------------------------

  private static void checkSplitFirst(String parent, String child, String path) {
    assertArrayEquals(ArrayUtil.buildObjectArrayOfType(String.class, parent, child), StringUtil.splitOnFirstSeparator(path, '='));
  }

  private static void checkSplitLast(String parent, String child, String path) {
    assertArrayEquals(ArrayUtil.buildObjectArrayOfType(String.class, parent, child), StringUtil.splitOnLastSeparator(path, '.'));
  }

}
