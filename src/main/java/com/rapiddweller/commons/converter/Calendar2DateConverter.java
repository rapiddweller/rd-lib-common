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

import java.util.Calendar;
import java.util.Date;

import com.rapiddweller.commons.ConversionException;

/**
 * Converts {@link Calendar} objects to {@link Date}s.
 * Created: 25.02.2010 23:34:02
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Calendar2DateConverter extends ThreadSafeConverter<Calendar, Date> {

    public Calendar2DateConverter() {
        super(Calendar.class, Date.class);
    }

    @Override
	public Date convert(Calendar target) throws ConversionException {
        return target.getTime();
    }

}
