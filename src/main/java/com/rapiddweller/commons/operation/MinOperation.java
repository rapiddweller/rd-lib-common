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
package com.rapiddweller.commons.operation;

import com.rapiddweller.commons.ComparableComparator;
import com.rapiddweller.commons.Operation;

import java.util.Comparator;

/**
 * Returns the minimum of two values. If a Comparaotr is provided, that one is used,
 * else it is assumed that E implements Comparable.
 * Created: 03.08.2007 07:40:14
 * @param <E> the argument and result type of the operation
 * @author Volker Bergmann
 */
public class MinOperation<E> implements Operation<E, E> {

    private final Comparator<E> comparator;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MinOperation() {
        this(new ComparableComparator());
    }

    public MinOperation(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @SafeVarargs
    @Override
	public final E perform(E... args) {
        if (args.length == 0)
            return null;
        E result = args[0];
        for (int i = 1; i < args.length; i++)
            if (comparator.compare(result, args[i]) > 0)
                result = args[i];
        return result;
    }
    
}
