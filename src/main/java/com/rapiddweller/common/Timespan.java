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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a timespan between two dates.
 * Created: 17.02.2005 20:59:22
 */
public class Timespan {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  /**
   * The Start date.
   */
  public Date startDate;
  /**
   * The End date.
   */
  public Date endDate;

  /**
   * Instantiates a new Timespan.
   *
   * @param startDate the start date
   * @param endDate   the end date
   */
  public Timespan(Date startDate, Date endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Overlaps boolean.
   *
   * @param that the that
   * @return the boolean
   */
  public boolean overlaps(Timespan that) {
    return (intersection(this, that) != null);
  }

  /**
   * Duration long.
   *
   * @return the long
   */
  public Long duration() {
    if (endDate != null && startDate != null) {
      return endDate.getTime() - startDate.getTime();
    } else {
      return null;
    }
  }

  /**
   * Contains boolean.
   *
   * @param that the that
   * @return the boolean
   */
  public boolean contains(Timespan that) {
    return (!this.startDate.after(that.startDate) && !this.endDate.before(that.endDate));
  }

  /**
   * Contains boolean.
   *
   * @param date the date
   * @return the boolean
   */
  public boolean contains(Date date) {
    return (!this.startDate.after(date) && !this.endDate.before(date));
  }

  /**
   * Intersection timespan.
   *
   * @param span1 the span 1
   * @param span2 the span 2
   * @return the timespan
   */
  public static Timespan intersection(Timespan span1, Timespan span2) {
    Date startDate = span1.startDate;
    if (span1.startDate == null || (span2.startDate != null && span1.startDate.before(span2.startDate))) {
      startDate = span2.startDate;
    }
    Date endDate = span1.endDate;
    if (span1.endDate == null || (span2.endDate != null && span1.endDate.after(span2.endDate))) {
      endDate = span2.endDate;
    }
    if (startDate != null && endDate != null && !endDate.after(startDate)) {
      return null;
    }
    return new Timespan(startDate, endDate);
  }

  /**
   * Unite timespan.
   *
   * @param span1 the span 1
   * @param span2 the span 2
   * @return the timespan
   */
  public static Timespan unite(Timespan span1, Timespan span2) {
    Date startDate = span1.startDate;
    if (startDate != null) {
      Date date2 = span2.startDate;
      if (date2 == null) {
        startDate = date2;
      } else if (date2.before(startDate)) {
        startDate = date2;
      }
    }
    Date endDate = span1.endDate;
    if (endDate != null) {
      Date date2 = span2.endDate;
      if (date2 == null) {
        endDate = date2;
      } else if (date2.after(endDate)) {
        endDate = date2;
      }
    }
    return new Timespan(startDate, endDate);
  }

  /**
   * Recent days timespan.
   *
   * @param count the count
   * @return the timespan
   */
  public static Timespan recentDays(int count) {
    Date end = TimeUtil.today();
    Date start = TimeUtil.addDays(end, -count);
    return new Timespan(start, end);
  }

  /**
   * Future days timespan.
   *
   * @param count the count
   * @return the timespan
   */
  public static Timespan futureDays(int count) {
    Date start = TimeUtil.today();
    Date end = TimeUtil.addDays(start, count);
    return new Timespan(start, end);
  }


  /**
   * Day iterator iterator.
   *
   * @return the iterator
   */
  public Iterator<Date> dayIterator() {
    List<Date> dates = new ArrayList<>();
    for (Date date = startDate; !date.after(endDate); date = TimeUtil.addDays(date, 1)) {
      dates.add(date);
    }
    return dates.iterator();
  }

  @Override
  public String toString() {
    if (startDate != null) {
      if (endDate != null) {
        return sdf.format(startDate) + " - " + sdf.format(endDate);
      } else {
        return "since " + sdf.format(startDate);
      }
    } else if (endDate != null) {
      return "until " + sdf.format(endDate);
    } else {
      return "ever";
    }
  }

  @Override
  public int hashCode() {
    int result = ((endDate == null) ? 0 : endDate.hashCode());
    return result * 31 + ((startDate == null) ? 0 : startDate.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Timespan that = (Timespan) obj;
    if (endDate == null) {
      if (that.endDate != null) {
        return false;
      }
    } else if (!endDate.equals(that.endDate)) {
      return false;
    }
    if (startDate == null) {
      return that.startDate == null;
    } else {
      return (startDate.equals(that.startDate));
    }
  }

}
