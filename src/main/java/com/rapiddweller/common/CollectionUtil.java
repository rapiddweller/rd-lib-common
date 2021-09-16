/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import com.rapiddweller.common.collection.SortedList;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Provides Collection-related utility methods.
 * Created: 18.12.2006 06:46:24
 * @author Volker Bergmann
 */
public final class CollectionUtil {

  public static boolean isEmpty(Collection<?> collection) {
    return (collection == null || collection.size() == 0);
  }

  @SafeVarargs
  public static <T> List<T> toList(T... array) {
    List<T> result = new ArrayList<>(array.length);
    result.addAll(Arrays.asList(array));
    return result;
  }

  @SafeVarargs
  public static <P, C extends P> List<P> toListOfType(Class<P> type, C... array) {
    List<P> result = new ArrayList<>(array.length);
    result.addAll(Arrays.asList(array));
    return result;
  }

  @SafeVarargs
  public static <T> Set<T> toSet(T... elements) {
    HashSet<T> set = new HashSet<>();
    if (elements != null) {
      set.addAll(Arrays.asList(elements));
    }
    return set;
  }

  @SafeVarargs
  public static <T extends Comparable<T>, U extends T> SortedSet<T> toSortedSet(U... elements) {
    return new TreeSet<>(Arrays.asList(elements));
  }

  @SafeVarargs
  public static <T extends Comparable<T>, U extends T> SortedList<T> toSortedList(U... elements) {
    return new SortedList<>(CollectionUtil.toList(elements), new ComparableComparator<>());
  }

  public static Set<Character> toCharSet(char[] chars) {
    HashSet<Character> set = new HashSet<>();
    if (chars != null) {
      for (char element : chars) {
        set.add(element);
      }
    }
    return set;
  }

  @SafeVarargs
  public static <T, U extends T, C extends Collection<? super T>> void add(C target, U... values) {
    target.addAll(Arrays.asList(values));
  }

  public static <T> List<T> copy(List<? extends T> src, int offset, int length) {
    List<T> items = new ArrayList<>(length);
    for (int i = 0; i < length; i++) {
      items.add(src.get(offset + i));
    }
    return items;
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] toArray(Collection<? extends T> source) {
    if (source.size() == 0) {
      throw new IllegalArgumentException("For empty collections, a componentType needs to be specified.");
    }
    Class<T> componentType = (Class<T>) source.iterator().next().getClass();
    T[] array = (T[]) Array.newInstance(componentType, source.size());
    return source.toArray(array);
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] toArray(Collection<? extends T> source, Class<T> componentType) {
    T[] array = (T[]) Array.newInstance(componentType, source.size());
    return source.toArray(array);
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] extractArray(List<? extends T> source, Class<T> componentType, int fromIndex, int toIndex) {
    T[] array = (T[]) Array.newInstance(componentType, toIndex - fromIndex);
    return source.subList(fromIndex, toIndex).toArray(array);
  }

  public static double[] toDoubleArray(Collection<Double> collection) {
    double[] result = new double[collection.size()];
    int i = 0;
    for (Double d : collection) {
      result[i++] = d;
    }
    return result;
  }

  public static char[] toCharArray(Collection<Character> source) {
    char[] result = new char[source.size()];
    int i = 0;
    for (Character c : source) {
      result[i++] = c;
    }
    return result;
  }

  public static <K, V> Map<K, V> buildMap(K key, V value) {
    Map<K, V> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Map buildMap(Object... keyValuePairs) {
    Map map = new HashMap();
    if (keyValuePairs.length % 2 != 0) {
      throw new IllegalArgumentException("Invalid number of arguments. " +
          "It must be even to represent key-value-pairs");
    }
    for (int i = 0; i < keyValuePairs.length; i += 2) {
      map.put(keyValuePairs[i], keyValuePairs[i + 1]);
    }
    return map;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Map buildOrderedMap(Object... keyValuePairs) {
    Map map = new OrderedMap();
    if (keyValuePairs.length % 2 != 0) {
      throw new IllegalArgumentException("Invalid numer of arguments. " +
          "It must be even to represent key-value-pairs");
    }
    for (int i = 0; i < keyValuePairs.length; i += 2) {
      map.put(keyValuePairs[i], keyValuePairs[i + 1]);
    }
    return map;
  }

  @SuppressWarnings("unchecked")
  public static <T extends Collection<U>, U> T newInstance(Class<T> collectionType) {
    if ((collectionType.getModifiers() & Modifier.ABSTRACT) == 0) {
      return BeanUtil.newInstance(collectionType);
    } else if (Collection.class.equals(collectionType) || List.class.equals(collectionType)) {
      return (T) new ArrayList<>();
    } else if (SortedSet.class.equals(collectionType)) {
      return (T) new TreeSet<>();
    } else if (Set.class.equals(collectionType)) {
      return (T) new TreeSet<>();
    } else {
      throw new UnsupportedOperationException("Not a supported collection type: " + collectionType.getName());
    }
  }

  /** Compares two lists for identical content, accepting different order.
   * @return true if both lists have the same content elements, else false */
  public static <T> boolean equalsIgnoreOrder(List<T> a1, List<T> a2) {
    if (a1 == a2) {
      return true;
    }
    if (a1 == null) {
      return false;
    }
    if (a1.size() != a2.size()) {
      return false;
    }
    List<T> l1 = new ArrayList<>(a1.size());
    l1.addAll(a1);
    for (int i = a1.size() - 1; i >= 0; i--) {
      if (a2.contains(a1.get(i))) {
        l1.remove(i);
      } else {
        return false;
      }
    }
    return l1.size() == 0;
  }

  public static <V> V getCaseInsensitive(String key, Map<String, V> map) {
    V result = map.get(key);
    if (result != null || key == null) {
      return result;
    }
    String lcKey = key.toLowerCase();
    for (Map.Entry<String, V> entry : map.entrySet()) {
      if (entry.getKey() != null && lcKey.equalsIgnoreCase(entry.getKey())) {
        return entry.getValue();
      }
    }
    return null;
  }

  public static <V> boolean containsCaseInsensitive(String key, Map<String, V> map) {
    if (map.containsKey(key)) {
      return true;
    }
    String lcKey = key.toLowerCase();
    for (Map.Entry<String, V> entry : map.entrySet()) {
      if (lcKey.equalsIgnoreCase(entry.getKey())) {
        return true;
      }
    }
    return false;
  }

  public static <T> boolean ofEqualContent(List<T> list, T[] array) {
    if (list == null || list.isEmpty()) {
      return (array == null || array.length == 0);
    }
    if (array == null || list.size() != array.length) {
      return false;
    }
    for (int i = list.size() - 1; i >= 0; i--) {
      if (!NullSafeComparator.equals(list.get(i), array[i])) {
        return false;
      }
    }
    return true;
  }

  public static <T> T lastElement(List<T> list) {
    return list.get(list.size() - 1);
  }

  @SuppressWarnings("rawtypes")
  private static final List EMPTY_LIST = Collections.emptyList();

  @SuppressWarnings("unchecked")
  public static <T> List<T> emptyList() {
    return EMPTY_LIST;
  }

  @SuppressWarnings("unchecked")
  public static <S, T extends S> List<T> extractItemsOfExactType(Class<T> itemType, Collection<S> items) {
    List<T> result = new ArrayList<>();
    for (S item : items) {
      if (itemType == item.getClass()) {
        result.add((T) item);
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <S, T extends S> List<T> extractItemsOfCompatibleType(Class<T> itemType, Collection<S> items) {
    List<T> result = new ArrayList<>();
    for (S item : items) {
      if (itemType.isAssignableFrom(item.getClass())) {
        result.add((T) item);
      }
    }
    return result;
  }

  public static String formatCommaSeparatedList(Collection<?> collection, Character quoteCharacter) {
    StringBuilder builder = new StringBuilder();
    int i = 0;
    for (Object o : collection) {
      if (i > 0) {
        builder.append(", ");
      }
      if (quoteCharacter != null) {
        builder.append(quoteCharacter);
      }
      builder.append(o);
      if (quoteCharacter != null) {
        builder.append(quoteCharacter);
      }
      i++;
    }
    return builder.toString();
  }

  public static void printAll(Collection<?> collection) {
    for (Object o : collection) {
      System.out.println(o);
    }
  }

  public static <E> Collection<E> copy(Iterator<E> src, Collection<E> dst) {
    while (src.hasNext()) {
      dst.add(src.next());
    }
    return dst;
  }

  public static <T> List<T> revert(List<T> values) {
    int iMid = values.size() / 2;
    int iEnd = values.size() - 1;
    for (int i = 0; i < iMid; i++) {
      T tmp = values.get(i);
      int k = iEnd - i;
      values.set(i, values.get(k));
      values.set(k, tmp);
    }
    return values;
  }

  public static <T> Set<T> union(Set<T> src1, Set<T> src2) {
    Set<T> result = new HashSet<>(src1);
    result.addAll(src2);
    return result;
  }

}
