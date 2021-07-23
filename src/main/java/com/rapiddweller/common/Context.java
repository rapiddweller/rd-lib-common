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

import java.util.Map;
import java.util.Set;

/**
 * Abstraction of a context that provides named items which can be set and retrieved.
 * Created: 23.08.2007 08:32:53
 *
 * @author Volker Bergmann
 * @since 0.3
 */
public interface Context {
  /**
   * Get object.
   *
   * @param key the key
   * @return the object
   */
  Object get(String key);

  /**
   * Set.
   *
   * @param key   the key
   * @param value the value
   */
  void set(String key, Object value);

  /**
   * Remove.
   *
   * @param key the key
   */
  void remove(String key);

  /**
   * Key set set.
   *
   * @return the set
   */
  Set<String> keySet();

  /**
   * Entry set set.
   *
   * @return the set
   */
  Set<Map.Entry<String, Object>> entrySet();

  /**
   * Contains boolean.
   *
   * @param key the key
   * @return the boolean
   */
  boolean contains(String key);
}
