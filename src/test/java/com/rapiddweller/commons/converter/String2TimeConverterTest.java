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
package com.rapiddweller.commons.converter;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import com.rapiddweller.commons.TimeUtil;

import org.junit.Test;

/**
 * Tests the String2TimeConverter.
 * Created: 14.03.2008 22:23:51
 * @author Volker Bergmann
 */
public class String2TimeConverterTest extends AbstractConverterTest {
	
	public String2TimeConverterTest() {
	    super(String2TimeConverter.class);
    }

	@Test
    public void testMillis() {
    	checkTimeZones(new Runnable() {
            @Override
			public void run() {
                check("00:00:00.000", 0);
                check("00:00:00.001", 1);
                check("00:00:00.123", 123);
                check("00:00:01.001", 1001);
                check("00:01:00.001", 60001);
                check("01:00:00.001", 3600001);
            }
    	});
    }

	@Test
    public void testSeconds() {
    	checkTimeZones(new Runnable() {
            @Override
			public void run() {
		        check("00:00:00", 0);
		        check("00:00:01", 1000);
		        check("00:01:00", 60000);
		        check("01:01:01", 3661000);
            }
    	});
    }

	@Test
    public void testMinutes() {
    	checkTimeZones(new Runnable() {
            @Override
			public void run() {
		        check("00:00", 0);
		        check("00:01", 60000);
		        check("01:00", 3600000);
            }
    	});
    }
    
    // test helpers ----------------------------------------------------------------------------------------------------

    private static void checkTimeZones(Runnable action) {
	    TimeUtil.runInTimeZone(TimeUtil.GMT,					action);
    	TimeUtil.runInTimeZone(TimeUtil.CENTRAL_EUROPEAN_TIME,	action);
    	TimeUtil.runInTimeZone(TimeUtil.SNGAPORE_TIME,			action);
    	TimeUtil.runInTimeZone(TimeUtil.PACIFIC_STANDARD_TIME,	action);
    }

    protected static void check(String timeString, long expectedLocalMillis) {
    	// Half-hour time zone fix, see http://mail-archives.apache.org/mod_mbox/struts-user/200502.mbox/%3C42158AA9.3050001@gridnode.com%3E
        TimeZone timeZone = TimeZone.getDefault();
		int offset = timeZone.getOffset(0L);
		long expectedUTCMillis = expectedLocalMillis - offset;
		long actualMillis = new String2TimeConverter().convert(timeString).getTime();
		assertEquals(expectedUTCMillis, actualMillis);
    }

}
