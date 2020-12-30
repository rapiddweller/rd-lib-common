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

import java.text.Format;

import com.rapiddweller.common.Converter;

/**
 * Parent class for {@link Converter}s that use a {@link java.text.Format} instance for 
 * parsing Strings or formatting other objects.
 * Created: 26.02.2010 14:52:25
 * @param <S> the object type to convert from
 * @param <T> the object type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public abstract class FormatBasedConverter<S, T> extends AbstractConverter<S, T> implements Cloneable {

	/** The java.text.Format object used for conversion */
    protected Format format;
	private final boolean threadSafe;

	public FormatBasedConverter(Class<S> sourceType, Class<T> targetType, Format format, boolean threadSafe) {
		super(sourceType, targetType);
		this.format = format;
		this.threadSafe = threadSafe;
	}

	@Override
	public boolean isParallelizable() {
        return true;
    }

	@Override
	public boolean isThreadSafe() {
        return threadSafe;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object clone() {
        try {
        	FormatBasedConverter copy = (FormatBasedConverter) super.clone();
        	copy.format = (Format) format.clone();
    		return copy;
        } catch (CloneNotSupportedException e) {
        	throw new RuntimeException(e);
        }
    }

}