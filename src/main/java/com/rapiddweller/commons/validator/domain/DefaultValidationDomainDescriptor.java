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
package com.rapiddweller.commons.validator.domain;

import java.lang.reflect.Modifier;
import java.util.List;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.Filter;
import com.rapiddweller.commons.ValidationDomainDescriptor;
import com.rapiddweller.commons.Validator;
import com.rapiddweller.commons.filter.FilterUtil;

/**
 * Default implementation of the {@link ValidationDomainDescriptor} interface.
 * Created: 20.12.2011 16:53:55
 * @since 0.5.14
 * @author Volker Bergmann
 */
public class DefaultValidationDomainDescriptor extends AbstractValidationDomainDescriptor {
	
	private List<Class<? extends Validator<?>>> validatorClasses;
	
	/** This constructor is assumed to be used by child classes - it used the child class' package name
	 * to search Validator instances. */
	protected DefaultValidationDomainDescriptor() {
		init(getClass().getPackage().getName());
	}
	
	public DefaultValidationDomainDescriptor(String packageName) {
		init(packageName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(String packageName) {
		List<Class<?>> candidates = BeanUtil.getClasses(packageName);
		this.validatorClasses = (List) FilterUtil.filter(candidates, new ValidatorClassFilter());
	}

	public class ValidatorClassFilter implements Filter<Class<?>> {
		@Override
		public boolean accept(Class<?> candidate) {
			return Validator.class.isAssignableFrom(candidate) 
				&& !Modifier.isAbstract(candidate.getModifiers());
		}
	}

	@Override
	public List<Class<? extends Validator<?>>> getValidatorClasses() {
		return validatorClasses;
	}

}
