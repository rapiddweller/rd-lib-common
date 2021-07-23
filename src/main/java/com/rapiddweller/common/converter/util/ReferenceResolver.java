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

package com.rapiddweller.common.converter.util;

/**
 * Resolves object references.
 * Created: 19.09.2014 16:10:42
 *
 * @author Volker Bergmann
 * @since 1.0.0
 */
public interface ReferenceResolver {
  /**
   * Resolve references object.
   *
   * @param value       the value
   * @param target      the target
   * @param featureName the feature name
   * @return the object
   */
  Object resolveReferences(Object value, Object target, String featureName);
}
