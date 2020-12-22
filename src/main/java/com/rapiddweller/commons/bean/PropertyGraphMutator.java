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
import com.rapiddweller.commons.UpdateFailedException;
import com.rapiddweller.commons.mutator.NamedMutator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Mutates JavaBean object graphs.
 * Created: 08.05.2005 06:24:41
 * @author Volker Bergmann
 */
public class PropertyGraphMutator implements NamedMutator {

    private static Logger logger = LogManager.getLogger(PropertyGraphMutator.class);

    private boolean required;
    private boolean autoConvert;
    private PropertyAccessor<Object, ?>[] subAccessors;
    private NamedMutator lastMutator;
    private String propertyName;

    // constructors ----------------------------------------------------------------------------------------------------

    public PropertyGraphMutator(String propertyName) {
        this(propertyName, true, false);
    }

    public PropertyGraphMutator(String propertyName, boolean required, boolean autoConvert) {
        this(null, propertyName, required, autoConvert);
    }

    public PropertyGraphMutator(Class<?> beanClass, String propertyName) {
        this(beanClass, propertyName, true, false);
    }

    @SuppressWarnings("unchecked")
    public PropertyGraphMutator(Class<?> beanClass, String propertyName, boolean required, boolean autoConvert) {
        this.propertyName = propertyName;
        this.required = required;
        this.autoConvert = autoConvert;
        int separatorIndex = propertyName.indexOf('.');
        if (separatorIndex >= 0) {
            String[] nodeNames = StringUtil.tokenize(propertyName, '.');
            Class<?> nodeClass = beanClass;
            subAccessors = new PropertyAccessor[nodeNames.length - 1];
            for (int i = 0; i < nodeNames.length - 1; i++) {
                subAccessors[i] = PropertyAccessorFactory.getAccessor(nodeClass, nodeNames[i], required);
                nodeClass = subAccessors[i].getValueType();
            }
            String lastNodeName = nodeNames[nodeNames.length - 1];
            if (beanClass != null)
                lastMutator = PropertyMutatorFactory.getPropertyMutator(
                    subAccessors[subAccessors.length - 1].getValueType(), lastNodeName, required, autoConvert);
            else
                lastMutator = new UntypedPropertyMutator(lastNodeName, required, autoConvert);
        } else
            lastMutator = new TypedPropertyMutator(beanClass, propertyName, required, autoConvert);
    }
    
	public boolean isRequired() {
		return required;
	}
	
	public boolean isAutoConvert() {
		return autoConvert;
	}

    // PropertyMutator interface ---------------------------------------------------------------------------------------

    @Override
	public String getName() {
        return propertyName;
    }

    @Override
	public void setValue(Object bean, Object propertyValue) throws UpdateFailedException {
        if (bean == null)
            if (required)
                throw new IllegalArgumentException("Cannot set a property on null");
            else
                return;
        logger.debug("setting property '" + getName() + "' to '" + propertyValue + "' on bean " + bean);
        Object superBean = bean;
        if (subAccessors != null) {
            for (PropertyAccessor<Object, ?> subAccessor : subAccessors) {
                Object subBean = subAccessor.getValue(superBean);
                if (subBean == null && propertyValue != null) {
                    // bean is null but since there is something to set create one
                    Class<?> propertyType = subAccessor.getValueType();
                    subBean = BeanUtil.newInstance(propertyType);
                    BeanUtil.setPropertyValue(superBean, subAccessor.getPropertyName(), subBean);
                }
                superBean = subBean;
            }
        }
        lastMutator.setValue(superBean, propertyValue);
    }
    
	public static void setPropertyGraph(Object bean, String propertyGraph, Object propertyValue, boolean required, boolean autoConvert) {
		new PropertyGraphMutator(bean.getClass(), propertyGraph, required, autoConvert).setValue(bean, propertyValue);
	}
	
}
