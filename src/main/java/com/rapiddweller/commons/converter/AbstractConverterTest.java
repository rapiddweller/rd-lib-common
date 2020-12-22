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
package com.rapiddweller.commons.converter;

import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.Converter;

/**
 * Parent class for {@link Converter} test classes.
 * Created: 26.02.2010 16:29:48
 * @since 0.5.0
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractConverterTest {
	
	private Class<? extends Converter> converterClass;

    public AbstractConverterTest(Class<? extends Converter> converterClass) {
		this.converterClass = converterClass;
	}
	
	public void verifyParallelizable() {
		Converter<?, ?> converter;
        try {
	        converter = BeanUtil.newInstance(converterClass);
        } catch (Exception e) {
	        return; // if there is no default constructor, we can't test
        }
	    checkParallelizable(converter);
	}

	private void checkParallelizable(Converter<?, ?> converter) {
	    if (converter.isParallelizable()) {
	    	Assert.isTrue(converter instanceof Cloneable, "Parallelizable converters must implement " + Cloneable.class);
	    	Assert.isTrue(BeanUtil.findMethod(converterClass, "clone", (Class[]) null) != null, 
	    			"Parallelizable converters must have a public clone() method");
	    }
    }
	
}
