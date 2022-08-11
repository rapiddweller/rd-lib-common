/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Parent class for {@link com.rapiddweller.common.Converter}s which convert {@link OffsetDateTime} object
 * to representations in other date/time related classes.<br/><br/>
 * Created: 09.08.2022 15:46:13
 * @author Volker Bergmann
 * @since 2.0.0
 */
public abstract class AbstractOffsetDateTimeConverter<E> extends AbstractZonedConverter<OffsetDateTime, E> {

	protected AbstractOffsetDateTimeConverter(Class<E> targetType, ZoneId zone) {
		super(OffsetDateTime.class, targetType, zone);
	}

}
