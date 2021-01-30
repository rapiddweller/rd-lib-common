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
package com.rapiddweller.common.ui;

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.ui.osx.OSXUtil;

import javax.swing.UIManager;

/**
 * Provides utility methods for Java applications.
 * Created: 03.09.2010 16:16:12
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class ApplicationUtil {

	public static void prepareNativeLAF(String appName) {
		if (SystemInfo.isMacOsx())
		    prepareMacVM(appName);
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private static void prepareMacVM(String applicationName) {
	    System.setProperty("apple.awt.brushMetalLook", "true");
	    System.setProperty("apple.laf.useScreenMenuBar", "true");
	    System.setProperty("com.apple.mrj.application.apple.menu.about.name", applicationName);
    }

	public static void configureApplication(JavaApplication application) {
	    if (SystemInfo.isMacOsx())
	    	OSXUtil.configureApplication(application);
    }
	
}
