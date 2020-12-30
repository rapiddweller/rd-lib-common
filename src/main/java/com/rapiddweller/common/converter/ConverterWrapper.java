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

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.ThreadAware;

/**
 * Parent class for {@link Converter}s that hold a reference to another converter instance.
 * Created: 26.02.2010 17:55:21
 * @param <S> the type to convert from
 * @param <T> the type to convert to
 * @since 0.5.0
 * @author Volker Bergmann
 */
public abstract class ConverterWrapper<S, T> implements ThreadAware, Cloneable {

	protected Converter<S, T> realConverter;

	protected ConverterWrapper(Converter<S, T> realConverter) {
	    this.realConverter = realConverter;
    }

	@Override
	public boolean isParallelizable() {
        return realConverter.isParallelizable();
    }

	@Override
	public boolean isThreadSafe() {
        return realConverter.isParallelizable();
    }

	@Override
	public Object clone() {
	    try {
	        return super.clone();
        } catch (CloneNotSupportedException e) {
	        throw new RuntimeException(e);
        }
	}
        
}