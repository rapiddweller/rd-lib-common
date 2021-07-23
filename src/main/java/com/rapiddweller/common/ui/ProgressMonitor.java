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
 * Platform-independent abstraction of a progress monitor.
 * Created at 14.12.2008 20:29:28
 *
 * @author Volker Bergmann
 * @since 0.4.7
 */
public interface ProgressMonitor {

  /**
   * Gets maximum.
   *
   * @return the maximum
   */
  int getMaximum();

  /**
   * Sets maximum.
   *
   * @param i the
   */
  void setMaximum(int i);

  /**
   * Gets progress.
   *
   * @return the progress
   */
  int getProgress();

  /**
   * Sets progress.
   *
   * @param i the
   */
  void setProgress(int i);

  /**
   * Sets note.
   *
   * @param note the note
   */
  void setNote(String note);

  /**
   * Advance.
   */
  void advance();

  /**
   * Is canceled boolean.
   *
   * @return the boolean
   */
  boolean isCanceled();
}
