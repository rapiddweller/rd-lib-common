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
package com.rapiddweller.common.bean;

import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.comparator.ComparatorFactory;

import java.util.Comparator;

/**
 * Compares JavaBeans by a property.
 * If a beanComparator is provided, this one is used for property comparison,
 * else the ComparatorFactory is queried for a Comparator.
 * Created: 06.01.2005 20:04:36
 * @param <E> the type of objects to be compared
 * @author Volker Bergmann
 * @see ComparatorFactory
 */
public class BeanComparator<E> implements Comparator<E> {

    private final Comparator<Object> propertyComparator;
	private final PropertyAccessor<E, ?> propertyAccessor;

    // constructor -----------------------------------------------------------------------------------------------------

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BeanComparator(String propertyName) {
        this.propertyComparator = new ComparableComparator();
        try {
            this.propertyAccessor = PropertyAccessorFactory.getAccessor(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BeanComparator(Class<?> comparedClass, String propertyName) {
        this(comparedClass, propertyName, getComparator(comparedClass, propertyName));
    }

    @SuppressWarnings("unchecked")
    public BeanComparator(Class<?> comparedClass, String propertyName, Comparator<?> comparator) {
        this.propertyComparator = (Comparator<Object>) comparator;
        try {
            this.propertyAccessor = PropertyAccessorFactory.getAccessor(comparedClass, propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // interface -------------------------------------------------------------------------------------------------------

    @Override
	public int compare(E o1, E o2) {
        try {
        	Object v1 = propertyAccessor.getValue(o1);
        	Object v2 = propertyAccessor.getValue(o2);
            return propertyComparator.compare(v1, v2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // private helpers -------------------------------------------------------------------------------------------------

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Comparator getComparator(Class<?> comparedClass, String propertyName) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.getAccessor(comparedClass, propertyName);
        Comparator<?> beanComparator = ComparatorFactory.getComparator(propertyAccessor.getValueType());
        if (beanComparator == null)
            throw new IllegalArgumentException("Property '" + comparedClass.getName() + '.' + propertyName + "' " +
                    "is expected to implement Comparable");
        return beanComparator;
    }

}
