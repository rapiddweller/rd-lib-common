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
package com.rapiddweller.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provides convenience methods for {@link Named} objects.
 * Created: 12.08.2010 09:21:46
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NameUtil {

	private NameUtil() { }
	
    public static String[] getNames(Named[] objects) {
    	String[] result = new String[objects.length];
    	for (int i = 0; i < objects.length; i++)
    		result[i] = objects[i].getName();
    	return result;
    }

    public static <T extends Collection<? extends Named>> List<String> getNames(T objects) {
    	List<String> result = new ArrayList<>(objects.size());
    	for (Named object : objects)
    		result.add(object.getName());
    	return result;
    }

    public static <T extends Collection<? extends Named>> String[] getNamesAsArray(T objects) {
    	String[] result = new String[objects.size()];
    	int i = 0;
    	for (Named object : objects)
    		result[i++] = object.getName();
    	return result;
    }

    public static <T extends Named> void orderByName(T[] objects) {
    	Arrays.sort(objects, new NameComparator());
    }

    public static <T extends Named> void orderByName(List<T> objects) {
    	objects.sort(new NameComparator());
    }

	public static int indexOf(String name, List<? extends Named> objects) {
		for (int i = 0; i < objects.size(); i++)
			if (name.equals(objects.get(i).getName()))
				return i;
		return -1;
	}

	public static int indexOf(String name, Named[] objects) {
		for (int i = 0; i < objects.length; i++)
			if (name.equals(objects[i].getName()))
				return i;
		return -1;
	}

	public static void sort(List<? extends Named> namedObjects) {
		namedObjects.sort(new NameComparator());
	}
	
	public static <T extends Named> List<T> find(List<T> list, Filter<String> filter) {
		List<T> result = new ArrayList<>();
		for (T object : list)
			if (filter.accept(object.getName()))
				result.add(object);
		return result;
	}

	public static <T extends Named> T findByName(String name, T[] array) {
		for (T item : array)
			if (NullSafeComparator.equals(item.getName(), name))
				return item;
		return null;
	}
	
	public static <T extends Named> T findByName(String name, List<T> list) {
		for (T item : list)
			if (NullSafeComparator.equals(item.getName(), name))
				return item;
		return null;
	}
	
}
