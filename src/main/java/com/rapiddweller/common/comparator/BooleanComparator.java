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

package com.rapiddweller.common.comparator;

import java.util.Comparator;

/**
 * Compares values of type Boolean or boolean.
 * Created: 05.04.2005 19:25:25
 * @author Volker Bergmann
 */
public class BooleanComparator implements Comparator<Boolean> {

  @Override
  public int compare(Boolean o1, Boolean o2) {
    return compare(o1.booleanValue(), o2.booleanValue());
  }

  public static int compare(boolean b1, boolean b2) {
    return (!b1 && b2 ? -1 : (b1 && !b2 ? 1 : 0));
  }

}
