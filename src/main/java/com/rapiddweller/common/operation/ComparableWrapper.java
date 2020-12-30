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
package com.rapiddweller.common.operation;

import com.rapiddweller.common.Converter;

/**
 * Wraps a data object with a helper object that can be compared independently of the data object.
 * Created: 26.02.2010 09:17:05
 * @param <E> the type to wrap
 * @since 0.5.0
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComparableWrapper<E> implements Comparable<ComparableWrapper> {

	public final Comparable comparable;
	public final E realObject;
	
	public ComparableWrapper(Comparable comparable, E realObject) {
	    this.comparable = comparable;
	    this.realObject = realObject;
    }

	@Override
	public int compareTo(ComparableWrapper that) {
	    return this.comparable.compareTo(that.comparable);
    }

    public static <T> ComparableWrapper<T>[] wrapAll(T[] realObjects, Converter<T, ?> comparableBuilder) {
		ComparableWrapper<T>[] result = new ComparableWrapper[realObjects.length];
		for (int i = 0; i < realObjects.length; i++) {
			T realObject = realObjects[i];
			Comparable comparable = (Comparable) comparableBuilder.convert(realObject);
			result[i] = new ComparableWrapper<>(comparable, realObject);
		}
		return result;
	}
	
}
