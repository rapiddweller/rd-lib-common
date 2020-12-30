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
package com.rapiddweller.common;

import com.rapiddweller.common.converter.ToStringConverter;

/**
 * Helper class for managing escalations.
 * @author Volker Bergmann
 * @since 0.2.04
 */
public class Escalation {
    
    public final String message;
    public final Object originator;
    public final Object cause;
    
    public Escalation(String message, Object originator, Object cause) {
        super();
        Assert.notNull(message, "message");
        this.message = message;
        this.originator = originator;
        this.cause = cause;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (originator != null)
            builder.append(originator).append(": ");
        builder.append(message);
        if (cause != null)
            builder.append(": ").append(ToStringConverter.convert(cause, ""));
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result
                + ((originator == null) ? 0 : originator.hashCode());
        return result;
    }

    /**
     * Two Escalations are considered equal if originator and message are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Escalation other = (Escalation) obj;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (originator == null) {
            return other.originator == null;
        } else return originator.equals(other.originator);
    }

    
}
