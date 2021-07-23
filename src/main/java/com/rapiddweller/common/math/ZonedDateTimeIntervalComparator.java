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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * Compares Intervals of {@link ZonedDateTime}s.<br><br>
 * Created: 26.04.2019 23:32:29
 *
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class ZonedDateTimeIntervalComparator implements Comparator<Interval<ZonedDateTime>> {

  @Override
  public int compare(Interval<ZonedDateTime> i1, Interval<ZonedDateTime> i2) {
    ZonedDateTime zdt1 = i1.getMin().withZoneSameInstant(ZoneId.systemDefault());
    ZonedDateTime zdt2 = i2.getMin().withZoneSameInstant(ZoneId.systemDefault());
    return zdt1.compareTo(zdt2);
  }

}
