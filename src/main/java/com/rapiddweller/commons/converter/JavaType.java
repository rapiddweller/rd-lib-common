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
package com.rapiddweller.commons.converter;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Utility class for operations related to Java types, e.g. mapping number types and their wrappers.
 * Created: 29.09.2006 12:29:48
 */
public class JavaType {

    // static maps for JavaType lookup by different criteria ---------------------------------------------------------

    /** Maps the NumberTypes by name */
    private static final Map<String,JavaType> instancesByName = new HashMap<>();

    /** Maps the NumberTypes by primitive class */
    private static final Map<Class<?>,JavaType> instancesByPrimitive = new HashMap<>();

    /** Maps the NumberTypes by wrapper class */
    private static final Map<Class<?>,JavaType> instancesByWrapper = new HashMap<>();

    /** Collects all Number types, primitives and wrappers */
    private static final Set<Class<? extends Number>> numberTypes = new HashSet<>();

    // instances -------------------------------------------------------------------------------------------------------

    public static final JavaType BOOLEAN = new JavaType("boolean", boolean.class, Boolean.class);
    public static final JavaType CHAR = new JavaType("char", short.class, Short.class);
    public static final JavaType BYTE = new JavaType("byte", byte.class, Byte.class);
    public static final JavaType SHORT = new JavaType("short", short.class, Short.class);
    public static final JavaType INT  = new JavaType("int", int.class, Integer.class);
    public static final JavaType LONG = new JavaType("long", long.class, Long.class);
    public static final JavaType FLOAT = new JavaType("float", float.class, Float.class);
    public static final JavaType DOUBLE = new JavaType("double", double.class, Double.class);
    public static final JavaType BIG_INT = new JavaType("big_int", BigInteger.class, BigInteger.class);
    public static final JavaType BIG_DECIMAL = new JavaType("big_decimal", BigDecimal.class, BigDecimal.class);

    // attributes ------------------------------------------------------------------------------------------------------

    /** logic name */
    private final String name;

    /** primitive class of the JavaType */
    private final Class<?> primitiveClass;

    /** wrapper class of the JavaType */
    private final Class<?> wrapperClass;

    // private constructor ---------------------------------------------------------------------------------------------

    /** Initializes a JavaType instance and puts it into all lookup maps 
     * @param name 
     * @param primitiveClass 
     * @param objectClass */
    @SuppressWarnings("unchecked")
    private JavaType(String name, Class<?> primitiveClass, Class<?> objectClass) {
        this.name = name;
        this.primitiveClass = primitiveClass;
        this.wrapperClass = objectClass;
        instancesByName.put(name, this);
        instancesByPrimitive.put(primitiveClass, this);
        instancesByWrapper.put(objectClass, this);
        if (Number.class.isAssignableFrom(wrapperClass))
            numberTypes.add((Class<? extends Number>) wrapperClass);
        if (Number.class.isAssignableFrom(primitiveClass))
            numberTypes.add((Class<? extends Number>) primitiveClass);
    }

    // property getters ------------------------------------------------------------------------------------------------

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the primitive class */
    public Class<?> getPrimitiveClass() {
        return primitiveClass;
    }

    /** @return the wrapper class */
    public Class<?> getWrapperClass() {
        return wrapperClass;
    }

    // static query methods --------------------------------------------------------------------------------------------

    public static Collection<JavaType> getInstances() {
        return instancesByName.values();
    }

    /** Returns a class instance by name.
     * @param name the class name
     * @return the corresponding class */
    public static JavaType getInstance(String name) {
        return instancesByName.get(name);
    }

    /** finds the wrapper class for primitive number types 
     * @param numberType the number type
     * @return the corresponding class */
    public static Class<?> getWrapperClass(Class<?> numberType) {
        JavaType resultType = instancesByPrimitive.get(numberType);
        if (resultType == null)
            resultType = instancesByWrapper.get(numberType);
        return (resultType != null ? resultType.getWrapperClass() : null);
    }

    /** Finds the primitive class for primitive number types. 
     * @param numberType the number type
     * @return the corresponding class */
    public static Class<?> getPrimitiveClass(Class<?> numberType) {
        JavaType resultType = instancesByWrapper.get(numberType);
        if (resultType == null)
            resultType = instancesByPrimitive.get(numberType);
        return (resultType != null ? resultType.getPrimitiveClass() : null);
    }

    /** Provides all Java number types. 
     * @return the number types */
    public static Set<Class<? extends Number>> getNumberTypes() {
        return numberTypes;
    }
    
    public static boolean isIntegralType(Class<?> type) {
    	return (type == Integer.class  || type == int.class 
    			|| type == Long.class  || type == long.class 
    			|| type == Byte.class  || type == byte.class 
    			|| type == Short.class || type == short.class 
    			|| type == BigInteger.class);
    }

    public static boolean isDecimalType(Class<?> type) {
    	return (type == Double.class || type == double.class || type == Float.class 
    			|| type == float.class || type == BigDecimal.class);
    }

}
