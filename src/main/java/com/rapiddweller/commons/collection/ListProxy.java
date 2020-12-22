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
package com.rapiddweller.commons.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Abstract proxy class for a {@link List}.
 * Created: 25.01.2012 17:03:57
 * @param <E> the type of the collection's elements
 * @since 0.5.14
 * @author Volker Bergmann
 */
public abstract class ListProxy<E> implements List<E> {

	protected List<E> realList;
	
	public ListProxy(List<E> realList) {
		this.realList = realList;
	}

	@Override
	public boolean add(E e) {
		return realList.add(e);
	}

	@Override
	public void add(int index, E element) {
		realList.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return realList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return realList.addAll(index, c);
	}

	@Override
	public void clear() {
		realList.clear();
	}

	@Override
	public boolean contains(Object o) {
		return realList.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return realList.containsAll(c);
	}
	
	@Override
	public E get(int index) {
		return realList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return realList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return realList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return realList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return realList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return realList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return realList.listIterator(index);
	}

	@Override
	public E remove(int index) {
		return realList.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return realList.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return realList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return realList.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		return realList.set(index, element);
	}

	@Override
	public int size() {
		return realList.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return realList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return realList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return realList.toArray(a);
	}
	
	@Override
	public boolean equals(Object o) {
		return realList.equals(o);
	}

	@Override
	public int hashCode() {
		return realList.hashCode();
	}
	
	@Override
	public String toString() {
		return realList.toString();
	}
	
}
