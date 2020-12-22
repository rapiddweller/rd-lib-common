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

import java.math.BigInteger;

import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.NumberUtil;

/**
 * Converts arbitrary {@link Number}s to {@link BigInteger}s.
 * Created: 15.11.2012 08:27:19
 * @since 0.5.20
 * @author Volker Bergmann
 */
public class Number2BigIntegerConverter extends ThreadSafeConverter<Number, BigInteger> {

	protected Number2BigIntegerConverter() {
		super(Number.class, BigInteger.class);
	}

	@Override
	public BigInteger convert(Number sourceValue) throws ConversionException {
		return NumberUtil.toBigInteger(sourceValue);
	}

}
