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

package com.rapiddweller.common.mutator;

import com.rapiddweller.common.Mutator;
import com.rapiddweller.common.UpdateFailedException;

/**
 * Proxy for a Mutator.
 * Created: 12.05.2005 18:54:36
 */
public abstract class MutatorProxy extends MutatorWrapper {

  /**
   * Instantiates a new Mutator proxy.
   *
   * @param realMutator the real mutator
   */
  public MutatorProxy(Mutator realMutator) {
    super(realMutator);
  }

  /**
   * @see com.rapiddweller.common.Mutator#setValue(java.lang.Object, java.lang.Object)
   */
  @Override
  public void setValue(Object target, Object value) throws UpdateFailedException {
    realMutator.setValue(target, value);
  }

}
