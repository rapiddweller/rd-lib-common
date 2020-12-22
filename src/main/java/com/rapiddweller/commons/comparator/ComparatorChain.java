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
package com.rapiddweller.commons.comparator;

import java.util.Comparator;

/**
 * Delegates comparation to a chain of comparators. 
 * They are evaluated consecutively and the first result that is not zero is returned. 
 * If all used Comparators return zero, zero is returned as the comparation result.
 * You can use this, e.g. for ordering objects by attributes of different priority, 
 * e.g. country, city.
 * Created: 22.05.2007 18:16:15
 * @param <E> the type of objects to be compared
 * @author Volker Bergmann
 */
public class ComparatorChain<E> implements Comparator<E> {

    private Comparator<E>[] comparators;

    public ComparatorChain(Comparator<E> ... comparators) {
        this.comparators = comparators;
    }

    @Override
	public int compare(E e1, E e2) {
        for (Comparator<E> comparator : comparators) {
            int diff = comparator.compare(e1, e2);
            if (diff != 0)
                return diff;
        }
        return 0;
    }
}
