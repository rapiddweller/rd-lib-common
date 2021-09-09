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

package com.rapiddweller.common.bean;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.StringUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides classes by name, supporting package import and local class names.
 * Created at 15.11.2008 17:03:04
 * @author Volker Bergmann
 * @since 0.4.6
 */
public class ClassCache {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassCache.class);

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
    if (className.endsWith(".*")) {
      importPackage(className.substring(0, className.length() - 2));
    } else {
      classes.put(StringUtil.lastToken(className, '.'), BeanUtil.forName(className));
    }
  }

  public void importPackage(String packageName) {
    packages.add(packageName);
  }

  public Class<?> forName(String name) {
    Class<?> result = classes.get(name);
    if (result != null) {
      return result;
    }
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
    // return null instead of raising an exception if a name does not resolve to a class name.
    // This has better performance in rapiddweller script evaluation,
    // which first checks for class names, then for variable names.
    return null;
  }
}
