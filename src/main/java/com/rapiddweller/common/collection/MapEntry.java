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

import java.util.Map;

import com.rapiddweller.common.NullSafeComparator;

/**
 * Simple implementation of the Map.Entry interface.
 * @param <K> the key type
 * @param <V> the value type
 * @since 0.3.0
 * @author Volker Bergmann
 */
public class MapEntry<K, V> implements Map.Entry<K, V> {
    
    private final K key;
    private V value;
    
    public MapEntry(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }
    
    // interface -------------------------------------------------------------------------------------------------------

    @Override
	public K getKey() {
        return key;
    }

    @Override
	public V getValue() {
        return value;
    }

    @Override
	public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public String toString() {
        return String.valueOf(key) + '=' + value;
    }

	@Override
	public int hashCode() {
		return ((key == null) ? 0 : key.hashCode()) * 31 + ((value == null) ? 0 : value.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		MapEntry that = (MapEntry) obj;
		return NullSafeComparator.equals(this.key, that.key) && NullSafeComparator.equals(this.value, that.value);
	}
    
}
