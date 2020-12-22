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
package com.rapiddweller.commons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.ArrayUtil;

/**
 * Tests the {@link StringUtil} class.
 * Created: 21.07.2006 17:31:42
 * @since 0.1
 * @author Volker Bergmann
 */
public class StringUtilTest {

	@Test
    public void testEmpty() {
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        assertFalse(StringUtil.isEmpty(" "));
        assertFalse(StringUtil.isEmpty("null"));
    }

	@Test
    public void testSuffix() {
        assertEquals(null, StringUtil.suffix("a", '.'));
        assertEquals(null, StringUtil.suffix("a.", '.'));
        assertEquals("b", StringUtil.suffix("a.b", '.'));
        assertEquals("b", StringUtil.suffix(".b", '.'));
        assertEquals(null, StringUtil.suffix(null, '.'));
    }

	@Test
    public void testLastToken() {
        assertEquals("a", StringUtil.lastToken("a", ','));
        assertEquals(null, StringUtil.lastToken("a,", ','));
        assertEquals("b", StringUtil.lastToken("a,b", ','));
        assertEquals("b", StringUtil.lastToken(",b", ','));
        assertEquals(null, StringUtil.lastToken(null, ','));
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
        assertTrue(Arrays.equals(new String[] { "" }, StringUtil.tokenize("", ',')));
        assertTrue(Arrays.equals(new String[] { "a", "b", "" }, StringUtil.tokenize("a,b,", ',')));
        assertTrue(Arrays.equals(new String[] { "", "b", "c" }, StringUtil.tokenize(",b,c", ',')));
        assertTrue(Arrays.equals(new String[] { "", "b", "" }, StringUtil.tokenize(",b,", ',')));
        assertTrue(Arrays.equals(new String[] { "a", "b", "c" }, StringUtil.tokenize("a,b,c", ',')));
        assertTrue(Arrays.equals(new String[] { "a", "b", "c" }, StringUtil.tokenize("a.b.c", '.')));
        assertTrue(Arrays.equals(new String[] { "a,b,c" }, StringUtil.tokenize("a,b,c", '.')));
    }

	@Test
    public void testNormalize() {
        assertEquals("#Abc!", StringUtil.normalize("#Abc!"));
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
    }

	@Test
    public void testTrimEnd() {
        assertEquals(null, StringUtil.trimEnd(null));
        assertEquals("", StringUtil.trimEnd(""));
        assertEquals("", StringUtil.trimEnd(" \r\n"));
        assertEquals("abc", StringUtil.trimEnd("abc"));
        assertEquals("abc", StringUtil.trimEnd("abc "));
        assertEquals(" abc", StringUtil.trimEnd(" abc"));
        assertEquals(" abc", StringUtil.trimEnd(" abc "));
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
        } catch (IllegalArgumentException e) {
            // this is expected
        }
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
        } catch (IllegalArgumentException e) {
            // this is expected
        }
    }

	@Test
    public void testTrimRight() {
        assertEquals(null, StringUtil.trimRight(null, '0'));
        assertEquals("", StringUtil.trimRight("", '0'));
        assertEquals("", StringUtil.trimRight("0", '0'));
        assertEquals("1", StringUtil.trimRight("1", '0'));
        assertEquals("1", StringUtil.trimRight("10", '0'));
        assertEquals("1", StringUtil.trimRight("1000", '0'));
        assertEquals("01", StringUtil.trimRight("0100", '0'));
    }

	@Test
    public void testTrimLeft() {
        assertEquals(null, StringUtil.trimLeft(null, '0'));
        assertEquals("", StringUtil.trimLeft("", '0'));
        assertEquals("", StringUtil.trimLeft("0", '0'));
        assertEquals("1", StringUtil.trimLeft("1", '0'));
        assertEquals("1", StringUtil.trimLeft("01", '0'));
        assertEquals("1", StringUtil.trimLeft("001", '0'));
        assertEquals("100", StringUtil.trimLeft("0100", '0'));
    }

	@Test
    public void testTrim() {
        assertEquals(null, StringUtil.trim(null, '0'));
        assertEquals("", StringUtil.trim("", '0'));
        assertEquals("", StringUtil.trim("0", '0'));
        assertEquals("1", StringUtil.trim("1", '0'));
        assertEquals("1", StringUtil.trim("10", '0'));
        assertEquals("1", StringUtil.trim("100", '0'));
        assertEquals("1", StringUtil.trim("00100", '0'));
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
    }
    
	@Test
    public void testNormalizeName() {
    	assertEquals(null, StringUtil.normalizeName(null));
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
    }
    
	@Test
    public void testSplitOnFirstSeparator() {
    	checkSplitFirst(null , null, null   );
    	checkSplitFirst("x" , null ,"x"    );
    	checkSplitFirst("x"  , ""  , "x="  );
    	checkSplitFirst(""   , "y" , "=y"  );
    	checkSplitFirst("x"  , "y" , "x=y"  );
    	checkSplitFirst("x", "(y=z)" , "x=(y=z)");
    }
    
	@Test
    public void testSplitOnLastSeparator() {
    	checkSplitLast(null , null, null   );
    	checkSplitLast(null , "c" , "c"    );
    	checkSplitLast("b"  , ""  , "b."  );
    	checkSplitLast(""   , "c" , ".c"  );
    	checkSplitLast("b"  , "c" , "b.c"  );
    	checkSplitLast("a.b", "c" , "a.b.c");
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
    }
    
	@Test
    public void testReplaceTokens() {
    	assertEquals("A(alpha,bravo)", StringUtil.replaceTokens("A(XX,XX)", "XX", "alpha", "bravo"));
    }
    
	@Test
    public void testEscape() {
    	assertEquals(null, StringUtil.escape(null));
    	assertEquals("", StringUtil.escape(""));
    	assertEquals("ABCD", StringUtil.escape("ABCD"));
    	assertEquals("'\"A\\rB\\nC\\tD\"'", StringUtil.escape("'\"A\rB\nC\tD\"'"));
    	assertEquals("\\'\"A\\rB\\nC\\tD\"\\'", StringUtil.escape("'\"A\rB\nC\tD\"'", true, false));
    	assertEquals("'\\\"A\\rB\\nC\\tD\\\"'", StringUtil.escape("'\"A\rB\nC\tD\"'", false, true));
    	assertEquals("\\'\\\"A\\rB\\nC\\tD\\\"\\'", StringUtil.escape("'\"A\rB\nC\tD\"'", true, true));
    	assertEquals("\\f\\u000B", StringUtil.escape("\f\u000B"));
    	assertEquals("\\u0007\\u0008", StringUtil.escape("\u0007\u0008")); // Non-Java escapes
    }
    
	@Test
    public void testUnescape() {
    	assertEquals(null, StringUtil.unescape(null));
    	assertEquals("", StringUtil.unescape(""));
    	assertEquals("'\"", StringUtil.unescape("\\'\\\"")); // Java quote escapes
    	assertEquals("ABCD", StringUtil.unescape("ABCD"));
    	assertEquals("'A\rB\nC\tD\"", StringUtil.unescape("\\'A\\rB\\nC\\tD\\\""));
    	assertEquals("C:\\temp", StringUtil.unescape("C:\\\\temp")); // testing bug #2879250
    	assertEquals("\f\u000Bxxx", StringUtil.unescape("\\f\\u000Bxxx"));
    	assertEquals("\u0007\u0008", StringUtil.unescape("\\a\\b")); // Non-Java escapes
    }
    
	@Test
    public void testEmptyToNull() {
    	assertEquals(" a ", StringUtil.emptyToNull(" a "));
    	assertEquals(null, StringUtil.emptyToNull(null));
    	assertEquals(null, StringUtil.emptyToNull(""));
    	assertEquals(null, StringUtil.emptyToNull("   "));
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
    }
	
	@Test
	public void testNormalizeLineSeparators() {
		// no line sep
		assertEquals(null, StringUtil.normalizeLineSeparators(null, "\r\n"));
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
	}
	
	@Test
	public void testExtract() {
		assertEquals("b", StringUtil.extract("a[b]c", "[", "]"));
		assertEquals("b", StringUtil.extract("a-b-c", "-", "-"));
		assertEquals("a[b", StringUtil.extract("a[b]c", null, "]"));
		assertEquals("b]c", StringUtil.extract("a[b]c", "[", null));
	}
    
	@Test
	public void testBuildPhrase() {
		assertEquals("", StringUtil.buildPhrase());
		assertEquals("", StringUtil.buildPhrase((String[]) null));
		assertEquals("", StringUtil.buildPhrase(""));
		assertEquals("Test", StringUtil.buildPhrase("Test"));
		assertEquals("Test this", StringUtil.buildPhrase("Test", "this"));
		assertEquals("Test this", StringUtil.buildPhrase("Test", null, "this", null));
	}
	
	@Test
	public void testTrimLineSeparators() {
		assertEquals(null, StringUtil.trimLineSeparators(null));
		assertEquals("", StringUtil.trimLineSeparators(""));
		assertEquals("alpha", StringUtil.trimLineSeparators("alpha"));
		assertEquals("alpha", StringUtil.trimLineSeparators("\ralpha\n"));
		assertEquals("alpha\nbeta", StringUtil.trimLineSeparators("\r\nalpha\nbeta\n\r"));
    }

	@Test
	public void testRemoveEmptyLines() {
		assertEquals(null, StringUtil.removeEmptyLines(null));
		assertEquals("", StringUtil.removeEmptyLines(""));
		assertEquals("alpha", StringUtil.removeEmptyLines("alpha"));
		assertEquals("alpha\nbeta", StringUtil.removeEmptyLines("alpha\nbeta"));
		assertEquals("alpha\nbeta", StringUtil.removeEmptyLines("alpha\n\n\nbeta"));
		assertEquals("alpha\nbeta", StringUtil.removeEmptyLines("\nalpha\n\n\nbeta\n"));
		assertEquals("", StringUtil.removeEmptyLines(""));
	}
	
	@Test
	public void testSplitLines_withoutLinefeed() {
		assertEquals(null, StringUtil.splitLines(null));
		assertEquals(CollectionUtil.toList(""), StringUtil.splitLines(""));
		assertEquals(CollectionUtil.toList("alpha"), StringUtil.splitLines("alpha"));
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
	}

	@Test
	public void testGetLeadingWhitespace() {
		assertEquals("", StringUtil.getLeadingWhitespace(""));
		assertEquals("", StringUtil.getLeadingWhitespace("x"));
		assertEquals(" ", StringUtil.getLeadingWhitespace(" "));
		assertEquals(" ", StringUtil.getLeadingWhitespace(" !"));
		assertEquals("\t \t", StringUtil.getLeadingWhitespace("\t \t"));
		assertEquals("\t \t", StringUtil.getLeadingWhitespace("\t \t_"));
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
		assertEquals(null, StringUtil.limitLength(null, 100));
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
		assertEquals(null, StringUtil.limitLengthWithEllipsis(null, 100));
	}

	// helpers ---------------------------------------------------------------------------------------------------------

	private static void checkSplitFirst(String parent, String child, String path) {
		assertTrue(Arrays.equals(ArrayUtil.buildObjectArrayOfType(String.class, parent, child), StringUtil.splitOnFirstSeparator(path, '=')));
	}

	private static void checkSplitLast(String parent, String child, String path) {
		assertTrue(Arrays.equals(ArrayUtil.buildObjectArrayOfType(String.class, parent, child), StringUtil.splitOnLastSeparator(path, '.')));
	}
	
}
