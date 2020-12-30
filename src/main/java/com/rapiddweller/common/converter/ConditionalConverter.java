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

import com.rapiddweller.common.Condition;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;

/**
 * {@link Converter} implementation which applies another converter only if a condition is fulfilled, 
 * otherwise returns the argument itself.
 * Created: 20.07.2011 18:39:51
 * @since 0.5.9
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class ConditionalConverter extends ConverterWrapper implements Converter {

	protected Condition<Object> condition;

	@SuppressWarnings("unchecked")
	public ConditionalConverter(Condition<Object> condition, Converter realConverter) {
		super(realConverter);
		this.condition = condition;
	}

	@Override
	public Class<?> getSourceType() {
		return Object.class;
	}

	@Override
	public Class<?> getTargetType() {
		return Object.class;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object convert(Object sourceValue) throws ConversionException {
		return (condition.evaluate(sourceValue) ? realConverter.convert(sourceValue) : sourceValue);
	}
	
}
