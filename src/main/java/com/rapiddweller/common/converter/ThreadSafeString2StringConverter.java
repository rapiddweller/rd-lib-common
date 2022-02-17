/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

/**
 * Parent class for {@link com.rapiddweller.common.Converter}
 * which convert one {@link String} to another String.<br/><br/>
 * Created: 15.02.2022 13:40:00
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class ThreadSafeString2StringConverter extends ThreadSafeConverter<String, String> {

	public ThreadSafeString2StringConverter() {
		super(String.class, String.class);
	}

	@Override
	public String convert(String sourceValue) throws ConversionException {
		return sourceValue;
	}

}
