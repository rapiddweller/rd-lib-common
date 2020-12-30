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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Escalator;
import com.rapiddweller.common.LoggerEscalator;

/**
 * Internationalization utilities.
 * Created at 21.07.2008 07:32:56
 * @since 0.4.5
 * @author Volker Bergmann
 */
public class I18NSupport {
	
	private static final Escalator escalator = new LoggerEscalator();
	
	private final String name;
	private final ResourceBundle bundle;

	public I18NSupport(String name, Locale locale) {
		this.name = name;
		this.bundle = PropertyResourceBundle.getBundle(name, locale);
	}

	public String getName() {
		return name;
	}
	
	public Locale getLocale() {
		return bundle.getLocale();
	}

	public String getString(String resourceName) {
		String string;
		try {
			string = bundle.getString(resourceName);
		} catch (MissingResourceException e) {
			escalator.escalate("Resource not defined: " + resourceName, this, null);
			string = resourceName;
		}
		return string;
	}
	
	public String format(String resourceName, Object... args) {
		return MessageFormat.format(getString(resourceName), args);
	}
	
	@Override
	public String toString() {
		return BeanUtil.toString(this);
	}
}
