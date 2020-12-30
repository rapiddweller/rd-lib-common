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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link List} implementation which takes wraps another given list 
 * and assures that the list is sorted before any read access.
 * Created: 19.06.2012 07:55:42
 * @param <E> the type of the collection's elements
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class SortedList<E> implements List<E> {

	private boolean sorted;
	private final List<E> baseList;
	private final Comparator<? super E> comparator;
	
	public SortedList(List<E> baseList, Comparator<? super E> comparator) {
		this.baseList = baseList;
		this.comparator = comparator;
		sorted = false;
	}

	@Override
	public boolean add(E e) {
		sorted = false;
		return baseList.add(e);
	}

	@Override
	public void add(int index, E element) {
		sorted = false;
		baseList.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		sorted = false;
		return baseList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		sorted = false;
		return baseList.addAll(index, c);
	}

	@Override
	public void clear() {
		sorted = true;
		baseList.clear();
	}

	@Override
	public boolean contains(Object o) {
		return baseList.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return baseList.containsAll(c);
	}
	
	@Override
	public E get(int index) {
		validate();
		return baseList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		validate();
		return baseList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return baseList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		validate();
		return baseList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		validate();
		return baseList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		validate();
		return baseList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		validate();
		return baseList.listIterator(index);
	}

	@Override
	public E remove(int index) {
		validate();
		return baseList.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return baseList.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return baseList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return baseList.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		validate();
		return baseList.set(index, element);
	}

	@Override
	public int size() {
		return baseList.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		validate();
		return baseList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		validate();
		return baseList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		validate();
		return baseList.toArray(a);
	}
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private void validate() {
		if (!sorted)
			baseList.sort(comparator);
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public boolean equals(Object o) {
		validate();
		return baseList.equals(o);
	}

	@Override
	public int hashCode() {
		return baseList.hashCode();
	}
	
	@Override
	public String toString() {
		return baseList.toString();
	}
	
}
