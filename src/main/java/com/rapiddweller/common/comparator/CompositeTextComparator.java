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

import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.ParseUtil;

import java.math.BigInteger;
import java.util.Comparator;

/**
 * Splits texts into tokens of words and numbers and compares them element-wise.
 * Created: 22.05.2007 07:04:10
 * @author Volker Bergmann
 * @since 0.1
 */
public class CompositeTextComparator implements Comparator<String> {

  private final ArrayComparator<Object> arrayComparator;

  @SuppressWarnings({"rawtypes"})
  public CompositeTextComparator() {
    this.arrayComparator = new ArrayComparator<>(new ComparatorChain<>(
        new ObjectTypeComparator(BigInteger.class, String.class),
        new ComparableComparator()
    ));
  }

  @Override
  public int compare(String s1, String s2) {
    Object[] s1Parts = ParseUtil.splitNumbers(s1);
    Object[] s2Parts = ParseUtil.splitNumbers(s2);
    return arrayComparator.compare(s1Parts, s2Parts);
  }

}
