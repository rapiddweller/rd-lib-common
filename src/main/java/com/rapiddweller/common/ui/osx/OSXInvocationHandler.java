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
package com.rapiddweller.common.ui.osx;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ui.JavaApplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvocationHandler interface for Mac OS X.
 * Created: 10.09.2010 09:18:57
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class OSXInvocationHandler implements InvocationHandler {
	
	private final JavaApplication application;
	
	public OSXInvocationHandler(JavaApplication application) {
	    this.application = application;
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		if ("handleQuit".equals(method.getName())) {
			application.exit();
		} else if ("handleAbout".equals(method.getName())) {
			BeanUtil.invoke(args[0], "setHandled", true);
			application.about();
		} else if ("handlePreferences".equals(method.getName())) {
			application.preferences();
		}
		return null;
	}

}
