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

package com.rapiddweller.common.log;

import com.rapiddweller.common.ui.InfoPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@link InfoPrinter} implementation that writes text to a logger category.
 * Created: 01.08.2010 17:13:04
 *
 * @author Volker Bergmann
 * @since 0.5.3
 */
public class LoggingInfoPrinter extends InfoPrinter {

  private final Logger logger;

  /**
   * Instantiates a new Logging info printer.
   *
   * @param clazz the clazz
   */
  public LoggingInfoPrinter(Class<?> clazz) {
    this.logger = LogManager.getLogger(clazz);
  }

  /**
   * Instantiates a new Logging info printer.
   *
   * @param category the category
   */
  public LoggingInfoPrinter(String category) {
    this.logger = LogManager.getLogger(category);
  }

  @Override
  public void printLines(Object owner, String... infoLines) {
    for (String info : infoLines) {
      logger.info(info);
    }
  }

}
