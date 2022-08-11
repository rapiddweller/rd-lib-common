/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Parent class for {@link com.rapiddweller.common.Converter}s which convert {@link java.time.ZonedDateTime} values
 * to another type.<br/><br/>
 * Created: 10.08.2022 11:53:43
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractZonedDateTimeConverter<E> extends AbstractZonedConverter<ZonedDateTime, E> {

	protected AbstractZonedDateTimeConverter(Class<E> targetType, ZoneId zone) {
		super(ZonedDateTime.class, targetType, zone);
	}

}
