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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.ConfigurationError;
import com.rapiddweller.commons.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Provides classes by name, supporting package import and local class names.
 * Created at 15.11.2008 17:03:04
 * @since 0.4.6
 * @author Volker Bergmann
 */
public class ClassCache {
	
	private static final Logger LOGGER = LogManager.getLogger(ClassCache.class);
	
    private final Map<String, Class<?>> classes;
	private final List<String> packages;
	private final Set<String> nonClassNames = new HashSet<>(1000);
	
    public ClassCache() {
		classes = new HashMap<>();
		packages = new ArrayList<>();
		importPackage("java.lang");
	}

	public void importClass(String className) {
		className = className.trim();
		if (className.endsWith(".*"))
			importPackage(className.substring(0, className.length() - 2));
		else 
			classes.put(StringUtil.lastToken(className, '.'), BeanUtil.forName(className));
	}

	public void importPackage(String packageName) {
		packages.add(packageName);
	}

    public Class<?> forName(String name) {
		Class<?> result = classes.get(name);
		if (result != null)
			return result;
		if (!nonClassNames.contains(name)) {
			try {
				result = BeanUtil.forName(name);
				classes.put(result.getSimpleName(), result);
				return result;
			} catch (ConfigurationError e) {
				nonClassNames.add(name);
				LOGGER.debug("class not found: {}", name);
			}
		}
		for (String pkg : packages) {
			String fqnTrial = pkg + '.' + name;
			if (!nonClassNames.contains(fqnTrial)) {
				try {
					result = BeanUtil.forName(fqnTrial);
					classes.put(result.getSimpleName(), result);
					return result;
				} catch (ConfigurationError e) {
					nonClassNames.add(fqnTrial);
					LOGGER.debug("class not found: {}", name);
				}
			}
		}
		throw new ConfigurationError("Class not found: " + name);
	}
}
