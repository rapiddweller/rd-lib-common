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

package com.rapiddweller.common.collection;

import com.rapiddweller.common.NullSafeComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link List} interface which supports
 * individual marking of each list element and retrieval of the
 * marked or unmarked element sub lists.
 * Created: 25.01.2012 17:03:05
 * @param <E> the type of the collection's elements
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class MarkedList<E> extends ListProxy<E> {

  private final List<Boolean> marks;

  public MarkedList(List<E> realList) {
    this(realList, createMarks(realList.size()));
  }

  public MarkedList(List<E> realList, List<Boolean> marks) {
    super(realList);
    this.marks = marks;
  }

  // marker interface ------------------------------------------------------------------------------------------------

  public boolean mark(int index) {
    return marks.set(index, true);
  }

  public boolean isMarked(int index) {
    return marks.get(index);
  }

  public void markAll() {
    Collections.fill(marks, true);
  }

  public boolean unmark(int index) {
    return marks.set(index, false);
  }

  public void unmarkAll() {
    Collections.fill(marks, false);
  }

  public void invertMarks() {
    for (int i = 0; i < marks.size(); i++) {
      marks.set(i, !marks.get(i));
    }
  }

  public List<E> getMarkedElements() {
    List<E> result = new ArrayList<>();
    for (int i = 0; i < realList.size(); i++) {
      if (isMarked(i)) {
        result.add(get(i));
      }
    }
    return result;
  }

  public List<E> getUnmarkedElements() {
    List<E> result = new ArrayList<>();
    for (int i = 0; i < realList.size(); i++) {
      if (!isMarked(i)) {
        result.add(get(i));
      }
    }
    return result;
  }

  // java.util.List overrides ----------------------------------------------------------------------------------------

  @Override
  public boolean add(E element) {
    marks.add(false);
    return super.add(element);
  }

  @Override
  public void add(int index, E element) {
    marks.add(index, false);
    super.add(index, element);
  }

  @Override
  public boolean addAll(Collection<? extends E> elements) {
    for (int i = elements.size(); i > 0; i--) {
      marks.add(false);
    }
    return super.addAll(elements);
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> elements) {
    for (int i = elements.size(); i > 0; i--) {
      marks.add(index, false);
    }
    return super.addAll(index, elements);
  }

  @Override
  public E remove(int index) {
    marks.remove(index);
    return super.remove(index);
  }

  @Override
  public boolean remove(Object element) {
    int index = indexOf(element);
    if (index < 0) {
      return false;
    }
    remove(index);
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> elementsToRemove) {
    boolean removedAny = false;
    for (int index = realList.size() - 1; index >= 0; index--) {
      Object element = realList.get(index);
      for (Object elementToRemove : elementsToRemove) {
        if (NullSafeComparator.equals(element, elementToRemove)) {
          remove(index);
          removedAny = true;
          break;
        }
      }
    }
    return removedAny;
  }

  @Override
  public boolean retainAll(Collection<?> elementsToRetain) {
    boolean changed = false;
    for (int index = realList.size() - 1; index >= 0; index--) {
      Object element = realList.get(index);
      boolean found = false;
      for (Object elementToRetain : elementsToRetain) {
        if (NullSafeComparator.equals(element, elementToRetain)) {
          found = true;
          break;
        }
      }
      if (!found) {
        remove(index);
      }
    }
    return changed;
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return new MarkedList<>(realList.subList(fromIndex, toIndex), marks.subList(fromIndex, toIndex));
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static ArrayList<Boolean> createMarks(int size) {
    ArrayList<Boolean> result = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      result.add(false);
    }
    return result;
  }

}
