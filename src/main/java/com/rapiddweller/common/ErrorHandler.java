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

import com.rapiddweller.common.exception.ExceptionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * Provides for error handling by logging and eventually raising an exception.
 * Created at 02.08.2008 13:39:11
 * @author Volker Bergmann
 * @since 0.4.5
 */
public class ErrorHandler {

  // attributes ------------------------------------------------------------------------------------------------------

  private final Logger logger;
  private final String category;
  private final Level level;
  private boolean loggingStackTrace;

  // constructors ----------------------------------------------------------------------------------------------------

  public ErrorHandler(Class<?> category) {
    this(category.getName());
  }

  public ErrorHandler(String category) {
    this(category, Level.fatal);
  }

  public ErrorHandler(String category, Level level) {
    this.category = category;
    this.logger = LoggerFactory.getLogger(category);
    this.level = level;
    this.loggingStackTrace = true;
  }

  // interface -------------------------------------------------------------------------------------------------------

  public void handleError(String message) {
    switch (level) {
      // yes, this could be more efficient, but it's just for error handling
      // and you're not supposed to have a number of errors that impacts performance
      case trace:
        logger.trace(message);
        break;
      case debug:
        logger.debug(message);
        break;
      case info:
        logger.info(message);
        break;
      case warn:
        logger.warn(message);
        break;
      case error:
        logger.error(message);
        break;
      case fatal:
        logger.error(message);
        throw ExceptionFactory.getInstance().operationFailed(message, null);
      case ignore: // ignore
    }
  }

  public void handleError(String message, Throwable t) {
    if (loggingStackTrace) {
      switch (level) {
        // yes, this could be more efficient, but it's just for error handling
        // and you're not supposed to have a number of errors that impacts performance
        case trace:
          logger.trace(message);
          break;
        case debug:
          logger.debug(message);
          break;
        case info:
          logger.info(message);
          break;
        case warn:
          logger.warn(message);
          break;
        case error:
          logger.error(message, t);
          break;
        case fatal:
          if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
          } else {
            throw ExceptionFactory.getInstance().operationFailed(message, t);
          }
        case ignore:
          break; // ignore
      }
    } else {
      handleError(message + SystemInfo.getLineSeparator() + t.toString());
    }
  }

  // properties ------------------------------------------------------------------------------------------------------

  public String getCategory() {
    return category;
  }

  public Level getLevel() {
    return level;
  }

  public boolean isLoggingStackTrace() {
    return loggingStackTrace;
  }

  public void setLoggingStackTrace(boolean loggingStackTrace) {
    this.loggingStackTrace = loggingStackTrace;
  }

  // static utilities ------------------------------------------------------------------------------------------------

  private static ErrorHandler defaultInstance = new ErrorHandler(ErrorHandler.class);

  public static ErrorHandler getDefault() {
    return defaultInstance;
  }

  private static Level defaultLevel = Level.fatal;

  public static Level getDefaultLevel() {
    return defaultLevel;
  }

  public static void setDefaultLevel(Level level) {
    defaultLevel = level;
    if (defaultInstance.getLevel() != level) {
      defaultInstance = new ErrorHandler(defaultInstance.logger.getName(), level);
    }
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorHandler that = (ErrorHandler) o;
    return this.category.equals(that.category) && this.level == that.level;
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, level);
  }

}
