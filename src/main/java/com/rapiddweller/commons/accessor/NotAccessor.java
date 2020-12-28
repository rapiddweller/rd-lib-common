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
 * Inverts the boolean result of another Accessor.
 * Created: 07.03.2006 16:48:52
 * @param <E> the object type to access
 * @author Volker Bergmann
 */
public class NotAccessor<E> extends AccessorProxy<E, Boolean> {

    public NotAccessor(Accessor<E, Boolean> realAccessor) {
        super(realAccessor);
    }
    
    // Accessor interface ----------------------------------------------------------------------------------------------

    @Override
    public Boolean getValue(E target) {
        Boolean value = super.getValue(target);
        if (value == null)
            return null;
        else if (value)
            return Boolean.FALSE;
        else
            return Boolean.TRUE;
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final NotAccessor that = (NotAccessor) o;
        return this.realAccessor.equals(that.realAccessor);
    }

    @Override
    public int hashCode() {
        return realAccessor.hashCode();
    }
}
