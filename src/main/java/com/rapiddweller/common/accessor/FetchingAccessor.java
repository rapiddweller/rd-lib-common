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

/**
 * Two step Accessor, that uses one Accessor to fetch an object and another one to read a value from it.
 * Created: 12.06.2007 18:43:28
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FetchingAccessor implements Accessor {

    private final Accessor provider;
    private final Accessor accessor;

    public FetchingAccessor(Accessor provider, Accessor accessor) {
        this.provider = provider;
        this.accessor = accessor;
    }

    @Override
	public Object getValue(Object o) {
        return accessor.getValue(provider.getValue(o));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[accessor=" + accessor + ", provider=" + provider + "]";
    }
    
}
