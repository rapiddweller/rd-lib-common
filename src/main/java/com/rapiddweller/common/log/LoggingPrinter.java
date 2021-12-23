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

import com.rapiddweller.common.ui.TextPrinter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * {@link TextPrinter} implementation that writes text to a logger category.
 * Created: 01.08.2010 17:13:04
 * @author Volker Bergmann
 * @since 0.5.3
 */
public class LoggingPrinter implements TextPrinter {

  private final Logger logger;

  public LoggingPrinter(Class<?> clazz) {
    this.logger = LoggerFactory.getLogger(clazz);
  }

  public LoggingPrinter(String category) {
    this.logger = LoggerFactory.getLogger(category);
  }

  @Override
  public void printStd(String... infoLines) {
    for (String infoLine : infoLines) {
      logger.info(infoLine);
    }
  }

  @Override
  public void printErr(String... errLines) {
    for (String errLine : errLines) {
      logger.error(errLine);
    }
  }

}
