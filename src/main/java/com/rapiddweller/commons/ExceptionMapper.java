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
package com.rapiddweller.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.beans.IntrospectionException;

/**
 * Wraps checked exceptions as unchecked exceptions of type ConfigurationError.
 * Created: 02.07.2006 07:44:23
 * @author Volker Bergmann
 */
public final class ExceptionMapper {

    /**
     * Maps method reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param constructor the constructor that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Constructor<?> constructor) {
        String message;
        if (cause instanceof IllegalAccessException)
            message = "No access to constructor: " + constructor;
        else if (cause instanceof InvocationTargetException)
            message = "Internal exception in constructor: " + constructor;
        else if (cause instanceof InstantiationException)
            message = "Error in instantiation by constructor: " + constructor;
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps method reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param method the method that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Method method) {
        String message;
        if (cause instanceof IllegalAccessException)
            message = "No access to method: " + method;
        else if (cause instanceof InvocationTargetException)
            message = "Internal exception in method: " + method;
        else if (cause instanceof IntrospectionException)
            message = "Internal exception in method: " + method;
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps class reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param type the class that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Class<?> type) {
        String message;
        if (cause instanceof IntrospectionException)
            message = "Introspection failed for class " + type;
        else if (cause instanceof InstantiationException)
            message = "Instantiation failed for class '" + type + "' Possibly it is abstract, the constructor not public, or no appropriate constructor is provided.";
        else if (cause instanceof IllegalAccessException)
            message = "Constructor not accessible for class " + type;
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps attribute reflection related exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param field the field that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, Field field) {
        String message;
        if (cause instanceof IllegalAccessException)
            message = "No access to field: " + field;
        else if (cause instanceof NoSuchFieldException)
            message = "No such field found: " + field; 
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }

    /**
     * Maps exceptions to ConfigurationExceptions
     * @param cause the exception that occurred
     * @param name a characteristic identifier that was involved in the cause
     * @return a ConfigurationError that maps the cause.
     */
    public static ConfigurationError configurationException(Exception cause, String name) {
        String message;
        if (cause instanceof ClassNotFoundException)
            message = "Class not found: '" + name + "'";
        else if (cause instanceof NoSuchFieldException)
            message = "Field not found: '" + name + "'";
        else if (cause instanceof NoSuchMethodException)
            message = "Method not found: '" + name + "'";
        else
            message = cause.getMessage();
        return new ConfigurationError(message, cause);
    }
}
