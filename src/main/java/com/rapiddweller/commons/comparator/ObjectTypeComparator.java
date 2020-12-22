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
package com.rapiddweller.commons.comparator;

import java.util.Comparator;

/**
 * Comparator that compares objects by its Java type with a predefined order.
 * Created: 22.05.2007 18:19:54
 * @author Volker Bergmann
 */
public class ObjectTypeComparator implements Comparator<Object> {

    private TypeComparator typeComparator;

    public ObjectTypeComparator(Class<?> ... orderedClasses) {
    	typeComparator = new TypeComparator(orderedClasses);
    }

    @Override
	public int compare(Object o1, Object o2) {
        return typeComparator.compare(o1.getClass(), o2.getClass());
    }

}
