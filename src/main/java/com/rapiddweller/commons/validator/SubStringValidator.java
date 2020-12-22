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

import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.Validator;
import com.rapiddweller.commons.converter.SubstringExtractor;

/**
 * Uses another validator to validate sub strings.
 * Created: 02.08.2011 07:19:04
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class SubStringValidator extends AbstractValidator<String> {

	protected Converter<String, String> substringExtractor;
	protected Validator<String> realValidator;

	public SubStringValidator(int from, Integer to, Validator<String> realValidator) {
		this.substringExtractor = new SubstringExtractor(from, to);
		this.realValidator = realValidator;
	}


	@Override
	public boolean valid(String value) {
		if (value == null)
			return false;
		return realValidator.valid(substringExtractor.convert(value));
	}

}
