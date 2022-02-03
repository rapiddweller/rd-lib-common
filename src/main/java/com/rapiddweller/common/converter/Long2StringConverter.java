/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

import java.util.Date;
import java.util.TimeZone;

/**
 * Converts {@link Long} values to {@link String}s.<br/><br/>
 * Created: 03.02.2022 17:49:51
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class Long2StringConverter extends ThreadSafeConverter<Long, String> {

	public Long2StringConverter() {
		super(Long.class, String.class);
	}

	@Override
	public Class<String> getTargetType() {
		return String.class;
	}

	@Override
	public String convert(Long target) throws ConversionException {
		if (target == null) {
			return null;
		}
		return String.valueOf(target);
	}

}
