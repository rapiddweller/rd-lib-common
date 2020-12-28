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
package com.rapiddweller.commons.bean;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.accessor.TypedAccessor;
import com.rapiddweller.commons.accessor.TypedAccessorChain;

/**
 * Accesses JavaBean object graphs.
 * Created: 21.07.2007 10:18:17
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class PropertyGraphAccessor extends TypedAccessorChain implements PropertyAccessor {

    private final String propertyName;

    public PropertyGraphAccessor(Class<?> beanClass, String propertyName, boolean strict) {
        super(createSubAccessors(beanClass, propertyName, strict));
        this.propertyName = propertyName;
    }
    
    @Override
	public String getPropertyName() {
        return propertyName;
    }

    
    // static utility methods ------------------------------------------------------------------------------------------
    
    public static Object getPropertyGraph(String path, Object bean) {
    	String[] tokens = StringUtil.splitOnFirstSeparator(path, '.');
    	Object tmp = BeanUtil.getPropertyValue(bean, tokens[0]);
    	if (tokens[1] != null && tmp != null)
    		return getPropertyGraph(tokens[1], tmp);
    	else
    		return tmp;
    }

    private static TypedAccessor[] createSubAccessors(Class<?> beanClass, String propertyName, boolean strict) {
        String[] nodeNames = StringUtil.tokenize(propertyName, '.');
        PropertyAccessor[] nodes = new PropertyAccessor[nodeNames.length];
        Class<?> intermediateClass = beanClass;
        for (int i = 0; i < nodeNames.length; i++) {
            PropertyAccessor node = PropertyAccessorFactory.getAccessor(intermediateClass, nodeNames[i], strict);
            nodes[i] = node;
            if (intermediateClass != null)
                intermediateClass = node.getValueType();
        }
        return nodes;
    }
    
}
