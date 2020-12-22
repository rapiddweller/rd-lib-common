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
package com.rapiddweller.commons.mutator;

import com.rapiddweller.commons.Mutator;
import com.rapiddweller.commons.UpdateFailedException;

/**
 * Mutator proxy that converts the 'value' argument to a String before calling the real proxy.
 * Created: 18.12.2005 21:05:59
 * @deprecated 
 */
@Deprecated
public class StringMutator extends MutatorProxy {

    public StringMutator(Mutator realMutator) {
        super(realMutator);
    }

    @Override
    public void setValue(Object target, Object value) throws UpdateFailedException {
        String s;
        if (value == null)
            s = null;
        else if (value instanceof String)
            s = (String) value;
        else
            s = value.toString();
        realMutator.setValue(target, s);
    }
}
