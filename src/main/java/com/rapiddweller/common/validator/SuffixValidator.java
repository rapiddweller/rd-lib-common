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
 * Validates the suffix of a String.
 * Created: 02.08.2011 07:29:25
 * @since 0.5.9
 * @author Volker Bergmann
 */
public class SuffixValidator extends SubStringValidator {
	
	protected String suffix;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SuffixValidator(String suffix) {
		super(-2, null, (Validator) new ConstantValidator(suffix));
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + suffix + ']';
	}
	
}
