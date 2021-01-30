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

import com.rapiddweller.common.ArrayBuilder;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class which provides convenience methods related to {@link Filter}s.
 * Created: 05.06.2011 22:58:00
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class FilterUtil {
	
	/** private constructor for preventing instantiation of this utility class. */
	private FilterUtil() { }

	@SafeVarargs
    public static <T> List<T> multiFilter(Collection<T> candidates, Filter<T>... filters) {
		List<T> result = new ArrayList<>();
		for (T candidate : candidates)
			if (acceptedByAll(candidate, filters))
				result.add(candidate);
		return result;
	}
	
	public static <T> List<T> filter(Collection<T> candidates, Filter<T> filter) {
		List<T> result = new ArrayList<>();
		for (T candidate : candidates)
			if (filter == null || filter.accept(candidate))
				result.add(candidate);
		return result;
	}
	
	@SafeVarargs
    public static <T> boolean acceptedByAll(T candidate, Filter<T>... filters) {
		for (Filter<T> filter : filters)
			if (!filter.accept(candidate))
				return false;
		return true;
	}

	public static <T> T findSingleMatch(Collection<T> candidates, Filter<T> filter) {
		T result = null;
		for (T candidate : candidates)
			if (filter.accept(candidate)) {
				if (result == null)
					result = candidate;
				else
					throw new ConfigurationError("Found multiple matches: " + candidates);
			}
		return result;
	}

    public static <T> SplitResult<T> split(T[] items, Filter<T> filter) {
        List<T> matches = new ArrayList<>();
        List<T> mismatches = new ArrayList<>();
        for (T item : items) {
            if (filter.accept(item))
                matches.add(item);
            else
                mismatches.add(item);
        }
        return new SplitResult<>(matches, mismatches);
    }

    public static <T> SplitResult<T> split(List<T> list, Filter<T> filter) {
        List<T> matches = new ArrayList<>();
        List<T> mismatches = new ArrayList<>();
        for (T item : list) {
            if (filter.accept(item))
                matches.add(item);
            else
                mismatches.add(item);
        }
        return new SplitResult<>(matches, mismatches);
    }

    @SafeVarargs
    public static <T> List<List<T>> filterGroups(T[] items, Filter<T> ... filters) {
        List<List<T>> results = new ArrayList<>(filters.length);
        for (int i = 0; i < filters.length; i++)
            results.add(new ArrayList<>());
        for (T item : items) {
            for (int i = 0; i < filters.length; i++) {
                Filter<T> filter = filters[i];
                if (filter.accept(item))
                    results.get(i).add(item);
            }
        }
        return results;
    }

	public static <T> T[] filter(T[] items, Filter<T> filter) {
        @SuppressWarnings("unchecked")
		ArrayBuilder<T> result = new ArrayBuilder<>((Class<T>) items[0].getClass(), items.length / 3);
        for (T item : items)
            if (filter.accept(item))
                result.add(item);
        return result.toArray();
	}

}
