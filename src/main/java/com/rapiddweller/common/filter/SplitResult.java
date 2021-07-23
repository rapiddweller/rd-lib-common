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

package com.rapiddweller.common.filter;

import java.util.List;

/**
 * The items of a splitted list:
 * <code>matches</code> contains the List items that matched the filter,
 * <code>mismatches</code> contains the other ones.
 * Created: 10.04.2007 08:09:06
 *
 * @param <E> The type of elements processed
 * @author Volker Bergmann
 */
public class SplitResult<E> {

  private final List<E> matches;
  private final List<E> mismatches;

  /**
   * Instantiates a new Split result.
   *
   * @param matches    the matches
   * @param mismatches the mismatches
   */
  public SplitResult(List<E> matches, List<E> mismatches) {
    this.matches = matches;
    this.mismatches = mismatches;
  }

  /**
   * Gets matches.
   *
   * @return the matches
   */
  public List<E> getMatches() {
    return matches;
  }

  /**
   * Gets mismatches.
   *
   * @return the mismatches
   */
  public List<E> getMismatches() {
    return mismatches;
  }
}
