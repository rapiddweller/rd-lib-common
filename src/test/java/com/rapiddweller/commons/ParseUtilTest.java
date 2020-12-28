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
import junit.framework.AssertionFailedError;

import java.io.PushbackReader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.math.BigInteger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tests the {@link ParseUtil} class.
 * Created: 20.03.2005 16:32:47
 * @author Volker Bergmann
 */
public class ParseUtilTest {

    private static final Logger logger = LogManager.getLogger(ParseUtilTest.class);

    private static final double DELTA = 1e-4;
    
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
    }
    
	@Test
    public void testIsHex() {
    	checkHexChars("0123456789abcdefABCDEF");
    	checkNonHexChars("gG!%-.");
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
    private static <T> void assertEqualArrays(T[] found, T ... expected) {
        if (!Arrays.deepEquals(expected, found))
            throw new AssertionFailedError();
    }

    private static void checkParseEmptyLineSeparatedFile(String[][] expectedOutput, String ... input) throws IOException {
        String[][] output = ParseUtil.parseEmptyLineSeparatedFile(createReader(input));
        logger.debug(Arrays.deepToString(expectedOutput) + " <-> " + Arrays.deepToString(output));
        assertTrue(Arrays.deepEquals(expectedOutput, output));
    }

    private static PushbackReader createReader(String ... lines) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            buffer.append(line);
            if (i < lines.length - 1)
                buffer.append(SystemInfo.getLineSeparator());
        }
        StringReader reader = new StringReader(buffer.toString());
        return new PushbackReader(reader);
    }

    private static void checkHexChars(String chars) {
    	for (int i = 0; i < chars.length(); i++)
    		assertTrue(ParseUtil.isHex(chars.charAt(i)));	
    }

    private static void checkNonHexChars(String chars) {
    	for (int i = 0; i < chars.length(); i++)
    		assertFalse(ParseUtil.isHex(chars.charAt(i)));	
    }
}
