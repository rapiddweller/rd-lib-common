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

package com.rapiddweller.common.debug;

import com.rapiddweller.common.SystemInfo;

/**
 * Wrapper class for resources that are monitored.
 * It stores the monitored object itself and its allocation stack trace.
 * Created: 14.04.2011 17:22:56
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class MonitoredResource {

  /**
   * The Resource.
   */
  final Object resource;
  /**
   * The Registration trace.
   */
  final StackTraceElement[] registrationTrace;

  /**
   * Instantiates a new Monitored resource.
   *
   * @param resource          the resource
   * @param registrationTrace the registration trace
   */
  public MonitoredResource(Object resource, StackTraceElement[] registrationTrace) {
    this.resource = resource;
    this.registrationTrace = registrationTrace;
  }

  private static String toString(StackTraceElement[] trace) {
    StringBuilder builder = new StringBuilder();
    boolean first = true;
    for (StackTraceElement element : trace) {
      appendTraceElement(element, first, builder);
      first = false;
    }
    return builder.toString();
  }

  private static void appendTraceElement(StackTraceElement element, boolean first, StringBuilder builder) {
    builder.append(SystemInfo.getLineSeparator());
    builder.append("\t");
    if (!first) {
      builder.append("at ");
    }
    builder.append(element.getClassName()).append('.').append(element.getMethodName());
    if (element.getLineNumber() > 0) {
      builder.append(" (Line ").append(element.getLineNumber()).append(")");
    }
  }

  @Override
  public String toString() {
    return "Monitored Object: " + resource.toString() + SystemInfo.getLineSeparator() +
        "Registration stack:" +
        toString(registrationTrace);
  }

}
