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

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides utility methods for threading.
 * Created: 26.03.2010 19:26:07
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class ThreadUtil {

  private ThreadUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static boolean allThreadSafe(Collection<?> elements) {
    for (Object element : elements) {
      if (!isThreadSafe(element)) {
        return false;
      }
    }
    return true;
  }

  public static boolean allThreadSafe(Object... objects) {
    for (Object element : objects) {
      if (!isThreadSafe(element)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isThreadSafe(Object object) {
    if (object instanceof ThreadAware)
      return ((ThreadAware) object).isThreadSafe();
    return false;
  }

  public static boolean allParallelizable(Collection<?> objects) {
    for (Object object : objects) {
      if (!isParallelizable(object)) {
        return false;
      }
    }
    return true;
  }

  public static boolean allParallelizable(Object... objects) {
    for (Object object : objects) {
      if (!isParallelizable(object)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isParallelizable(Object object) {
    if (object instanceof ThreadAware)
      return ((ThreadAware) object).isParallelizable();
    return false;
  }

  public static void sleepWithException(int millis) throws InterruptedException {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      if (Thread.interrupted()) {
        throw e;
      }
    }
  }

  public static void sleepIgnoringException(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      // ignore exception
    }
  }

  public static void sleepRandom(int minMillis, int maxMillis) {
    sleepIgnoringException(ThreadLocalRandom.current().nextInt(minMillis, maxMillis));
  }

  public static String currentStackTraceAsString() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    StringBuilder builder = new StringBuilder();
    builder.append("Current stack trace:").append(SystemInfo.LF);
    for (int i = 2; i < stackTrace.length; i++) {
      builder.append(stackTrace[i]).append(SystemInfo.LF);
    }
    return builder.toString();
  }

}
