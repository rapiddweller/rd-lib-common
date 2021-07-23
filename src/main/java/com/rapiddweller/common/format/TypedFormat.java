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

package com.rapiddweller.common.format;

import java.text.Format;

/**
 * {@link Format} that exhibits the information which Java class it can format and parse.
 * Created: 11.05.2005 22:04:05
 *
 * @param <S> the source type
 * @author Volker Bergmann
 * @since 0.1
 */
@SuppressWarnings("serial")
public abstract class TypedFormat<S> extends Format {

  /**
   * Gets source type.
   *
   * @return the source type
   */
  public abstract Class<S> getSourceType();
}
