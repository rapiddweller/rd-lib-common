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
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.PrintStream;
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

  private CollectionUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static boolean isEmpty(Collection<?> collection) {
    return (collection == null || collection.isEmpty());
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return (map == null || map.isEmpty());
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
    if (source.isEmpty()) {
      throw ExceptionFactory.getInstance().illegalArgument("For empty collections, a componentType needs to be specified.");
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
      throw ExceptionFactory.getInstance().illegalArgument(
          "Invalid number of arguments. It must be even to represent key-value-pairs");
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
      throw ExceptionFactory.getInstance().illegalArgument(
          "Invalid number of arguments. It must be even to represent key-value-pairs");
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
      throw ExceptionFactory.getInstance().programmerUnsupported(
          "Not a supported collection type: " + collectionType.getName());
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
    return l1.isEmpty();
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

  public static <E> List<List<E>> splitPages(Collection<E> list, int pageSize) {
    List<E> source = (list instanceof List ? (List<E>) list : new ArrayList<E>(list));
    List<List<E>> result = new ArrayList<>();
    for (int i = 0; i < source.size(); i += pageSize)
      result.add(source.subList(i, Math.min(i + pageSize, list.size())));
    return result;
  }

  public static <E> List<E> merge(List<E> list1, List<E> list2) {
    List<E> result = new ArrayList<>(list1);
    result.addAll(list2);
    return result;
  }

  public static <E> Set<E> merge(Set<E> set1, Set<E> set2) {
    Set<E> result = new HashSet<>(set1);
    result.addAll(set2);
    return result;
  }

  public static <E> List<E> intersect(List<E> list1, List<E> list2) {
    List<E> result = new ArrayList<>();
    for (E elem : list1)
      if (list2.contains(elem))
        result.add(elem);
    return result;
  }

  public static void printRecursively(Map<?, ?> map) {
    printRecursively(map, "");
  }

  private static void printRecursively(Map<?, ?> map, String indent) {
    printRecursively(map, indent, System.out);
  }

  private static void printRecursively(Map<?, ?> map, String indent, PrintStream out) {
    if (map == null)
      return;
    for (Map.Entry<?, ?> entry : map.entrySet()) {
      Object value = entry.getValue();
      out.print(indent + entry.getKey() + ": ");
      if (value == null) {
        out.println("null");
      } else if (value instanceof Map) {
        out.println();
        printRecursively((Map<?,?>)value, indent + "  ");
      } else if (List.class.isAssignableFrom(value.getClass())) {
        List<?> list = (List<?>) value;
        out.println('[');
        printRecursively(list, indent + "  ");
        out.println(indent + ']');
      } else {
        out.println(value);
      }
    }
  }

  private static void printRecursively(List<?> list, String indent) {
    printRecursively(list, indent, System.out);
  }

  private static void printRecursively(List<?> list, String indent, PrintStream out) {
    for (int i = 0; i < list.size(); i++) {
      Object value = list.get(i);
      if (value instanceof Map) {
        out.println(indent + "{");
        printRecursively((Map<?,?>) value, indent + "  ");
        out.print(indent + "}");
      } else {
        out.print(indent + value);
      }
      if (i < list.size() - 1) {
        out.println(',');
      }
    }
  }

  public static Object find(Object searchedKey, Map<?, ?> tree, boolean required) {
    for (Map.Entry<?, ?> entry : tree.entrySet()) {
      Object key = entry.getKey();
      Object value = entry.getValue();
      if (searchedKey.equals(key)) {
        return value;
      } else if (value instanceof Map) {
        Object result = find(searchedKey, (Map<?, ?>) value, false);
        if (result != null)
          return result;
      }
    }
    if (required)
      throw ExceptionFactory.getInstance().objectNotFound(
          "No item with key '" + searchedKey + "' found in data structure");
    else
      return null;
  }

  /** Iterates a String-to-String {@link Map}, expecting key values like prefix.sub.property,
   *  strips off the prefixes and groups the remainders in a Map indexed by the prefix.
   *  If no token follows after the prefix, the remainder is set to an empty String.
   */
  public static Map<String, Map<String, String>> stripOffPrefixes(Map<String, String> properties) {
    Map<String, Map<String, String>> result = new HashMap<>();
    for (Map.Entry<String, String> entry : properties.entrySet()) {
      String key = entry.getKey();
      String[] tokens = StringUtil.splitOnFirstSeparator(key, '.');
      String prefix = tokens[0];
      String remainder = (tokens[1] != null ? tokens[1] : "");
      String value = entry.getValue();
      Map<String, String> prefixMap = result.computeIfAbsent(prefix, k -> new HashMap<>());
      prefixMap.put(remainder, value);
    }
    return result;
  }

  public static int recursiveSize(List<?> list) {
    int sum = 0;
    for (Object item : list) {
      if (item instanceof Collection) {
        sum += ((Collection<?>) item).size();
      } else {
        sum++;
      }
    }
    return sum;
  }

}
