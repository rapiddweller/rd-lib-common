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
package com.rapiddweller.commons.converter.util;

/**
 * {@link ClassProvider} implementation which provides the same bean class on any call.
 * Created: 18.09.2014 18:15:27
 * @param <E> the class to provide
 * @since 1.0.0
 * @author Volker Bergmann
 */

public class ConstantClassProvider<E> implements ClassProvider<E> {

	private final Class<?> beanClass;

	public ConstantClassProvider(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public Class<?> classFor(Object data) {
		return beanClass;
	}

}
