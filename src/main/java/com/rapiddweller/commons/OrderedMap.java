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

import java.io.Serializable;
import java.util.*;

import com.rapiddweller.commons.collection.ListBasedSet;
import com.rapiddweller.commons.collection.MapEntry;
import com.rapiddweller.commons.collection.MapProxy;

/**
 * Map implementation that tracks the order in which elements where added
 * and returns them in that order by the <i>values()</i> method. 
 * This is useful for all cases in which elements will be queried by a key
 * and processed but need to be stored in the original order.
 * Created: 06.01.2007 09:04:17
 * @param <K> the key type
 * @param <V> the value type
 * @author Volker Bergmann
 */
public class OrderedMap<K,V> implements Map<K,V>, Serializable {

    private static final long serialVersionUID = -6081918861041975388L;
    
	private final Map<K, Integer> keyIndices;
    protected List<V> values;

    // constructors ----------------------------------------------------------------------------------------------------

    public OrderedMap() {
        keyIndices = new HashMap<>();
        values = new ArrayList<>();
    }

    public OrderedMap(int initialCapacity) {
        keyIndices = new HashMap<>(initialCapacity);
        values = new ArrayList<>(initialCapacity);
    }

    public OrderedMap(int initialCapacity, float loadFactor) {
        keyIndices = new HashMap<>(initialCapacity, loadFactor);
        values = new ArrayList<>(initialCapacity);
    }

    public OrderedMap(Map<K,V> source) {
        this(source.size());
        for (Entry<K, V> entry : source.entrySet())
            put(entry.getKey(), entry.getValue());
    }
    
    
    
    // custom interface ------------------------------------------------------------------------------------------------
    
	public Map.Entry<K, V> getEntry(K key) {
		if (containsKey(key))
			return new MapEntry<>(key, get(key));
		else
			return null;
    }
	
	
	
    // Map interface implementation ------------------------------------------------------------------------------------

    @Override
	public int size() {
        return values.size();
    }

    @Override
	public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
	public boolean containsKey(Object key) {
        return keyIndices.containsKey(key);
    }

    @Override
	public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
	public V get(Object key) {
        Integer index = keyIndices.get(key);
        if (index == null)
            return null;
        return values.get(index);
    }

    @Override
	public V put(K key, V value) {
        Integer index = keyIndices.get(key);
        if (index != null)
            return values.set(index, value);
        else {
            keyIndices.put(key, values.size());
            values.add(value);
            return null;
        }
    }

    @Override
	public V remove(Object key) {
        Integer index = keyIndices.remove(key);
        if (index != null) {
            V oldValue = values.get(index);
            values.remove((int)index);
            for (Entry<K, Integer> entry : keyIndices.entrySet()) {
                int entryIndex = entry.getValue();
                if (entryIndex > index)
                    entry.setValue(entryIndex - 1);
            }
            return oldValue;
        } else
            return null;
    }

    @Override
	public void putAll(Map<? extends K, ? extends V> t) {
        for (Map.Entry<? extends K, ? extends V> entry : t.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
	public void clear() {
        keyIndices.clear();
        values.clear();
    }

    @Override
	public Set<K> keySet() {
        List<K> tmp = new ArrayList<>(values.size());
        // set the used array size by adding nulls
        for (int i = 0; i < values.size(); i++)
            tmp.add(null);
        // set the array elements themselves
        for (Entry<K, Integer> entry : keyIndices.entrySet())
            tmp.set(entry.getValue(), entry.getKey());
        return new ListBasedSet<>(tmp);
    }

    @Override
	public List<V> values() {
        return new ArrayList<>(values);
    }

    @Override
	@SuppressWarnings("unchecked")
    public Set<Map.Entry<K, V>> entrySet() {
    	Map.Entry<K, V>[] tmp = new Map.Entry[values.size()];
        for (Map.Entry<K, Integer> entry : keyIndices.entrySet()) {
            Integer index = entry.getValue();
            tmp[index] = new ProxyEntry(entry.getKey(), index);
        }
        return new ListBasedSet<>(tmp);
    }

    // List/Vector interface -------------------------------------------------------------------------------------------

    public V valueAt(int index) {
        return values.get(index);
    }
    
    public int indexOfValue(V value) {
    	return values.indexOf(value);
    }

    /**
     * Returns an array containing all of the values in this map in proper sequence.
     * Obeys the general contract of the Collection.toArray method.
     * @return an array containing all of the elements in this list in proper sequence.
     */
    public Object[] toArray() {
        return values.toArray();
    }

    /**
     * Returns an array containing all of the values in this map in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     * Obeys the general contract of the Collection.toArray(Object[]) method.
     * @param a the array into which the elements of this list are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * @param <T> the component type of the result array
     * @return an array containing the values of this map.
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this list.
     * @throws NullPointerException - if the specified array is null.
     */
    public <T>T[] toArray(T[] a) {
        return values.toArray(a);
    }
    
    // specific interface ----------------------------------------------------------------------------------------------
    
    public List<V> internalValues() {
        return values;
    }

    public boolean equalsIgnoreOrder(Map<K, V> that) {
        if (this == that)
            return true;
        if (this.size() != that.size())
        	return false;
        for (Map.Entry<K, V> entry : that.entrySet()) {
            K key = entry.getKey();
            if (!this.containsKey(key))
                return false;
            if (!NullSafeComparator.equals(this.get(key), that.get(key)))
                return false;
        }
        return true;
    }
    
    private class ProxyEntry implements Map.Entry<K, V> {
    	
    	private final K key;
    	private final int index;
    	
		public ProxyEntry(K key, int index) {
			this.key = key;
			this.index = index;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return OrderedMap.this.values.get(index);
		}

		@Override
		public V setValue(V value) {
			return OrderedMap.this.values.set(index, value);
		}
    	
		@Override
		public String toString() {
			return String.valueOf(key) + '=' + getValue();
		}
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Map))
            return false;
        while (o instanceof MapProxy)
        	o = ((MapProxy) o).getRealMap();
        final OrderedMap that = (OrderedMap) o;
        return (this.values.equals(that.values) && this.keyIndices.equals(that.keyIndices));
    }

    @Override
    public int hashCode() {
        return keyIndices.hashCode() * 29 + values.hashCode();
    }

    @Override
    public String toString() {
        ListBasedSet<Entry<K, V>> entries = (ListBasedSet<Entry<K, V>>)entrySet();
        StringBuilder buffer = new StringBuilder("{");
        if (entries.size() > 0)
            buffer.append(entries.get(0));
        for (int i = 1; i < entries.size(); i++)
            buffer.append(", ").append(entries.get(i));
        buffer.append('}');
        return buffer.toString();
    }
    
}