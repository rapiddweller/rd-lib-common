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

package com.rapiddweller.common.ui;

/**
 * Abstraction of file operation types.
 * Created at 14.12.2008 15:19:56
 *
 * @author Volker Bergmann
 * @since 0.4.7
 */
public enum FileOperation {
  /**
   * Open file operation.
   */
  OPEN,
  /**
   * Save file operation.
   */
  SAVE,
  /**
   * Custom file operation.
   */
  CUSTOM
}
