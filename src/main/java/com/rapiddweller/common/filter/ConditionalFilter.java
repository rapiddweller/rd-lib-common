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
package com.rapiddweller.common.filter;

import com.rapiddweller.common.Condition;
import com.rapiddweller.common.Filter;

/**
 * Filter that evaluates a Condition on each candidate.
 * Created: 16.06.2007 12:30:26
 * @param <E> the type of objects to filter
 * @author Volker Bergmann
 */
public class ConditionalFilter<E> implements Filter<E> {

    private final Condition<E> condition;

    public ConditionalFilter(Condition<E> condition) {
        this.condition = condition;
    }

    @Override
	public boolean accept(E candidate) {
        return condition.evaluate(candidate);
    }
}
