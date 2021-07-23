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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Provides Exception related utilities.
 * Created at 23.02.2009 13:13:19
 *
 * @author Volker Bergmann
 * @since 0.4.8
 */
public class ExceptionUtil {

  /**
   * Gets root cause.
   *
   * @param t the t
   * @return the root cause
   */
  public static Throwable getRootCause(Throwable t) {
    Throwable result = t;
    while (result.getCause() != null && result.getCause() != result) // Note: UnsupportedOperationException recurses to itself as cause
    {
      result = result.getCause();
    }
    return result;
  }

  /**
   * Scans recursively through an Exception and its 'cause' chain and tells if a given Exception type is contained.
   *
   * @param exceptionType the requested exception type
   * @param exception     the actual exception to examine
   * @return true if the given Exception type was found
   */
  public static boolean containsException(Class<? extends Throwable> exceptionType, Throwable exception) {
    do {
      if (exceptionType.isAssignableFrom(exception.getClass())) {
        return true;
      }
      exception = exception.getCause();
    } while (exception != null);
    return false;
  }

  /**
   * Stack trace to string string.
   *
   * @param throwable the throwable
   * @return the string
   */
  public static String stackTraceToString(Throwable throwable) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    return sw.toString();
  }
}
