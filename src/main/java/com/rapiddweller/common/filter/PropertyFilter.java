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
package com.rapiddweller.common.filter;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Condition;
import com.rapiddweller.common.ExceptionMapper;
import com.rapiddweller.common.Filter;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Filter that matches a JavaBean by checking a Condition for one of its property values.
 * Created: 04.02.2007 00:47:13
 * @param <E> the bean type
 * @param <P> the property type
 * @since 0.1
 * @author Volker Bergmann
 */
public class PropertyFilter<E, P> implements Filter<E> {

    private final Method propertyReadMethod;
    private final Condition<P> propertyCondition;

    public PropertyFilter(Class<E> type, String propertyName, Condition<P> propertyCondition) {
        try {
            this.propertyReadMethod = type.getMethod(BeanUtil.readMethodName(propertyName, type));
            this.propertyCondition = propertyCondition;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // How could this ever happen!
        }
    }

    @Override
	@SuppressWarnings("unchecked")
    public boolean accept(E candidate) {
        try {
            P propertyValue = (P) propertyReadMethod.invoke(candidate);
            return propertyCondition.evaluate(propertyValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ExceptionMapper.configurationException(e, propertyReadMethod);
        }
    }
    
}
