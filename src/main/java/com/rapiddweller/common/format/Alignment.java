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

package com.rapiddweller.common.format;

/**
 * Provides constants for horizontal aligning.
 * Created: 29.06.2007 18:20:45
 * @author Volker Bergmann
 */
public enum Alignment {

  /** Left alignment. */
  LEFT('l'),

  /** Right alignment. */
  RIGHT('r'),

  /** Center alignment. */
  CENTER('c');

  private final char id;

  Alignment(char id) {
    this.id = id;
  }

  public char getId() {
    return id;
  }
}
