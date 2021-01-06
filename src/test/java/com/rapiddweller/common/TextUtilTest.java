package com.rapiddweller.common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class TextUtilTest {
    @Test
    public void testFormatList() {
        assertEquals("", TextUtil.formatList(new ArrayList<>()));
    }

    @Test
    public void testFormatTable() {
        assertEquals("table\n", TextUtil.formatTable(new Object[][]{new Object[]{"table"}}, 'A'));
        assertEquals("65\n", TextUtil.formatTable(new Object[][]{new Object[]{65}}, 'A'));
        assertEquals("tableAtable\n", TextUtil.formatTable(new Object[][]{new Object[]{"table", "table"}}, 'A'));
    }
}

