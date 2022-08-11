/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.converter;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link OffsetDateTime2TimestampConverter}.<br/><br/>
 * Created: 07.08.2022 21:15:44
 * @author Volker Bergmann
 * @since 2.0.0
 */
public class OffsetDateTime2TimestampConverterTest extends AbstractDateConverterTest {

	private final OffsetDateTime2TimestampConverter defaultConverter = new OffsetDateTime2TimestampConverter();
	private final OffsetDateTime2TimestampConverter nullConverter = new OffsetDateTime2TimestampConverter(null);
	private final OffsetDateTime2TimestampConverter berlinConverter = new OffsetDateTime2TimestampConverter(BERLIN);
	private final OffsetDateTime2TimestampConverter londonConverter = new OffsetDateTime2TimestampConverter(LONDON);
	private final OffsetDateTime2TimestampConverter chicagoConverter = new OffsetDateTime2TimestampConverter(CHICAGO);

	public OffsetDateTime2TimestampConverterTest() {
		super(OffsetDateTime2TimestampConverter.class);
	}

	@Test
	public void testNullValue() {
		assertNull(new OffsetDateTime2TimestampConverter().convert(null));
	}

	@Test
	public void testDefaultConstructionBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, defaultConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testDefaultConstructionChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, defaultConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneNullBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, defaultConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneNullLondon() {
		assertEqualTimestamps(TIMESTAMP_LONDON, nullConverter.convert(ODT_NANOS_LONDON));
	}

	@Test
	public void testZoneNullChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, nullConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testZoneLondon() {
		assertEqualTimestamps(TIMESTAMP_LONDON, londonConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, berlinConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testZoneChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, chicagoConverter.convert(ODT_NANOS_CHICAGO));
	}

	@Test
	public void testBerlinTimeAtZoneChicago() {
		assertEqualTimestamps(TIMESTAMP_CHICAGO, chicagoConverter.convert(ODT_NANOS_BERLIN));
	}

	@Test
	public void testChicagoTimeAtZoneBerlin() {
		assertEqualTimestamps(TIMESTAMP_BERLIN, berlinConverter.convert(ODT_NANOS_CHICAGO));
	}

}
