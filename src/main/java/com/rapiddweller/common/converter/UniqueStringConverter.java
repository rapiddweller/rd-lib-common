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
package com.rapiddweller.common.converter;

import java.util.HashSet;
import java.util.Set;

import com.rapiddweller.common.ConversionException;

/**
 * Assures uniqueness for all processed Strings by appending unique numbers to recurring instances.
 * Created: 24.06.2008 19:41:08
 * @since 0.4.4
 * @author Volker Bergmann
 */
public class UniqueStringConverter extends AbstractConverter<String, String> {
	
	private static final int MAX_TRIES = 10000;
	private final Set<String> usedStrings;

	public UniqueStringConverter() {
		super(String.class, String.class);
		usedStrings = new HashSet<>();
	}

	@Override
	public synchronized String convert(String sourceValue) throws ConversionException {
		String resultValue = sourceValue;
		if (usedStrings.contains(sourceValue)) {
			boolean ok = false;
			for (int i = 0; !ok && i < MAX_TRIES; i++) {
				resultValue = sourceValue + i;
				if (!usedStrings.contains(resultValue)) {
					ok = true;
				}
			}
			if (!ok)
				throw new UnsupportedOperationException("not more than " + MAX_TRIES + " identical Strings can be made unique");
		}
		usedStrings.add(resultValue);
		return resultValue;
	}

	@Override
	public boolean isParallelizable() {
	    return false;
    }

	@Override
	public boolean isThreadSafe() {
	    return true;
    }

}
