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

import com.rapiddweller.common.CharSet;
import com.rapiddweller.common.Validator;

/**
 * Accepts all characters that are contained in the internal {@link CharSet}.
 * Created: 17.12.2009 19:00:41
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class CharacterValidator implements Validator<Character> {

	private final CharSet charSet;

	public CharacterValidator(CharSet charSet) {
	    this.charSet = charSet;
    }
	
	public CharSet getCharSet() {
    	return charSet;
    }

	@Override
	public boolean valid(Character c) {
	    return charSet.contains(c);
    }
	
}
