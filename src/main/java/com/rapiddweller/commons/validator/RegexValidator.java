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

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.rapiddweller.commons.Validator;
import com.rapiddweller.commons.validator.bean.AbstractConstraintValidator;

/**
 * Databene {@link Validator} and JSR 303 {@link ConstraintValidator} implementation 
 * that validates a String by a regular expression.
 * Created at 15.07.2009 15:22:21
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class RegexValidator extends AbstractConstraintValidator<Pattern, String> {
	
	private static final Map<Flag, Integer> flagConstants;
	
	static {
		flagConstants = new HashMap<>();
		flagConstants.put(Flag.CANON_EQ, java.util.regex.Pattern.CANON_EQ);
		flagConstants.put(Flag.CASE_INSENSITIVE, java.util.regex.Pattern.CASE_INSENSITIVE);
		flagConstants.put(Flag.COMMENTS, java.util.regex.Pattern.COMMENTS);
		flagConstants.put(Flag.DOTALL, java.util.regex.Pattern.DOTALL);
		flagConstants.put(Flag.MULTILINE, java.util.regex.Pattern.MULTILINE);
		flagConstants.put(Flag.UNICODE_CASE, java.util.regex.Pattern.UNICODE_CASE);
		flagConstants.put(Flag.UNIX_LINES, java.util.regex.Pattern.UNIX_LINES);
	}
	
	private String regexp;
	private Flag[] flags = new Flag[0];
	private java.util.regex.Pattern pattern;
	private boolean nullValid;

    public RegexValidator() {
	    this(null);
    }

    public RegexValidator(String regexp) {
	    this(regexp, new Flag[0]);
    }

    public RegexValidator(String regexp, Flag... flags) {
	    setRegexp(regexp);
	    setFlags(flags);
    }

    public RegexValidator(String regexp, boolean nullValid, Flag... flags) {
    	this.nullValid = nullValid;
	    setRegexp(regexp);
	    setFlags(flags);
    }

	public boolean isNullValid() {
		return nullValid;
	}
	
	public void setNullValid(boolean nullValid) {
		this.nullValid = nullValid;
	}

	@Override
    public void initialize(Pattern params) {
	    setRegexp(params.regexp());
	    setFlags(params.flags());
    }

	@Override
	public boolean isValid(String string, ConstraintValidatorContext context) {
		if (string == null)
			return nullValid;
    	return pattern.matcher(string).matches();
    }

    public String getRegexp() {
	    return regexp;
    }

    public void setRegexp(String regexp) {
	    this.regexp = regexp;
	    if (this.regexp != null)
	    	this.pattern = java.util.regex.Pattern.compile(regexp, flagsAsNumber());
    }

    public Flag[] getFlags() {
    	return flags;
    }

    public void setFlags(Flag[] flags) {
	    this.flags = flags;
	    if (this.regexp != null)
	    	this.pattern = java.util.regex.Pattern.compile(regexp, flagsAsNumber());
    }

    private int flagsAsNumber() {
	    int bits = 0;
	    for (Flag flag : flags)
	    	bits |= flagConstants.get(flag);
	    return bits;
    }
    
    @Override
    public String toString() {
    	return super.toString();
    }
    
}
