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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Provides convenience methods for {@link Named} objects.
 * Created: 12.08.2010 09:21:46
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class NameUtil {

  private NameUtil() {
  }

  /**
   * Get names string [ ].
   *
   * @param objects the objects
   * @return the string [ ]
   */
  public static String[] getNames(Named[] objects) {
    String[] result = new String[objects.length];
    for (int i = 0; i < objects.length; i++) {
      result[i] = objects[i].getName();
    }
    return result;
  }

  /**
   * Gets names.
   *
   * @param <T>     the type parameter
   * @param objects the objects
   * @return the names
   */
  public static <T extends Collection<? extends Named>> List<String> getNames(T objects) {
    List<String> result = new ArrayList<>(objects.size());
    for (Named object : objects) {
      result.add(object.getName());
    }
    return result;
  }

  /**
   * Get names as array string [ ].
   *
   * @param <T>     the type parameter
   * @param objects the objects
   * @return the string [ ]
   */
  public static <T extends Collection<? extends Named>> String[] getNamesAsArray(T objects) {
    String[] result = new String[objects.size()];
    int i = 0;
    for (Named object : objects) {
      result[i++] = object.getName();
    }
    return result;
  }

  /**
   * Order by name.
   *
   * @param <T>     the type parameter
   * @param objects the objects
   */
  public static <T extends Named> void orderByName(T[] objects) {
    Arrays.sort(objects, new NameComparator());
  }

  /**
   * Order by name.
   *
   * @param <T>     the type parameter
   * @param objects the objects
   */
  public static <T extends Named> void orderByName(List<T> objects) {
    objects.sort(new NameComparator());
  }

  /**
   * Index of int.
   *
   * @param name    the name
   * @param objects the objects
   * @return the int
   */
  public static int indexOf(String name, List<? extends Named> objects) {
    for (int i = 0; i < objects.size(); i++) {
      if (name.equals(objects.get(i).getName())) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Index of int.
   *
   * @param name    the name
   * @param objects the objects
   * @return the int
   */
  public static int indexOf(String name, Named[] objects) {
    for (int i = 0; i < objects.length; i++) {
      if (name.equals(objects[i].getName())) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Sort.
   *
   * @param namedObjects the named objects
   */
  public static void sort(List<? extends Named> namedObjects) {
    namedObjects.sort(new NameComparator());
  }

  /**
   * Find list.
   *
   * @param <T>    the type parameter
   * @param list   the list
   * @param filter the filter
   * @return the list
   */
  public static <T extends Named> List<T> find(List<T> list, Filter<String> filter) {
    List<T> result = new ArrayList<>();
    for (T object : list) {
      if (filter.accept(object.getName())) {
        result.add(object);
      }
    }
    return result;
  }

  /**
   * Find by name t.
   *
   * @param <T>   the type parameter
   * @param name  the name
   * @param array the array
   * @return the t
   */
  public static <T extends Named> T findByName(String name, T[] array) {
    for (T item : array) {
      if (NullSafeComparator.equals(item.getName(), name)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Find by name t.
   *
   * @param <T>  the type parameter
   * @param name the name
   * @param list the list
   * @return the t
   */
  public static <T extends Named> T findByName(String name, List<T> list) {
    for (T item : list) {
      if (NullSafeComparator.equals(item.getName(), name)) {
        return item;
      }
    }
    return null;
  }

}
