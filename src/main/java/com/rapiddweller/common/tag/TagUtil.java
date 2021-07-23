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

package com.rapiddweller.common.tag;

import com.rapiddweller.common.Tagged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides tag-related utility methods.
 * Created: 15.11.2013 06:58:15
 *
 * @author Volker Bergmann
 * @since 0.5.25
 */
public class TagUtil {

  /**
   * Add tag.
   *
   * @param tag     the tag
   * @param taggeds the taggeds
   */
  public static void addTag(String tag, List<? extends Tagged> taggeds) {
    for (Tagged tagged : taggeds) {
      tagged.addTag(tag);
    }
  }

  /**
   * Remove tag.
   *
   * @param tag     the tag
   * @param taggeds the taggeds
   */
  public static void removeTag(String tag, List<? extends Tagged> taggeds) {
    for (Tagged tagged : taggeds) {
      tagged.removeTag(tag);
    }
  }

  /**
   * Frequency int.
   *
   * @param tag     the tag
   * @param taggeds the taggeds
   * @return the int
   */
  public static int frequency(String tag, List<? extends Tagged> taggeds) {
    int n = 0;
    for (Tagged tagged : taggeds) {
      if (tagged != null && tagged.hasTag(tag)) {
        n++;
      }
    }
    return n;
  }

  /**
   * Gets elements with tag.
   *
   * @param <T>          the type parameter
   * @param tag          the tag
   * @param elements     the elements
   * @param ignoreCase   the ignore case
   * @param partialMatch the partial match
   * @return the elements with tag
   */
  public static <T extends Tagged> List<T> getElementsWithTag(String tag, Collection<T> elements, boolean ignoreCase, boolean partialMatch) {
    List<T> result = new ArrayList<>();
    for (T element : elements) {
      if (hasTag(tag, element, ignoreCase, partialMatch)) {
        result.add(element);
      }
    }
    return result;
  }

  /**
   * Has tag boolean.
   *
   * @param tag          the tag
   * @param tagged       the tagged
   * @param ignoreCase   the ignore case
   * @param partialMatch the partial match
   * @return the boolean
   */
  public static boolean hasTag(String tag, Tagged tagged, boolean ignoreCase, boolean partialMatch) {
    for (String candidate : tagged.getTags()) {
      if (ignoreCase) {
        candidate = candidate.toLowerCase();
        tag = tag.toLowerCase();
      }
      if (candidate.equals(tag) || (partialMatch && candidate.startsWith(tag))) {
        return true;
      }
    }
    return false;
  }
}
