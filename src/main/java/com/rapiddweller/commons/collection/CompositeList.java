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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Combines several {@link List} components to be treated as a single list which contains all component lists' elements.
 * Created: 22.08.2012 17:53:31
 * @param <E> the type of the collection's elements
 * @since 0.5.18
 * @author Volker Bergmann
 */
public class CompositeList<E> implements List<E>, Serializable {
	
	private static final long serialVersionUID = -6652107866866558487L;
	
	List<List<E>> components;

	@SuppressWarnings("unchecked")
	public CompositeList(List<? extends E>... components) {
		this.components = new ArrayList<List<E>>();
		for (List<? extends E> component : components) {
			if (component == null)
				throw new IllegalArgumentException("Component is null");
			this.components.add((List<E>) component);
		}
	}
	
	@Override
	public boolean add(E element) {
		return getOrCreateLastComponent().add(element);
	}

	@Override
	public void add(int index, E element) {
		for (List<E> component : components) {
			if (index < component.size()) {
				component.add(index, element);
				return;
			}
			index -= component.size();
		}
		if (index == 0) {
			getOrCreateLastComponent().add(element);
			return;
		}
		throw new IndexOutOfBoundsException("Index " + index + " does not exist (I have " + size() + " elements)");
	}

	@Override
	public boolean addAll(Collection<? extends E> elements) {
		return getOrCreateLastComponent().addAll(elements);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> elements) {
		for (List<E> component : components) {
			if (index < component.size()) {
				return component.addAll(index, elements);
			}
			index -= component.size();
		}
		if (index == 0)
			return getOrCreateLastComponent().addAll(elements);
		throw new IndexOutOfBoundsException("Index " + index + " does not exist (I have " + size() + " elements)");
	}

	@Override
	public void clear() {
		this.components.clear();
	}

	@Override
	public boolean contains(Object element) {
		for (List<? extends E> component : components)
			if (component.contains(element))
				return true;
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> elements) {
		for (Object element : elements)
			if (!contains(element))
				return false;
		return true;
	}
	
	@Override
	public E get(int index) {
		for (List<? extends E> component : components) {
			if (index < component.size())
				return component.get(index);
			index -= component.size();
		}
		throw new IndexOutOfBoundsException("Index " + index + " does not exist (I have " + size() + " elements)");
	}

	@Override
	public E set(int index, E element) {
		for (List<E> component : components) {
			if (index < component.size())
				return component.set(index, element);
			index -= component.size();
		}
		throw new IndexOutOfBoundsException("Index " + index + " does not exist (I have " + size() + " elements)");
	}

	@Override
	public int indexOf(Object element) {
		int offset = 0;
		for (List<? extends E> component : components) {
			int index = component.indexOf(element);
			if (index >= 0)
				return offset + index;
			offset += component.size();
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		for (List<? extends E> component : components)
			if (!component.isEmpty())
				return false;
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new SubIterator();
	}

	@Override
	public int lastIndexOf(Object element) {
		for (int i = components.size() - 1; i >= 0; i--) {
			List<? extends E> component = components.get(i);
			int index = component.lastIndexOf(element);
			if (index >= 0) {
				int offset = 0;
				for (int j = 0; j < i; j++)
					offset += components.get(j).size();
				return offset + index;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new SubIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new SubIterator(index);
	}

	@Override
	public boolean remove(Object element) {
		for (List<? extends E> component : components)
			if (component.remove(element))
				return true;
		return false;
	}

	@Override
	public E remove(int index) {
		for (List<? extends E> component : components) {
			if (index < component.size())
				return component.remove(index);
			index -= component.size();
		}
		throw new IndexOutOfBoundsException("Tried to remove index " + index + " from a list with " + size() + " elements");
	}

	@Override
	public boolean removeAll(Collection<?> elements) {
		boolean result = false;
		for (List<? extends E> component : components)
			if (component.removeAll(elements))
				result = true;
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> elements) {
		boolean result = false;
		for (List<? extends E> component : components)
			if (component.retainAll(elements))
				result = true;
		return result;
	}

	@Override
	public int size() {
		int result = 0;
		for (List<? extends E> component : components)
			result += component.size();
		return result;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> list = new ArrayList<E>(toIndex - fromIndex + 1);
		for (int i = fromIndex; i < toIndex; i++)
			list.add(get(i));
		return list;
	}

	@Override
	public Object[] toArray() {
		return toArray(new Object[size()]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] array) {
		int offset = 0;
		for (List<E> component : components) {
			for (int i = 0; i < components.size(); i++)
				array[offset + i] = (T) component.get(i);
			offset += component.size();
		}
		return array;
	}
	
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private List<E> getOrCreateLastComponent() {
		if (components.size() == 0)
			this.components.add(new ArrayList<E>());
		return components.get(components.size() - 1);
	}
	
	
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return components.toString();
	}
	
	
	
	public class SubIterator implements ListIterator<E> {
		
		private int superIndex;
		private int subIndex;
		private int totalIndex;
		
		public SubIterator() {
			this(0);
		}
		
		public SubIterator(int offset) {
			this.superIndex = 0;
			this.subIndex = 0;
			this.totalIndex = 0;
			for (int i = 0; i < offset; i++)
				next();
		}
		
		@Override
		public boolean hasNext() {
			return (totalIndex < size());
		}

		@Override
		public E next() {
			List<E> currentComponent = components.get(superIndex);
			while (subIndex >= currentComponent.size() && superIndex < components.size() - 1) {
				++superIndex;
				currentComponent = components.get(superIndex);
				subIndex = 0;
			}
			totalIndex++;
			return currentComponent.get(subIndex++);
		}

		@Override
		public int nextIndex() {
			return totalIndex;
		}

		@Override
		public boolean hasPrevious() {
			return (subIndex > 0 || superIndex > 0);
		}

		@Override
		public E previous() {
			List<E> currentComponent = components.get(superIndex);
			while (subIndex <= 0 && superIndex > 0) {
				--superIndex;
				currentComponent = components.get(superIndex);
				subIndex = currentComponent.size();
			}
			totalIndex--;
			return currentComponent.get(--subIndex);
		}

		@Override
		public int previousIndex() {
			return totalIndex - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public void add(E newValue) {
			throw new UnsupportedOperationException("Not supported");
		}

		@Override
		public void set(E newValue) {
			throw new UnsupportedOperationException("Not supported");
		}

	}

}
