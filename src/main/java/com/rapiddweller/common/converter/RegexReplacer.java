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
package com.rapiddweller.common.converter;

import com.rapiddweller.common.ConversionException;

import java.util.regex.Pattern;

/**
 * Converts strings using a regular expression.
 * Each part of the 'input' string that matches the regular expression 'pattern' 
 * is replaced with the 'replacement' string.
 * Created: 22.02.2010 07:12:12
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class RegexReplacer extends ThreadSafeConverter<String, String> {
	
	private Pattern pattern;
	private String replacement;

	public RegexReplacer() {
	    this(null, null);
    }

	public RegexReplacer(String pattern, String replacement) {
	    super(String.class, String.class);
	    setPattern(pattern);
	    setReplacement(replacement);
    }

	private void setReplacement(String replacement) {
	    this.replacement = replacement;
    }

	public void setPattern(String pattern) {
	    this.pattern = Pattern.compile(pattern);
    }

	@Override
	public String convert(String input) throws ConversionException {
	    return this.pattern.matcher(input).replaceAll(this.replacement);
    }

	public String convert(String input, String replacement) throws ConversionException {
	    return this.pattern.matcher(input).replaceAll(replacement);
    }

	public static String convert(String input, String pattern, String replacement) {
		return new RegexReplacer(pattern, replacement).convert(input);
	}
	
}
