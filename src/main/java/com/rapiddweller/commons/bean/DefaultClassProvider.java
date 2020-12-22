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

/**
 * Default implementation of the {@link ClassProvider} interface.
 * It forwards the call to {@link BeanUtil}.
 * Created at 16.11.2008 07:05:10
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class DefaultClassProvider implements ClassProvider {
	
	private static DefaultClassProvider instance = new DefaultClassProvider();

    public static ClassProvider getInstance() {
	    return instance;
    }

	@Override
	public Class<?> forName(String className) {
		return BeanUtil.forName(className);
	}

    public static Class<?> resolveByObjectOrDefaultInstance(String className, Object context) {
        ClassProvider classProvider;
		if (context instanceof ClassProvider)
			classProvider = (ClassProvider) context;
		else
			classProvider = DefaultClassProvider.getInstance();
        return classProvider.forName(className);
    }
    
}
