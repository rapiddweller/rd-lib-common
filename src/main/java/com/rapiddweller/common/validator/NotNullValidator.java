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
package com.rapiddweller.common.validator;

import com.rapiddweller.common.Validator;

/**
 * Validator implementation that checks if a value is not null.
 * Created: 01.09.2007 08:53:07
 * @param <E> the object type to be validated
 * @author Volker Bergmann
 */
public class NotNullValidator<E> implements Validator<E> {

    @Override
	public boolean valid(E object) {
        return (object != null);
    }
}
