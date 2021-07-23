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

package com.rapiddweller.common.math;

/**
 * Combines an int and a double to a pair.<br><br>
 * Created: 13.02.2020 09:15:27
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class IntDoublePair {

  /**
   * The .
   */
  public int i;
  /**
   * The D.
   */
  public double d;

  /**
   * Instantiates a new Int double pair.
   *
   * @param i the
   * @param d the d
   */
  public IntDoublePair(int i, double d) {
    this.i = i;
    this.d = d;
  }

}
