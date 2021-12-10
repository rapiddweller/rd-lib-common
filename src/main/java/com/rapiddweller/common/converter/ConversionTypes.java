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

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.bean.HashCodeBuilder;

/**
 * Converter id class for the ConverterManager.
 * Created: 27.02.2010 05:45:43
 * @author Volker Bergmann
 * @since 0.5.0
 */
class ConversionTypes {

  public final Class<?> sourceType;
  public final Class<?> targetType;

  public ConversionTypes(Converter<?, ?> converter) {
    this(converter.getSourceType(), converter.getTargetType());
  }

  public ConversionTypes(Class<?> sourceType, Class<?> targetType) {
    this.sourceType = sourceType;
    this.targetType = targetType;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.hashCode(sourceType, targetType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    ConversionTypes that = (ConversionTypes) obj;
    return (this.sourceType == that.sourceType && this.targetType == that.targetType);
  }

  @Override
  public String toString() {
    return sourceType.getName() + "->" + targetType.getName();
  }

}
