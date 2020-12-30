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
package com.rapiddweller.common.iterator;

import com.rapiddweller.common.HeavyweightIterator;
import com.rapiddweller.common.HeavyweightTypedIterable;

/**
 * {@link Iterable} proxy which skips the first data row.
 * Created: 19.07.2011 09:04:03
 * @param <T> the type to iterate
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class HeadSkippingIterable<T> implements HeavyweightTypedIterable<T> {
	
	private final HeavyweightTypedIterable<T> source;

	public HeadSkippingIterable(HeavyweightTypedIterable<T> source) {
		this.source = source;
	}

	@Override
	public Class<T> getType() {
		return source.getType();
	}

	@Override
	public HeavyweightIterator<T> iterator() {
		HeavyweightIterator<T> result = source.iterator();
		if (result.hasNext())
			result.next();
		return result;
	}

}
