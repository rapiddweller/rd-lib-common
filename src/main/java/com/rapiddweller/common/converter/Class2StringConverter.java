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

/**
 * Converts a {@link Class} object to its fully qualified class name.
 * Created: 25.02.2010 23:37:15
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
@SuppressWarnings("rawtypes")
public class Class2StringConverter extends ThreadSafeConverter<Class, String> {

  /**
   * Instantiates a new Class 2 string converter.
   */
  public Class2StringConverter() {
    super(Class.class, String.class);
  }

  @Override
  public String convert(Class clazz) throws ConversionException {
    return clazz.getName();
  }

}
