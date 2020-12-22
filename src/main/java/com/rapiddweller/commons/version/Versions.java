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
package com.rapiddweller.commons.version;

import java.text.ParsePosition;

import com.rapiddweller.commons.ComparableComparator;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.math.Interval;
import com.rapiddweller.commons.math.Intervals;
import com.rapiddweller.commons.math.IntervalsParser;

/**
 * {@link Intervals} implementation for {@link VersionNumber}s.
 * Created: 11.03.2011 10:09:15
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class Versions extends Intervals<VersionNumber> {

	private static final long serialVersionUID = 6258577730893701943L;
	
	private static final ComparableComparator<VersionNumber> VERSION_COMPARATOR = new ComparableComparator<VersionNumber>();

	public static Versions valueOf(String spec) {
		if (StringUtil.isEmpty(spec) || "*".equals(spec.trim()))
			return createUnlimited();
		IntervalsParser<VersionNumber> parser = new IntervalsParser<VersionNumber>(
				new VersionNumberParser(), VERSION_COMPARATOR);
		return (Versions) parser.parseObject(spec, new ParsePosition(0), new Versions());
	}

	public static Versions createUnlimited() {
		Versions result = new Versions();
		result.add(Interval.<VersionNumber>createInfiniteInterval());
		return result;
	}

	public static Versions createSingleVersion(VersionNumber version) {
		Versions result = new Versions();
		result.add(new Interval<VersionNumber>(version, true, version, true, VERSION_COMPARATOR));
		return result;
	}

}
