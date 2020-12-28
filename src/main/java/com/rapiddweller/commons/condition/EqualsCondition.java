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
package com.rapiddweller.commons.condition;

import com.rapiddweller.commons.Condition;
import com.rapiddweller.commons.NullSafeComparator;

/**
 * Condition that requires to be the checked argument to be equal to a prototype.
 * Created: 16.06.2007 12:46:03
 * @param <E> the type of argument to evaluate
 * @author Volker Bergmann
 */
public class EqualsCondition<E> implements Condition<E> {

    private final E reference;

    public EqualsCondition(E reference) {
        this.reference = reference;
    }

    @Override
	public boolean evaluate(E argument) {
        return NullSafeComparator.equals(argument, reference);
    }

}
