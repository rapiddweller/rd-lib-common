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

/**
 * Converts a java.util.Date to a java.sql.Date.
 * Created: 09.09.2007 12:51:17
 * @author Volker Bergmann
 */
public class UtilDate2SqlDateConverter extends ThreadSafeConverter<Date, java.sql.Date> {

	public UtilDate2SqlDateConverter() {
		super(Date.class, java.sql.Date.class);
	}

    @Override
	public java.sql.Date convert(Date sourceValue) throws ConversionException {
        return new java.sql.Date(sourceValue.getTime());
    }

}
