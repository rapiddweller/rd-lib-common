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

package com.rapiddweller.common.iterator;

import com.rapiddweller.common.exception.ExceptionFactory;

/**
 * Abstract implementation of the {@link TabularIterator} interface.
 * Created: 27.10.2011 08:46:11
 * @author Volker Bergmann
 * @since 0.5.11
 */
public abstract class AbstractTabularIterator implements TabularIterator {

  private final String[] columnLabels;

  protected AbstractTabularIterator(String... columnLabels) {
    this.columnLabels = columnLabels;
  }

  @Override
  public String[] getColumnNames() {
    return columnLabels;
  }

  @Override
  public void remove() {
    throw ExceptionFactory.getInstance().illegalOperation(getClass() + " does not support removal");
  }

  @Override
  @SuppressWarnings("unused")
  public void close() {
    // empty implementation
  }

}
