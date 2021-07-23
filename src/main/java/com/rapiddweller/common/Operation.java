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
 * Abstraction for operations on an arbitrary number of objects of type I which yield a result of type O.
 * Created: 03.08.2007 06:58:26
 *
 * @param <I> the argument type
 * @param <O> the result type
 * @author Volker Bergmann
 */
public interface Operation<I, O> {
  /**
   * Perform o.
   *
   * @param args the args
   * @return the o
   */
  O perform(I... args);
}
