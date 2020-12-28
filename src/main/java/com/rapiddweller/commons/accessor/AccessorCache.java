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

import java.util.List;
import java.util.ArrayList;

/**
 * Accessor implementation that caches the value it retrieves first 
 * until it is manually invalidated.
 * Created: 11.03.2006 17:02:27
 * @author Volker Bergmann
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 */
public class AccessorCache<C, V> implements DependentAccessor<C, V> {

    private final String name;
    private final Accessor<C, V> realAccessor;
    private V cachedValue;
    private boolean valid;

    public AccessorCache(String name, Accessor<C, V> realAccessor) {
        this.realAccessor = realAccessor;
        this.name = name;
        this.valid = false;
    }
    
    // properties ------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }
    
    public boolean isValid() {
        return valid;
    }

    public void invalidate() {
        valid = false;
    }

    // DependentAccessor interface -------------------------------------------------------------------------------------

    @Override
	public V getValue(C item) {
        if (!valid) {
            cachedValue = realAccessor.getValue(item);
            valid = true;
        }
        return cachedValue;
    }

    @Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public List<? extends Accessor<?,?>> getDependencies() {
        if (realAccessor instanceof DependentAccessor)
            return ((DependentAccessor) realAccessor).getDependencies();
        else
            return new ArrayList();
    }
    
}
