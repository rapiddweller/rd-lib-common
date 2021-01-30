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

import com.rapiddweller.common.Element;
import com.rapiddweller.common.Filter;
import com.rapiddweller.common.Visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Iterates through a tree for searching items that match a filter.
 * Created: 04.02.2007 11:59:03
 * @author Volker Bergmann
 */
public class FilteredFinder {

    public static <T> Collection<T> find(Element<T> root, Filter<T> filter) {
        HelperVisitor<T> visitor = new HelperVisitor<>(filter);
        root.accept(visitor);
        return visitor.getMatches();
    }

    private static class HelperVisitor<E> implements Visitor<E> {

        private final Filter<E> filter;
        private final List<E> matches;

        public HelperVisitor(Filter<E> filter) {
            this.filter = filter;
            this.matches = new ArrayList<>();
        }

        @Override
		public <C extends E >void visit(C element) {
            if (filter.accept(element))
                matches.add(element);
        }

        public List<E> getMatches() {
            return matches;
        }
    }
}
