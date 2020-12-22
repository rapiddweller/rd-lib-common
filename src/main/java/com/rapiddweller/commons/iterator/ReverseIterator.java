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
package com.rapiddweller.commons.iterator;

/**
 * Iterates through a {@link BidirectionalIterator} in reverse order.
 * Created: 12.05.2007 23:18:08
 * @param <E> the type to iterate
 * @author Volker Bergmann
 */
public class ReverseIterator<E> extends BidirectionalIteratorProxy<E> {

    public ReverseIterator(BidirectionalIterator<E> realIterator) {
        super(realIterator);
    }

    @Override
    public E first() {
        return super.last();
    }

    @Override
    public boolean hasPrevious() {
        return super.hasNext();
    }

    @Override
    public E previous() {
        return super.next();
    }

    @Override
    public E last() {
        return super.first();
    }

    @Override
    public boolean hasNext() {
        return super.hasPrevious();
    }

    @Override
    public E next() {
        return super.previous();
    }

}
