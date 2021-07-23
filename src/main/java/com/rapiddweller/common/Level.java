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

/**
 * Represents a log or failure level.
 * Created at 18.09.2009 13:46:48
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public enum Level {
  /**
   * Ignore level.
   */
  ignore,
  /**
   * Trace level.
   */
  trace,
  /**
   * Debug level.
   */
  debug,
  /**
   * Info level.
   */
  info,
  /**
   * Warn level.
   */
  warn,
  /**
   * Error level.
   */
  error,
  /**
   * Fatal level.
   */
  fatal
}
