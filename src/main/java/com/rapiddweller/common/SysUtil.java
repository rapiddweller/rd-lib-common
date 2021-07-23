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

import java.util.concurrent.Callable;

/**
 * Provides system related utility methods.
 * Created: 21.10.2009 19:26:24
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class SysUtil {

  private SysUtil() {
  }

  /**
   * Run with system property.
   *
   * @param name   the name
   * @param value  the value
   * @param runner the runner
   */
  public static void runWithSystemProperty(String name, String value, Runnable runner) {
    String oldValue = System.getProperty(name);
    try {
      System.setProperty(name, value);
      runner.run();
    } finally {
      System.setProperty(name, (oldValue != null ? oldValue : ""));
    }
  }

  /**
   * Call with system property t.
   *
   * @param <T>    the type parameter
   * @param name   the name
   * @param value  the value
   * @param callee the callee
   * @return the t
   * @throws Exception the exception
   */
  public static <T> T callWithSystemProperty(String name, String value, Callable<T> callee) throws Exception {
    String oldValue = System.getProperty(name);
    try {
      System.setProperty(name, value);
      return callee.call();
    } finally {
      System.setProperty(name, oldValue);
    }
  }

}
