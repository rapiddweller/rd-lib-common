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

/**
 * Accessor that consecutively queries a chain of accessors 
 * until one provides the requested value.
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 * @since 0.3.0
 * @author Volker Bergmann
 */
public class FallbackAccessor<C, V> implements Accessor<C, V> {

    private Accessor<C, V>[] realAccessors;
    
    public FallbackAccessor(Accessor<C, V> ... realAccessors) {
        this.realAccessors = realAccessors;
    }

    @Override
	public V getValue(C target) {
        for (Accessor<C, V> realAccessor : realAccessors) {
            V value = realAccessor.getValue(target);
            if (value != null)
                return value;
        }
        return null;
    }
}
