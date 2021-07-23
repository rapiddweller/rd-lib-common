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
 * Enumeration which provides constants for Binary scales.
 * Created: 06.03.2011 15:29:47
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public enum BinaryScale {

  /**
   * Tera binary scale.
   */
  TERA(1099511627776L, "T"),
  /**
   * Giga binary scale.
   */
  GIGA(1073741824L, "G"),
  /**
   * Mega binary scale.
   */
  MEGA(1048576L, "M"),
  /**
   * Kilo binary scale.
   */
  KILO(1024L, "K"),
  /**
   * None binary scale.
   */
  NONE(1L, "");

  private final double factor;
  private final String designator;

  BinaryScale(double factor, String designator) {
    this.factor = factor;
    this.designator = designator;
  }

  /**
   * Gets factor.
   *
   * @return the factor
   */
  public double getFactor() {
    return factor;
  }

  /**
   * Gets designator.
   *
   * @return the designator
   */
  public String getDesignator() {
    return designator;
  }

}
