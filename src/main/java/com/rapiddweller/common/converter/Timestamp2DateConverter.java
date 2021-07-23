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

package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Converts {@link Timestamp}s to {@link Date}s.
 * Created: 25.02.2010 23:26:35
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class Timestamp2DateConverter extends ThreadSafeConverter<Timestamp, Date> {

  /**
   * Instantiates a new Timestamp 2 date converter.
   */
  public Timestamp2DateConverter() {
    super(Timestamp.class, Date.class);
  }

  @Override
  public Date convert(Timestamp target) throws ConversionException {
    return new Date(target.getTime());
  }

}
