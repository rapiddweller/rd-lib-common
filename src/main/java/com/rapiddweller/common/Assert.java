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

package com.rapiddweller.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An assertion utility.
 * Created at 25.04.2008 17:59:43
 *
 * @author Volker Bergmann
 * @since 0.4.2
 */
public class Assert {

  private static final double TOLERANCE = 0.00001;

  private Assert() {
  }

  /**
   * End.
   *
   * @param text the text
   * @param end  the end
   */
  public void end(String text, String end) {
    if (text == null) {
      if (end != null) {
        throw new IllegalArgumentException("String is supposed to end with '" + end + ", but is null.");
      }
    } else if (!text.endsWith(end)) {
      throw new IllegalArgumentException("String is supposed to end with '" + end + ", but is: " + text);
    }
  }

  /**
   * End ignore case.
   *
   * @param text the text
   * @param end  the end
   */
  public static void endIgnoreCase(String text, String end) {
    if (text == null) {
      if (end != null) {
        throw new IllegalArgumentException("String is supposed to end with '" + end + ", but is null.");
      }
    } else if (!text.endsWith(end)) {
      throw new IllegalArgumentException("String is supposed to case-insensitively end with '" + end
          + ", but is: " + text);
    }
  }

  /**
   * Not null t.
   *
   * @param <T>        the type parameter
   * @param object     the object
   * @param objectRole the object role
   * @return the t
   */
  public static <T> T notNull(T object, String objectRole) {
    if (object == null) {
      throw new IllegalArgumentException(objectRole + " must not be null");
    }
    return object;
  }

  /**
   * Not empty string.
   *
   * @param text    the text
   * @param message the message
   * @return the string
   */
  public static String notEmpty(String text, String message) {
    if (text == null || text.length() == 0) {
      throw new IllegalArgumentException(message);
    }
    return text;
  }

  /**
   * Not empty.
   *
   * @param collection the collection
   * @param message    the message
   */
  public static void notEmpty(Collection<?> collection, String message) {
    if (collection == null || collection.size() == 0) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Not empty.
   *
   * @param array   the array
   * @param message the message
   */
  public static void notEmpty(Object[] array, String message) {
    if (array == null || array.length == 0) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Equals.
   *
   * @param a1      the a 1
   * @param a2      the a 2
   * @param message the message
   */
  @SuppressWarnings("null")
  public static void equals(Object a1, Object a2, String message) {
    if (a1 == null && a2 == null) {
      return;
    } else if ((a1 == null && a2 != null) || (a1 != null && a2 == null)) {
      throw new IllegalArgumentException(message);
    } else if (!a1.equals(a2)) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Equals.
   *
   * @param <T> the type parameter
   * @param a1  the a 1
   * @param a2  the a 2
   */
  @SuppressWarnings("null")
  public static <T> void equals(T[] a1, T[] a2) {
    if (a1 == null && a2 == null) {
      return;
    } else if ((a1 == null && a2 != null) || (a1 != null && a2 == null)) {
      throw new IllegalArgumentException("Arrays are not equal, one of them is null");
    } else if (a1.length != a2.length) {
      throw new IllegalArgumentException("Arrays are not equal, the size differs: [" +
          ArrayFormat.format(a1) + "] vs. [" + ArrayFormat.format(a2) + ']');
    } else if (!Arrays.deepEquals(a1, a2)) {
      throw new IllegalArgumentException("Arrays are not equal, content differs: [" +
          ArrayFormat.format(a1) + "] vs. [" + ArrayFormat.format(a2) + ']');
    }
  }

  /**
   * Equals.
   *
   * @param a1 the a 1
   * @param a2 the a 2
   */
  @SuppressWarnings("null")
  public static void equals(byte[] a1, byte[] a2) {
    if (a1 == null && a2 == null) {
      return;
    } else if ((a1 == null && a2 != null) || (a1 != null && a2 == null)) {
      throw new IllegalArgumentException("Arrays are not equal, one of them is null");
    } else if (a1.length != a2.length) {
      throw new IllegalArgumentException("Arrays are not equal, the size differs: [" +
          ArrayFormat.formatBytes(",", a1) + "] vs. [" + ArrayFormat.formatBytes(",", a2) + ']');
    } else if (!Arrays.equals(a1, a2)) {
      throw new IllegalArgumentException("Arrays are not equal, content differs: [" +
          ArrayFormat.formatBytes(",", a1) + "] vs. [" + ArrayFormat.formatBytes(",", a2) + ']');
    }
  }

  /**
   * Length.
   *
   * @param string the string
   * @param length the length
   */
  public static void length(String string, int length) {
    if (string == null || string.length() != length) {
      throw new IllegalArgumentException("Unexpected string length: Expected string of length " + length + ", found: "
          + (string != null ? string.length() : "null"));
    }
  }

  /**
   * Starts with.
   *
   * @param prefix the prefix
   * @param string the string
   */
  public static void startsWith(String prefix, String string) {
    if (string == null || !string.startsWith(prefix)) {
      throw new IllegalArgumentException("Expected prefix '" + prefix + "' is missing in: " + string);
    }
  }

  /**
   * Ends with.
   *
   * @param suffix the suffix
   * @param string the string
   */
  public static void endsWith(String suffix, String string) {
    if (string == null || !string.endsWith(suffix)) {
      throw new IllegalArgumentException("Expected suffix '" + suffix + "' is missing in: " + string);
    }
  }

  /**
   * Instance of.
   *
   * @param object the object
   * @param type   the type
   * @param name   the name
   */
  public static void instanceOf(Object object, Class<?> type, String name) {
    if (object == null) {
      throw new IllegalArgumentException(name + " is not supposed to be null");
    }
    if (!type.isAssignableFrom(object.getClass())) {
      throw new IllegalArgumentException(name + " is expected to be of type " + type.getName() + ", but is " + object.getClass());
    }
  }

  /**
   * Is true boolean.
   *
   * @param expression the expression
   * @param message    the message
   * @return the boolean
   */
  public static boolean isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
    return expression;
  }

  /**
   * Is false boolean.
   *
   * @param expression the expression
   * @param message    the message
   * @return the boolean
   */
  public static boolean isFalse(boolean expression, String message) {
    if (expression) {
      throw new IllegalArgumentException(message);
    }
    return expression;
  }

  /**
   * Found object.
   *
   * @param object the object
   * @param name   the name
   * @return the object
   */
  public static Object found(Object object, String name) {
    if (object == null) {
      throw new IllegalArgumentException(name + " not found");
    }
    return object;
  }

  /**
   * Not negative.
   *
   * @param <E>   the type parameter
   * @param value the value
   * @param role  the role
   */
  public static <E extends Number> void notNegative(E value, String role) {
    if (value.doubleValue() < 0) {
      throw new IllegalArgumentException(role + " is less than zero: " + value);
    }
  }

  /**
   * Positive e.
   *
   * @param <E>   the type parameter
   * @param value the value
   * @param role  the role
   * @return the e
   */
  public static <E extends Number> E positive(E value, String role) {
    if (value.doubleValue() <= 0) {
      throw new IllegalArgumentException(role + " is not positive: " + value);
    }
    return value;
  }

  /**
   * Negative e.
   *
   * @param <E>   the type parameter
   * @param value the value
   * @param role  the role
   * @return the e
   */
  public static <E extends Number> E negative(E value, String role) {
    if (value.doubleValue() >= 0) {
      throw new IllegalArgumentException(role + " is not negative: " + value);
    }
    return value;
  }

  /**
   * Less or equal.
   *
   * @param <T>       the type parameter
   * @param value     the value
   * @param threshold the threshold
   * @param role      the role
   */
  public static <T extends Comparable<T>> void lessOrEqual(T value, T threshold, String role) {
    if (value.compareTo(threshold) > 0) {
      throw new IllegalArgumentException(role + " is expected to be less than or equal to " + threshold + ", but is " + value);
    }
  }

  /**
   * That.
   *
   * @param flag    the flag
   * @param message the message
   */
  public static void that(boolean flag, String message) {
    if (!flag) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Not equals.
   *
   * @param d1      the d 1
   * @param d2      the d 2
   * @param message the message
   */
  public static void notEquals(double d1, double d2, String message) {
    if (Math.abs(d1 - d2) < TOLERANCE) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Not zero e.
   *
   * @param <E>     the type parameter
   * @param value   the value
   * @param message the message
   * @return the e
   */
  public static <E extends Number> E notZero(E value, String message) {
    if (Math.abs(value.doubleValue()) < TOLERANCE) {
      throw new IllegalArgumentException(message);
    }
    return value;
  }

  /**
   * A number.
   *
   * @param triggerPrice the trigger price
   * @param message      the message
   */
  public static void aNumber(double triggerPrice, String message) {
    if (Double.isNaN(triggerPrice)) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Unique labels.
   *
   * @param array  the array
   * @param errmsg the errmsg
   */
  public static void uniqueLabels(Labeled[] array, String errmsg) {
    Set<String> labels = new HashSet<>();
    for (Labeled item : array) {
      String label = item.getLabel();
      if (labels.contains(label)) {
        throw new IllegalArgumentException(errmsg + ": " + label);
      }
      labels.add(label);
    }
  }

}
