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

import java.sql.Timestamp;
import java.util.Date;

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.StringUtil;

/**
 * Parses Strings converting them to {@link Timestamp}s.
 * Created at 01.10.2009 10:53:20
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class String2TimestampConverter extends ConverterWrapper<String, Date> 
		implements Converter<String, Timestamp> {

    public String2TimestampConverter() {
        super(new String2DateConverter<>());
    }

	@Override
	public Class<String> getSourceType() {
	    return String.class;
    }

	@Override
	public Class<Timestamp> getTargetType() {
	    return Timestamp.class;
    }

    @Override
	public Timestamp convert(String sourceValue) throws ConversionException {
        if (StringUtil.isEmpty(sourceValue))
            return null;
        
        // separate the String into Date and nano parts 
    	sourceValue = sourceValue.trim();
        String datePart;
        String nanoPart;
        if (sourceValue.contains(".")) {
        	String[] parts = StringUtil.splitOnFirstSeparator(sourceValue, '.');
            datePart = parts[0];
            nanoPart = parts[1];
        } else {
        	datePart = sourceValue;
        	nanoPart = null; 
        }
        
        // calculate date part
        Date date = realConverter.convert(datePart);
        Timestamp result = new Timestamp(date.getTime());
            
        // calculate nano part
        if (!StringUtil.isEmpty(nanoPart)) {
        	nanoPart = StringUtil.padRight(nanoPart, 9, '0');
        	int nanos = Integer.parseInt(nanoPart);
        	result.setNanos(nanos);
        }
		return result;
    }

}
