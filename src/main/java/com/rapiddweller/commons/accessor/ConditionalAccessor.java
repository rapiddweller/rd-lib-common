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
package com.rapiddweller.commons.accessor;

import com.rapiddweller.commons.Accessor;

/**
 * Evaluates a boolean 'condition' accessor and, depending on the result, calls one of two other accessors.
 * Created: 28.02.2013 16:17:04
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class ConditionalAccessor<C, V> implements Accessor<C, V> {
	
	private final Accessor<C, Boolean> condition;
	private final Accessor<C, V> trueAccessor;
	private final Accessor<C, V> falseAccessor;
	
	public ConditionalAccessor(Accessor<C, Boolean> condition,
			Accessor<C, V> trueAccessor, Accessor<C, V> falseAccessor) {
		this.condition = condition;
		this.trueAccessor = trueAccessor;
		this.falseAccessor = falseAccessor;
	}

	@Override
	public V getValue(C target) {
		if (condition.getValue(target))
			return trueAccessor.getValue(target);
		else
			return falseAccessor.getValue(target);
	}

}
