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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests the Base64Codec.
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class Base64CodecTest {
    
    private static final byte[] ALPHABET_BYTES  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
    private static final String ALPHABET_BASE64 = "QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVo=";

    private static final byte[] ALL_BYTES;
    private static final String ALL_BASE64 = "gIGCg4SFhoeIiYqLjI2Oj5CRkpOUlZaXmJmam5ydnp+goaKjpKWmp" +
    		"6ipqqusra6vsLGys7S1tre4ubq7vL2+v8DBwsPExcbHyMnKy8zNzs/Q0dLT1NXW19jZ2tvc3d7f4OHi4+Tl5uf" +
    		"o6err7O3u7/Dx8vP09fb3+Pn6+/z9/v8AAQIDBAUGBwgJCgsMDQ4PEBESExQVFhcYGRobHB0eHyAhIiMkJSYnK" +
    		"CkqKywtLi8wMTIzNDU2Nzg5Ojs8PT4/QEFCQ0RFRkdISUpLTE1OT1BRUlNUVVZXWFlaW1xdXl9gYWJjZGVmZ2h" +
    		"pamtsbW5vcHFyc3R1dnd4eXp7fH1+fw==";
    
    static {
        ALL_BYTES = new byte[256];
        for (int i = -128; i < 128; i++)
            ALL_BYTES[128 + i] = (byte)i;
    }
    
    // test methods ----------------------------------------------------------------------------------------------------

	@Test
    public void testEncode() {
        assertEquals(ALPHABET_BASE64, Base64Codec.encode(ALPHABET_BYTES));
        assertEquals(ALL_BASE64, Base64Codec.encode(ALL_BYTES));
    }
    
	@Test
    public void testDecode() {
        assertTrue(Arrays.equals(ALPHABET_BYTES, Base64Codec.decode(ALPHABET_BASE64)));
        assertTrue(Arrays.equals(ALL_BYTES, Base64Codec.decode(ALL_BASE64)));
    }
    
	@Test
    public void testRecode() {
        checkRecodeBytes(ALPHABET_BYTES);
        checkRecodeString(ALPHABET_BASE64);
        checkRecodeBytes(ALL_BYTES);
        checkRecodeString(ALL_BASE64);
        checkRecodeString("");
        checkRecodeBytes(new byte[] { 0 });
        checkRecodeBytes(new byte[] { 1 });
        checkRecodeBytes(new byte[] { 1, 2 });
        checkRecodeBytes(new byte[] { 1, 2, 3 });
        checkRecodeBytes(new byte[] { 1, 2, 3, 4 });
        checkRecodeString("AA==");
        checkRecodeString("ABCD");
    }
    
    // private helpers -------------------------------------------------------------------------------------------------

    private static void checkRecodeString(String code) {
        assertEquals(code, Base64Codec.encode(Base64Codec.decode(code)));
    }

    private static void checkRecodeBytes(byte[] bytes) {
        assertTrue(Arrays.equals(bytes, Base64Codec.decode(Base64Codec.encode(bytes))));
    }
    
}
