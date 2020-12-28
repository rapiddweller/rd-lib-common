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

import com.rapiddweller.commons.ComparableComparator;

/**
 * Reverts the result of another Comparator.
 * Use this for sorting in descending order.
 * Created: 19.06.2005 18:26:47
 * @param <E> the type of objects to be compared
 * @author Volker Bergmann
 */
public class ReverseComparator<E> implements Comparator<E> {

    private final Comparator<E> subject;

    @SuppressWarnings("unchecked")
    public ReverseComparator() {
        this((Comparator<E>)new ComparableComparator<Comparable<E>>());
    }

    public ReverseComparator(Comparator<E> subject) {
        this.subject = subject;
    }

    @Override
	public int compare(E o1, E o2) {
        return (- subject.compare(o1, o2));
    }
    
}
