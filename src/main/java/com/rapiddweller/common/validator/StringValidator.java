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
 * Validates a String by length and characters.
 * Created at 14.08.2008 09:19:51
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class StringValidator extends StringLengthValidator {
	
	private final Validator<Character> charValidator;
	
	// constructors ----------------------------------------------------------------------------------------------------

	public StringValidator() {
		this(null);
	}

	public StringValidator(Validator<Character> charValidator) {
		this(charValidator, 0, null);
	}

	public StringValidator(int minLength, Integer maxLength) {
		this(null, minLength, maxLength);
	}

	public StringValidator(Validator<Character> charValidator, int minLength, Integer maxLength) {
		super(minLength, maxLength);
		this.charValidator = charValidator;
	}

	// Validator implementation ----------------------------------------------------------------------------------------

	@Override
	public boolean valid(String text) {
		if (!super.valid(text))
			return false;
		if (charValidator != null)
			for (int i = 0 ; i < text.length(); i++)
				if (!charValidator.valid(text.charAt(i)))
					return false;
		return true;
	}
}
