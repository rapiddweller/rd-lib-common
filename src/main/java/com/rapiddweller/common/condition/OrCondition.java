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
package com.rapiddweller.common.condition;

import com.rapiddweller.common.Condition;

/**
 * Composite condition that requires at least one sub condition to be true. 
 * If no sub conditions exist, false is returned by default.
 * Created: 04.02.2007 00:45:23
 * @param <E> the type of argument to evaluate
 * @author Volker Bergmann
 */
public class OrCondition<E> extends CompositeCondition<E> {

    @SafeVarargs
    public OrCondition(Condition<E> ... components) {
        super(components);
    }

    @Override
	public boolean evaluate(E argument) {
        for (Condition<E> condition : components)
            if (condition.evaluate(argument))
                return true;
        return false;
    }
}
