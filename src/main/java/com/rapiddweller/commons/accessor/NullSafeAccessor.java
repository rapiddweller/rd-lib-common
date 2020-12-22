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
 * Accessor wrapper that returns a predefined value if getValue() is invoked with a 'null' source.
 * Created: 26.06.2005 08:22:21
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 * @author Volker Bergmann
 */
public abstract class NullSafeAccessor<C, V> extends AccessorProxy<C, V> {

    private V nullValue;

    public NullSafeAccessor(Accessor<C, V> realAccessor, V nullValue) {
        super(realAccessor);
        this.nullValue = nullValue;
    }

    @Override
    public V getValue(C source) {
        return (source != null ? super.getValue(source) : nullValue);
    }
}
