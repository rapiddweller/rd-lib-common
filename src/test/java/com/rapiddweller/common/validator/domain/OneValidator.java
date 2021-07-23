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

package com.rapiddweller.common.validator.domain;

import com.rapiddweller.common.validator.AbstractValidator;

/**
 * Simple validator class for testing.
 * Created: 20.12.2011 17:07:47
 *
 * @author Volker Bergmann
 * @since 0.5.14
 */
public class OneValidator extends AbstractValidator<Integer> {

  @Override
  public boolean valid(Integer candidate) {
    return (1 == candidate);
  }

}
