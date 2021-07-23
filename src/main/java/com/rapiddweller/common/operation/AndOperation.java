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

package com.rapiddweller.common.operation;

import com.rapiddweller.common.Operation;

/**
 * Combines boolean arguments with an 'and' condition ignoring null arguments.
 * If the number of arguments is zero, true is returned.
 * Created: 05.03.2008 07:56:59
 *
 * @author Volker Bergmann
 * @since 0.4.0
 */
public class AndOperation implements Operation<Boolean, Boolean> {

  @Override
  public Boolean perform(Boolean... args) {
    for (Boolean arg : args) {
      if (arg != null && arg) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }

}
