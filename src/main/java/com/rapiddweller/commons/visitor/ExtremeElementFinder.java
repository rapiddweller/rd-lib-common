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
package com.rapiddweller.commons.visitor;

import com.rapiddweller.commons.ComparableComparator;
import com.rapiddweller.commons.Element;
import com.rapiddweller.commons.Visitor;

import java.util.Comparator;

/**
 * Utility that navigates Elements with the Visitor Pattern and 
 * uses a Comparator for determining a minimum or maximum value in the graph.
 * Created: 04.02.2007 08:50:13
 * @param <E> the type of the elements to examine
 * @author Volker Bergmann
 */
public class ExtremeElementFinder<E> {

    public static <E extends Comparable<E>> E findMax(Element<E> root) {
        return findMax(root, new ComparableComparator<>());
    }

    public static <E> E findMax(Element<E> root, Comparator<E> comparator) {
        return findExtreme(root, comparator, 1);
    }

    public static <E extends Comparable<E>> E findMin(Element<E> root) {
        return findMin(root, new ComparableComparator<>());
    }

    public static <E> E findMin(Element<E> root, Comparator<E> comparator) {
        return findExtreme(root, comparator, -1);
    }

    private static <E> E findExtreme(Element<E> root, Comparator<E> comparator, int extreme) {
        ExtremeVisitor<E> visitor = new ExtremeVisitor<>(comparator, extreme);
        root.accept(visitor);
        return visitor.extremeElement;
    }

    static final class ExtremeVisitor<E> implements Visitor<E> {

        private final Comparator<E> comparator;
        private final int extreme;
        E extremeElement;

        public ExtremeVisitor(Comparator<E> comparator, int extreme) {
            this.comparator = comparator;
            this.extreme = extreme;
            this.extremeElement = null;
        }

        @Override
		public <C extends E>void visit(C element) {
            if (extremeElement == null || comparator.compare(element, extremeElement) == extreme)
                extremeElement = element;
        }
    }
}
