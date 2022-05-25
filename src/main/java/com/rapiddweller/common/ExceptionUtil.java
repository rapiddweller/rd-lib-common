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

import com.rapiddweller.common.exception.ApplicationException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.rapiddweller.common.StringUtil.isEmpty;

/**
 * Provides Exception related utilities.
 * Created at 23.02.2009 13:13:19
 * @author Volker Bergmann
 * @since 0.4.8
 */
public class ExceptionUtil {

  private ExceptionUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static Throwable getRootCause(Throwable t) {
    Throwable result = t;
    while (result.getCause() != null && result.getCause() != result) // Note: UnsupportedOperationException recurses to itself as cause
    {
      result = result.getCause();
    }
    return result;
  }

  /** Scans recursively through an Exception and its 'cause' chain and tells if a given Exception type is contained. */
  public static boolean containsException(Class<? extends Throwable> exceptionType, Throwable exception) {
    do {
      if (exceptionType.isAssignableFrom(exception.getClass())) {
        return true;
      }
      exception = exception.getCause();
    } while (exception != null);
    return false;
  }

  public static String stackTraceToString(Throwable throwable) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    return sw.toString();
  }

  public static String formatMessageWithLocation(String message, Throwable cause, TextFileLocation location) {
    return formatMessageWithTextAndLocation(message, cause, null, location);
  }

  public static String formatMessageWithTextAndLocation(String message, Throwable cause, String scriptText, TextFileLocation location) {
    // format message
    String result = ExceptionUtil.endWithDotSpace(message != null ? message: "Unspecific error");
    if (!isEmpty(scriptText)) {
      result = ExceptionUtil.endWithDotSpace(result + "Script text: '" + scriptText + "'");
    }
    // format help
    String help = (cause instanceof ApplicationException ? ((ApplicationException) cause).getHelp() : null);
    if (help != null) {
      result = ExceptionUtil.endWithDotSpace(result + help);
    }
    // format location
    if (location != null) {
      boolean sidUsed = false;
      if (location.getSystemId() != null) {
        result += "File " + StringUtil.lastToken(location.getSystemId(), '/');
        sidUsed = true;
      }
      if (location.getStartLine() > 0) {
        if (sidUsed) {
          result += ", line " + location.getStartLine();
        } else {
          result += "Line " + location.getStartLine();
        }
      }
    }
    return result;
  }

  public static String endWithDotSpace(String message) {
    String result = message.trim();
    if (!result.endsWith(".")) {
      result += ".";
    }
    return result + " ";
  }

}
