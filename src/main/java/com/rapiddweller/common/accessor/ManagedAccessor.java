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
package com.rapiddweller.common.accessor;

import com.rapiddweller.common.Accessor;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DependentAccessor implementation that manages the dependencies in a List.
 * Created: 11.03.2006 14:32:52
 * @param <C> the object type to access
 * @param <V> the type of the value to get from the object
 * @author Volker Bergmann
 */
public abstract class ManagedAccessor<C, V> implements DependentAccessor<C, V> {

    protected List<? extends Accessor<?, ?>> dependencies;
    
    // constructors ----------------------------------------------------------------------------------------------------

    protected ManagedAccessor() {
        this(new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    protected ManagedAccessor(Accessor<?, ?> dependency) {
        this(Collections.singletonList(dependency));
    }

    protected ManagedAccessor(List<? extends Accessor<?, ?>> dependencies) {
        this.dependencies = dependencies;
    }
    
    // DependentAccessor interface -------------------------------------------------------------------------------------

    @Override
	public List<? extends Accessor<?, ?>> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<? extends Accessor<?, ?>> dependencies) {
        this.dependencies = dependencies;
    }
    
}
