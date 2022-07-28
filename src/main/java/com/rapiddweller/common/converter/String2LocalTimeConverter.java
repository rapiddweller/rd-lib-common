/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.LocalTime;

/**
 * Parses a String as {@link LocalTime}.<br/><br/>
 * Created: 28.07.2022 12:11:50
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class String2LocalTimeConverter extends ThreadSafeConverter<String, LocalTime> {

	public String2LocalTimeConverter() {
		super(String.class, LocalTime.class);
	}

	@Override
	public LocalTime convert(String target) {
		return LocalTime.parse(target);
	}

}
