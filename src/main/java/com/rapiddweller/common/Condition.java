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

/**
 * Evaluates an argument of type E if it matches some internal condition.
 * Created: 16.06.2007 12:31:22
 *
 * @param <E> the type of argument to evaluate
 * @author Volker Bergmann
 */
public interface Condition<E> {
  /**
   * Evaluate boolean.
   *
   * @param argument the argument
   * @return the boolean
   */
  boolean evaluate(E argument);
}
