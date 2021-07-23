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

package com.rapiddweller.common.context;

import com.rapiddweller.common.Context;

/**
 * Interface for all classes that can make use of a {@link Context}.
 * Created at 20.07.2009 09:11:28
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public interface ContextAware {
  /**
   * Sets context.
   *
   * @param context the context
   */
  void setContext(Context context);
}
