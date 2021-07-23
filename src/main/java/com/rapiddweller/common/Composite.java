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

package com.rapiddweller.common;

import com.rapiddweller.common.collection.OrderedNameMap;

/**
 * Models an Object that is composed of other objects.
 * Created: 26.08.2007 08:08:02
 *
 * @author Volker Bergmann
 */
public interface Composite {
  /**
   * Gets component.
   *
   * @param key the key
   * @return the component
   */
  Object getComponent(String key);

  /**
   * Sets component.
   *
   * @param key   the key
   * @param value the value
   */
  void setComponent(String key, Object value);

  /**
   * Gets components.
   *
   * @return the components
   */
  OrderedNameMap<Object> getComponents();
}
