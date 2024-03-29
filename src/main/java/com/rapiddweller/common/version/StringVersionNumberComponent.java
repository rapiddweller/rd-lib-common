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

import java.util.HashMap;
import java.util.Map;

/**
 * {@link VersionNumberComponent} implementation for String-type number components.
 * Created at 07.01.2009 19:07:55
 *
 * @author Volker Bergmann
 * @since 0.5.7
 */
public class StringVersionNumberComponent extends VersionNumberComponent {

  private static final long serialVersionUID = -3865105131640319765L;

  /**
   * The Key order.
   */
  static final String[] KEY_ORDER = {
      "snapshot",
      "alpha",
      "beta",
      "rc", "cr",
      "final", "ga",
      "sp"
  };

  /**
   * The constant SNAPSHOT.
   */
  public static final StringVersionNumberComponent SNAPSHOT = new StringVersionNumberComponent("snapshot");

  /**
   * The Ordinals.
   */
  static final Map<String, Integer> ordinals;

  static {
    ordinals = new HashMap<>();
    for (int i = 0; i < KEY_ORDER.length; i++) {
      ordinals.put(KEY_ORDER[i], i);
    }
  }

  /**
   * The Final index.
   */
  static final int FINAL_INDEX = ordinals.get("final");
  /**
   * The Sp index.
   */
  static final int SP_INDEX = ordinals.get("sp");

  private final String key;

  /**
   * Instantiates a new String version number component.
   *
   * @param key the key
   */
  public StringVersionNumberComponent(String key) {
    super();
    this.key = key;
  }

  @Override
  public int compareTo(VersionNumberComponent that) {
    if (that == null) {
      return -1;
    }
    Integer thisIndexObject = ordinals.get(this.key.toLowerCase());
    int thisIndex = (thisIndexObject != null ? thisIndexObject : FINAL_INDEX);
    if (that instanceof NumberVersionNumberComponent) {
      boolean number = ((NumberVersionNumberComponent) that).getNumber() == 0;
      if (number && thisIndex >= SP_INDEX) {
        return 1;
      } else if (number && thisIndex >= FINAL_INDEX) {
        return 0;
      } else {
        return -1;
      }
    }
    Integer thatIndexObject = ordinals.get((((StringVersionNumberComponent) that).key).toLowerCase());
    int thatIndex = (thatIndexObject != null ? thatIndexObject : FINAL_INDEX);
    return IntComparator.compare(thisIndex, thatIndex);
  }

  @Override
  public String toString() {
    return key;
  }

  @Override
  public int hashCode() {
    return key.toLowerCase().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    StringVersionNumberComponent that = (StringVersionNumberComponent) obj;
    return this.compareTo(that) == 0;
  }

}
