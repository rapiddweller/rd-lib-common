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
 * Iterates through another BidirectionalIterator repeatedly. 
 * This is supported forward as well as backward.
 * Created: 12.05.2007 23:21:48
 * @param <E> the type to iterate
 * @author Volker Bergmann
 */
public class CyclicIterator<E> extends BidirectionalIteratorProxy<E> {

    private boolean cyclic;

    public CyclicIterator(BidirectionalIterator<E> realIterator) {
        super(realIterator);
        this.cyclic = true;
    }

    public boolean isCyclic() {
        return cyclic;
    }

    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }

    @Override
    public boolean hasPrevious() {
        return (cyclic || super.hasPrevious());
    }

    @Override
    public boolean hasNext() {
        return (cyclic || super.hasNext());
    }

    @Override
    public E previous() {
        if (super.hasPrevious())
            return super.previous();
        else if (cyclic)
            return super.last();
        else
            throw new IllegalStateException("No element available for previous()");
    }

    @Override
    public E next() {
        if (super.hasNext())
            return super.next();
        else if (cyclic)
            return super.first();
        else
            throw new IllegalStateException("No element available for next()");
    }
}
