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
package com.rapiddweller.commons.ui.osx;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;

import javax.imageio.ImageIO;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.ui.JavaApplication;

/**
 * Provides utility methods for Mac OS X.
 * Created: 10.09.2010 09:30:01
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class OSXUtil {

	public static void configureApplication(JavaApplication application) {
		// Get OSX Application
    	Class<?> applicationClass = BeanUtil.forName("com.apple.eawt.Application");
    	Object osxApplication = BeanUtil.invokeStatic(applicationClass, "getApplication");
    	if (application.supportsPreferences())
    		BeanUtil.invoke(osxApplication, "setEnabledPreferencesMenu", true);
    	
    	// add ApplicationListener
        Class<?> applicationListenerClass = BeanUtil.forName("com.apple.eawt.ApplicationListener");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Object osxAdapterProxy = Proxy.newProxyInstance(
				classLoader, 
				new Class[] { applicationListenerClass }, 
				new OSXInvocationHandler(application));
		BeanUtil.invoke(osxApplication, "addApplicationListener", new Object[] { osxAdapterProxy });
		
		// set dock icon image
		String iconPath = application.iconPath();
		if (iconPath != null) {
			try {
				InputStream icon = ClassLoader.getSystemResourceAsStream(iconPath);
				BeanUtil.invoke(osxApplication, "setDockIconImage", ImageIO.read(icon));
			} catch (IOException e) {
				// ignore errors 
			}
		}
    }

}
