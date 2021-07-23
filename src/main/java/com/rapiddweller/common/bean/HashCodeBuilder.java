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

import java.util.Arrays;

/**
 * Helper class for calculating hash codes.
 * Created: 24.11.2010 12:39:14
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class HashCodeBuilder {

  /**
   * Hash code int.
   *
   * @param components the components
   * @return the int
   */
  public static int hashCode(Object... components) {
    int result = 0;
    for (Object component : components) {
      result = 31 * result + componentHashCode(component);
    }
    return result;
  }

  /**
   * Component hash code int.
   *
   * @param component the component
   * @return the int
   */
  public static int componentHashCode(Object component) {
    if (component == null) {
      return 0;
    }
    if (component.getClass().isArray()) {
      return Arrays.hashCode((Object[]) component);
    } else {
      return component.hashCode();
    }
  }

}
