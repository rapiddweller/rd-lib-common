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
package com.rapiddweller.commons.validator;

import com.rapiddweller.commons.Validator;

/**
 * Requires that each validated object is equal to a reference object.
 * Created at 04.07.2009 06:59:57
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class ConstantValidator implements Validator<Object> {

	private Object expectedValue;
	
    public ConstantValidator() {
    	this(null);
    }

    public ConstantValidator(Object expectedValue) {
	    this.expectedValue = expectedValue;
    }

    @Override
	public boolean valid(Object value) {
        return (expectedValue.equals(value));
    }

}
