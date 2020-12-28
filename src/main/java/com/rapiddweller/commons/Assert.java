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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An assertion utility.
 * Created at 25.04.2008 17:59:43
 * @since 0.4.2
 * @author Volker Bergmann
 */
public class Assert {
	
	private static final double TOLERANCE = 0.00001;
	
	private Assert() {}
	
	public  void end(String text, String end) {
		if (text == null) {
			if (end != null)
				throw new IllegalArgumentException("String is supposed to end with '" + end + ", but is null.");
		} else if (!text.endsWith(end))
			throw new IllegalArgumentException("String is supposed to end with '" + end + ", but is: " + text);
	}

	public static void endIgnoreCase(String text, String end) {
		if (text == null) {
			if (end != null)
				throw new IllegalArgumentException("String is supposed to end with '" + end + ", but is null.");
		} else if (!text.endsWith(end))
			throw new IllegalArgumentException("String is supposed to case-insensitively end with '" + end 
					+ ", but is: " + text);
	}

	public static <T> T notNull(T object, String objectRole) {
		if (object == null)
			throw new IllegalArgumentException(objectRole + " must not be null");
		return object;
	}
	
	public static String notEmpty(String text, String message) {
		if (text == null || text.length() == 0)
			throw new IllegalArgumentException(message);
		return text;
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (collection == null || collection.size() == 0)
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0)
			throw new IllegalArgumentException(message);
	}

    @SuppressWarnings("null")
    public static void equals(Object a1, Object a2, String message) {
		if (a1 == null && a2 == null)
			return;
		else if ((a1 == null && a2 != null) || (a1 != null && a2 == null))
			throw new IllegalArgumentException(message);
		else if (!a1.equals(a2))
			throw new IllegalArgumentException(message);
    }

	@SuppressWarnings("null")
    public static <T> void equals(T[] a1, T[] a2) {
		if (a1 == null && a2 == null)
			return;
		else if ((a1 == null && a2 != null) || (a1 != null && a2 == null))
			throw new IllegalArgumentException("Arrays are not equal, one of them is null");
		else if (a1.length != a2.length)
			throw new IllegalArgumentException("Arrays are not equal, the size differs: [" + 
					ArrayFormat.format(a1) + "] vs. [" + ArrayFormat.format(a2) + ']');
		else if (!Arrays.deepEquals(a1, a2))
			throw new IllegalArgumentException("Arrays are not equal, content differs: [" + 
					ArrayFormat.format(a1) + "] vs. [" + ArrayFormat.format(a2) + ']');
	}
	
	@SuppressWarnings("null")
    public static void equals(byte[] a1, byte[] a2) {
		if (a1 == null && a2 == null)
			return;
		else if ((a1 == null && a2 != null) || (a1 != null && a2 == null))
			throw new IllegalArgumentException("Arrays are not equal, one of them is null");
		else if (a1.length != a2.length)
			throw new IllegalArgumentException("Arrays are not equal, the size differs: [" + 
					ArrayFormat.formatBytes(",", a1) + "] vs. [" + ArrayFormat.formatBytes(",", a2) + ']');
		else if (!Arrays.equals(a1, a2))
			throw new IllegalArgumentException("Arrays are not equal, content differs: [" + 
					ArrayFormat.formatBytes(",", a1) + "] vs. [" + ArrayFormat.formatBytes(",", a2) + ']');
	}

	public static void length(String string, int length) {
		if (string == null || string.length() != length)
			throw new IllegalArgumentException("Unexpected string length: Expected string of length " + length + ", found: " 
					+ (string != null ? string.length() : "null"));
	}
	
	public static void startsWith(String prefix, String string) {
		if (string == null || !string.startsWith(prefix))
			throw new IllegalArgumentException("Expected prefix '" + prefix + "' is missing in: " + string);
	}
	
	public static void endsWith(String suffix, String string) {
		if (string == null || !string.endsWith(suffix))
			throw new IllegalArgumentException("Expected suffix '" + suffix + "' is missing in: " + string);
	}
	
	public static void instanceOf(Object object, Class<?> type, String name) {
		 if (object == null)
			 throw new IllegalArgumentException(name + " is not supposed to be null");
		 if (!type.isAssignableFrom(object.getClass()))
			 throw new IllegalArgumentException(name + " is expected to be of type " + type.getName() + ", but is " + object.getClass());
	}

	public static boolean isTrue(boolean expression, String message) {
		if (!expression)
			throw new IllegalArgumentException(message);
		return expression;
	}

	public static boolean isFalse(boolean expression, String message) {
		if (expression)
			throw new IllegalArgumentException(message);
		return expression;
	}

	public static Object found(Object object, String name) {
		 if (object == null)
			 throw new IllegalArgumentException(name + " not found");
		 return object;
	}

	public static <E extends Number> E notNegative(E value, String role) {
		if (value.doubleValue() < 0)
			 throw new IllegalArgumentException(role + " is less than zero: " + value);
		return value;
	}

	public static <E extends Number> E positive(E value, String role) {
		if (value.doubleValue() <= 0)
			 throw new IllegalArgumentException(role + " is not positive: " + value);
		return value;
	}

	public static <E extends Number> E negative(E value, String role) {
		if (value.doubleValue() >= 0)
			 throw new IllegalArgumentException(role + " is not negative: " + value);
		return value;
	}

	public static <T extends Comparable<T>> void lessOrEqual(T value, T threshold, String role) {
		if (value.compareTo(threshold) > 0)
			 throw new IllegalArgumentException(role + " is expected to be less than or equal to " + threshold + ", but is " + value);
	}

	public static void that(boolean flag, String message) {
		if (!flag)
			throw new IllegalArgumentException(message);
	}

	public static void notEquals(double d1, double d2, String message) {
		if (Math.abs(d1 - d2) < TOLERANCE)
			throw new IllegalArgumentException(message);
	}

	public static <E extends Number> E notZero(E value, String message) {
		if (Math.abs(value.doubleValue()) < TOLERANCE)
			throw new IllegalArgumentException(message);
		return value;
	}

	public static void aNumber(double triggerPrice, String message) {
		if (Double.isNaN(triggerPrice))
			throw new IllegalArgumentException(message);
	}

	public static void uniqueLabels(Labeled[] array, String errmsg) {
		Set<String> labels = new HashSet<>();
		for (Labeled item : array) {
			String label = item.getLabel();
			if (labels.contains(label))
				throw new IllegalArgumentException(errmsg + ": " + label);
			labels.add(label);
		}
	}

}
