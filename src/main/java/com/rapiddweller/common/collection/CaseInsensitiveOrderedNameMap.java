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
import com.rapiddweller.common.OrderedMap;

import java.util.Map;

/**
 * {@link OrderedMap} implementation which assigns name strings to objects preserving the capitalization 
 * of stored names but allowing retrieval in a different capitalization.
 * Created: 12.12.2012 11:12:16
 * @param <E> the type of the collection's elements
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class CaseInsensitiveOrderedNameMap<E> extends OrderedMap<String, E> {
	
	private static final long serialVersionUID = 5774443959123444148L;

	// constructors + factory methods ----------------------------------------------------------------------------------
	
    public CaseInsensitiveOrderedNameMap() {
	}
    
    public CaseInsensitiveOrderedNameMap(Map<String, E> that) {
		super(that);
	}

    // Map interface implementation ------------------------------------------------------------------------------------
    
	@Override
	public boolean containsKey(Object key) {
        return containsKey((String) key);
    }

	public boolean containsKey(String key) {
        boolean result = super.containsKey(key);
        if (result)
        	return result;
        for (String tmp : super.keySet())
        	if (tmp.equalsIgnoreCase(key))
        		return true;
		return result;
    }

	@Override
	public E get(Object key) {
		return get((String) key);
	}
	
	public E get(String key) {
        E result = super.get(key);
        if (result != null)
        	return result;
        for (Map.Entry<String, E> entry : super.entrySet()) {
	        String tmp = entry.getKey();
	        if ((tmp == null && key == null) || (tmp != null && tmp.equalsIgnoreCase(key)))
        		return entry.getValue();
        }
		return null;
    }

	@Override
	public Map.Entry<String, E> getEntry(String key) {
        E value = super.get(key);
        if (value != null)
        	return new MapEntry<>(key, value);
        for (Map.Entry<String, E> entry : super.entrySet()) {
	        String tmp = entry.getKey();
	        if ((tmp == null && key == null) || (tmp != null && tmp.equalsIgnoreCase(key))) {
				String resultKey = entry.getKey();
				return new MapEntry<>(resultKey, entry.getValue());
			}
        }
		return null;
    }

    @Override
    public E put(String key, E value) {
        return super.put(key, value); 
    }

    public E remove(String key) {
        E result = super.remove(key);
        if (result != null)
        	return result;
        for (Map.Entry<String, E> entry : super.entrySet())
        	if (NullSafeComparator.equals(entry.getKey(), key))
        		return super.remove(entry.getKey());
        return null;
    }

}
