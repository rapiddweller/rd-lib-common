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

import com.rapiddweller.commons.ConversionException;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Converts Date into Calendar Objects and back.
 * Created: 05.08.2007 06:42:16
 * @author Volker Bergmann
 */
public class Date2CalendarConverter extends ThreadSafeConverter<Date, Calendar> {

    public Date2CalendarConverter() {
        super(Date.class, Calendar.class);
    }

    @Override
	public Calendar convert(Date sourceValue) throws ConversionException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(sourceValue);
        return calendar;
    }

}
