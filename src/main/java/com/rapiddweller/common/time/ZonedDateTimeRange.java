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

package com.rapiddweller.common.time;

import java.time.ZonedDateTime;

/**
 * Represents a time range between to {@link ZonedDateTime} instances.<br><br>
 * Created: 4 Jun 2020 12:17:25
 * @author Volker Bergmann
 * @since 1.0.12
 */
public class ZonedDateTimeRange {

  private ZonedDateTime min;
  private ZonedDateTime max;

  public ZonedDateTimeRange(ZonedDateTime from, ZonedDateTime until) {
    this.min = from;
    this.max = until;
  }

  public ZonedDateTime getMin() {
    return min;
  }

  public void setMin(ZonedDateTime min) {
    this.min = min;
  }

  public ZonedDateTime getMax() {
    return max;
  }

  public void setMax(ZonedDateTime max) {
    this.max = max;
  }

  public boolean isIntraday() {
    return min.toLocalDate().equals(max.toLocalDate());
  }

  public void include(ZonedDateTime dateTime) {
    if (this.min == null || dateTime.isBefore(this.min))
      this.min = dateTime;
    if (this.max == null || dateTime.isAfter(this.max))
      this.max = dateTime;
  }

}
