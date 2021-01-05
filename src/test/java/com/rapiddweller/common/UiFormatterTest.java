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

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

/**
 * Tests the {@link UiFormatter}.<br><br>
 * Created: 22.12.2017 17:20:51
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */

public class UiFormatterTest {

    @Test
    public void test() {
        LocaleUtil.runInLocale(Locale.GERMAN, () -> {
            assertEquals("1,2%", UiFormatter.formatPct(0.0123));
            assertEquals("1.234,6%", UiFormatter.formatPct(12.3456));
        });
    }

    @Test
    public void testFormat() {
        assertEquals("10.00", UiFormatter.format(10.0));
    }

    @Test
    public void testFormat2() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals("Jan 1, 1970",
                UiFormatter.format(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testFormat3() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals("#,##0.0%",
                UiFormatter.format(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()), "#,##0.0%"));
    }

    @Test
    public void testFormatShort() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals("1/1/70",
                UiFormatter.formatShort(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void testFormatPct() {
        assertEquals("1,000.0%", UiFormatter.formatPct(10.0));
    }
}
