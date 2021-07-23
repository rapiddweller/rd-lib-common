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

package com.rapiddweller.common.version;

import com.rapiddweller.common.comparator.IntComparator;

/**
 * Number based {@link VersionNumberComponent}.
 * Created at 22.12.2008 16:33:56
 *
 * @author Volker Bergmann
 * @since 0.5.7
 */
public class NumberVersionNumberComponent extends VersionNumberComponent {

  private static final long serialVersionUID = -4669653693506092150L;

  private final String numberString;
  private final int number;

  /**
   * Instantiates a new Number version number component.
   *
   * @param numberString the number string
   */
  public NumberVersionNumberComponent(String numberString) {
    this.numberString = numberString;
    this.number = Integer.parseInt(numberString);
  }

  /**
   * Instantiates a new Number version number component.
   *
   * @param number the number
   */
  public NumberVersionNumberComponent(int number) {
    this.numberString = String.valueOf(number);
    this.number = number;
  }

  @Override
  public int compareTo(VersionNumberComponent that) {
    if (that == null) {
      return IntComparator.compare(number, 0);
    }
    if (!(that instanceof NumberVersionNumberComponent)) // numbers are more significant than markers like 'alpha'
    {
      return 1;
    }
    return IntComparator.compare(this.number, ((NumberVersionNumberComponent) that).number);
  }

  /**
   * Gets number.
   *
   * @return the number
   */
  public int getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return numberString;
  }

  @Override
  public int hashCode() {
    return number;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    NumberVersionNumberComponent that = (NumberVersionNumberComponent) obj;
    return this.number == that.number;
  }

}
