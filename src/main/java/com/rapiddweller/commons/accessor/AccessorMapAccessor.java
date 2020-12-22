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
package com.rapiddweller.commons.accessor;

import com.rapiddweller.commons.Accessor;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Uses an accessor which is stored in a Map for accessing the target object.
 * Created: 11.03.2006 12:45:26
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AccessorMapAccessor implements DependentAccessor {

    private static final List<Accessor<?, ?>> EMPTY_LIST = new ArrayList<Accessor<?, ?>>();

    private Map<Object, Accessor<?, ?>> map;
    private Object key;

    public AccessorMapAccessor(Map<Object, Accessor<?, ?>> map, Object key) {
        this.map = map;
        this.key = key;
    }

    // interface -------------------------------------------------------------------------------------------------------

    public Object getKey() {
        return key;
    }

    @Override
	public Object getValue(Object target) {
        Accessor accessor = getAccessor();
        if (accessor == null)
            throw new IllegalStateException("Key not found: " + key);
        return accessor.getValue(target);
    }

    @Override
	public List<Accessor<?, ?>> getDependencies() {
        Accessor<?, ?> accessor = getAccessor();
        if (accessor instanceof DependentAccessor)
            return ((DependentAccessor) accessor).getDependencies();
        else
            return EMPTY_LIST;
    }

    public Accessor<?, ?> getAccessor() {
        return map.get(key);
    }
}
