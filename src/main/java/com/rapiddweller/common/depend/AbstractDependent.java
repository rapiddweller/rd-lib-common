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

package com.rapiddweller.common.depend;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides partial featur implementation of the Dependent interface.
 * @param <E> the type of the objects to process
 * @author Volker Bergmann
 * @since 0.3.04
 */
public abstract class AbstractDependent<E extends Dependent<E>> implements Dependent<E> {

  protected List<ProviderInfo<E>> providers;

  @SafeVarargs
  protected AbstractDependent(E... requiredProviders) {
    this.providers = new ArrayList<>();
    for (E requiredProvider : requiredProviders) {
      addRequiredProvider(requiredProvider);
    }
  }

  public void addRequiredProvider(E provider) {
    providers.add(new ProviderInfo<>(provider, true));
  }

  public void addOptionalProvider(E provider) {
    providers.add(new ProviderInfo<>(provider, false));
  }

  // Dependent interface --------------------------------------------------------------------------

  @Override
  public int countProviders() {
    return providers.size();
  }

  @Override
  public E getProvider(int index) {
    return providers.get(index).getProvider();
  }

  @Override
  public boolean requiresProvider(int index) {
    return providers.get(index).isRequired();
  }

}