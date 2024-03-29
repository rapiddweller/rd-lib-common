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

import java.io.File;

/**
 * Provides utility methods related to overall Databene functionality.
 * Created: 27.10.2012 08:53:22
 *
 * @author Volker Bergmann
 * @since 0.5.20
 */
public class DatabeneUtil {

  /**
   * Gets databene user home.
   *
   * @return the databene user home
   */
  public static File getDatabeneUserHome() {
    return new File(SystemInfo.getUserHome(), "rapiddweller");
  }

}
