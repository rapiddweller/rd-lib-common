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
 * Validates a String for a minimum and a maximum length.
 * Created: 20.09.2006 21:38:20
 * @author Volker Bergmann
 */
public class StringLengthValidator implements Validator<String> {

    /** the minimum length of the string */
    private int minLength;

    /** the maximum length of the string. If null, the size is not limited */
    private Integer maxLength;

    private boolean nullAllowed;

    // constructors ----------------------------------------------------------------------------------------------------

    /** Creates a validator that accepts any string */
    public StringLengthValidator() {
        this(0, null);
    }

    /** Creates a validator of a maximum length 
     * @param maxLength maximum tolerated length */
    public StringLengthValidator(int maxLength) {
        this(0, maxLength);
    }

    /** Creates a validator of a minimum and maximum length 
     * @param minLength minimum tolerated length
     * @param maxLength maximum tolerated length */
    public StringLengthValidator(int minLength, Integer maxLength) {
        setMinLength(minLength);
        setMaxLength(maxLength);
    }

    public StringLengthValidator(int minLength, Integer maxLength, boolean nullAllowed) {
        setMinLength(minLength);
        setMaxLength(maxLength);
        setNullAllowed(nullAllowed);
    }


    // properties ------------------------------------------------------------------------------------------------------

    /**
     * Returns the minimum length.
     * @return the minimum length.
     * @see #minLength
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Sets the minimum length.
     * @param minLength the new minimum length
     */
    public void setMinLength(int minLength) {
        if (minLength < 0)
            throw new IllegalArgumentException("minLength may not be less than 0, but was: " + minLength);
        this.minLength = minLength;
    }

    /**
     * Returns the maximum length.
     * @return the maximum length.
     * @see #maxLength
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the maximum length. If set to null, no maximum length check is done.
     * @param maxLength the new minimum length
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    private void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }

    // validator implementation ----------------------------------------------------------------------------------------

    /**
     * @see com.rapiddweller.common.Validator
     * @param text the string to validate
     * @return true if the length restrictions match, otherwise false.
     */
    @Override
	public boolean valid(String text) {
        if (text == null)
            return nullAllowed;
        return (text.length() >= minLength && (maxLength == null || text.length() <= maxLength));
    }

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getSimpleName());
        builder.append('[');
        if (minLength > 0)
            builder.append(minLength).append("<=length");
        if (maxLength != null) {
            if (minLength <= 0)
                builder.append("length");
            builder.append("<=").append(maxLength);
        }
        builder.append(']');
        return builder.toString();
    }
}
