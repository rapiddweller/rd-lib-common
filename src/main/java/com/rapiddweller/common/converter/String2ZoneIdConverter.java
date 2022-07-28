/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.ZoneId;

/**
 * Parses a String as {@link ZoneId}.<br/><br/>
 * Created: 28.07.2022 12:14:37
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class String2ZoneIdConverter extends ThreadSafeConverter<String, ZoneId> {

	public String2ZoneIdConverter() {
		super(String.class, ZoneId.class);
	}

	@Override
	public ZoneId convert(String sourceValue) {
		return ZoneId.of(sourceValue);
	}

}
