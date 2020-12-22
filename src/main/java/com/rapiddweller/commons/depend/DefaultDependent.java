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
package com.rapiddweller.commons.depend;

/**
 * Default implementation of the Dependent interface.
 * @param <S> the type of the objects to process
 * @param <E> the provider type
 * @author Volker Bergmann
 * @since 0.3.04
 */
public class DefaultDependent<S, E extends Dependent<E>> extends AbstractDependent<E> {

    private S subject;
    
    public DefaultDependent(S subject, E ... requiredProviders) {
        super(requiredProviders);
        this.subject = subject;
    }

    /**
     * @return the subject
     */
    public S getSubject() {
        return subject;
    }
    
    // java.lang.Object overrides ---------------------------------------------------------------------------

    @Override
    public int hashCode() {
        return subject.hashCode();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
		DefaultDependent that = (DefaultDependent) obj;
        return (this.subject != null ? this.subject.equals(that.subject) : that.subject == null);
    }
    
    @Override
    public String toString() {
        return subject.toString();
    }
}
