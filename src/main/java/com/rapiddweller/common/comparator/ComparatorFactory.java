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
package com.rapiddweller.common.comparator;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ComparableComparator;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.Collator;

/**
 * Creates comparators by the type of the objects to be compared.
 * Created: 22.10.2005 21:29:08
 * @author Volker Bergmann
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComparatorFactory {

    private static final Logger logger = LogManager.getLogger(ComparatorFactory.class);
    private static final String CONFIG_FILE_URI = "com/rapiddweller/common/comparator/comparators.txt";
    
    private static final Map<Class<?>, Comparator<?>> comparators;

    static {
        comparators = new HashMap<>();
        addComparator(String.class, Collator.getInstance());
        readConfigFileIfExists(CONFIG_FILE_URI);
        
        // this is the fallback if no specific Comparator was found
        addComparator(Comparable.class, new ComparableComparator());
    }

    public static void addComparator(Class comparedClass, Comparator comparator) {
        comparators.put(comparedClass, comparator);
    }

    private static void readConfigFileIfExists(String uri) {
        if (!IOUtil.isURIAvailable(uri)) {
            logger.info("No custom Comparator setup defined, (" + uri + "), using defaults");
            return;
        }
        BufferedReader reader = null;
        try {
            reader = IOUtil.getReaderForURI(uri);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!StringUtil.isEmpty(line))
                    createComparator(line);
            }
        } catch (IOException e) {
            throw new ConfigurationError(e);
        } finally {
            IOUtil.close(reader);
        }
    }

    private static <T> Comparator<T> createComparator(String className) {
        Class<Comparator<T>> cls = BeanUtil.forName(className);
        Comparator<T> comparator = BeanUtil.newInstance(cls);
        Type[] genTypes = BeanUtil.getGenericInterfaceParams(cls, Comparator.class);
        addComparator((Class<T>) genTypes[0], comparator);
        return comparator;
    }
    
    public static <T> Comparator<T> getComparator(Class<T> type) {
        Comparator<T> comparator = (Comparator<T>) comparators.get(type);
        if (comparator == null && Comparable.class.isAssignableFrom(type))
            comparator = new ComparableComparator();
        if (comparator == null)
            throw new RuntimeException("No Comparator defined for " + type.getName());
        return new NullSafeComparator<>(comparator);
    }
}
