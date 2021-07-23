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
 * double-based rectangle.
 * Created: 18.09.2010 08:37:58
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class DoubleRect {

  /**
   * The X.
   */
  public double x;
  /**
   * The Y.
   */
  public double y;
  /**
   * The Width.
   */
  public double width;
  /**
   * The Height.
   */
  public double height;

  /**
   * Instantiates a new Double rect.
   *
   * @param x      the x
   * @param y      the y
   * @param width  the width
   * @param height the height
   */
  public DoubleRect(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public String toString() {
    return "[" + x + "," + y + "," + width + "," + height + "]";
  }

}
