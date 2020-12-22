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

/**
 * Wraps a Mutator.
 * Created: 25.01.2008 13:33:37
 * @author Volker Bergmann
 * @since 0.3.0
 */
public abstract class MutatorWrapper implements Mutator {

    protected Mutator realMutator;

    public MutatorWrapper(Mutator realMutator) {
        this.realMutator = realMutator;
    }

}
