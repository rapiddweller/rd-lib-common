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

package com.rapiddweller.common.bean;

import com.rapiddweller.common.ArrayFormat;

import java.util.Arrays;

/**
 * Wraps a plain object or an array and provides equals() and hashCode()
 * that works consistently for both.
 * Created: 09.09.2010 09:53:11
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ObjectOrArray {

  private final Object realObject;

  public ObjectOrArray(Object realObject) {
    this.realObject = realObject;
  }

  @Override
  public int hashCode() {
    if (realObject.getClass().isArray()) {
      return Arrays.hashCode((Object[]) realObject);
    } else {
      return realObject.hashCode();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ObjectOrArray that = (ObjectOrArray) obj;
    if (this.realObject.getClass().isArray()) {
      return Arrays.equals((Object[]) this.realObject, (Object[]) that.realObject);
    } else {
      return this.realObject.equals(that.realObject);
    }
  }

  @Override
  public String toString() {
    if (realObject.getClass().isArray()) {
      return ArrayFormat.format((Object[]) realObject);
    } else {
      return String.valueOf(realObject);
    }
  }

}
