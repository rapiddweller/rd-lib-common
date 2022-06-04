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

package com.rapiddweller.common;

import com.rapiddweller.common.converter.AnyConverter;
import com.rapiddweller.common.converter.ConverterManager;
import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Bundles reflection and introspection related operations.
 * Created: 01.07.2006 08:44:33
 * @author Volker Bergmann
 * @since 0.1
 */
public final class BeanUtil {

  private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

  // (static) attributes ---------------------------------------------------------------------------------------------

  private static final HashSet<String> NON_CLASS_NAMES = new HashSet<>(100);

  private static final Escalator escalator = new LoggerEscalator();

  public static final String CLASS = "class";

  private static final Class<?>[] NO_CLASSES = new Class<?>[0];

  private static final Map<String, PropertyDescriptor> propertyDescriptors = new HashMap<>();

  /** List of simple Java types. */
  private static final Class<?>[] simpleTypes = {
      String.class,
      long.class, Long.class,
      int.class, Integer.class,
      short.class, Short.class,
      byte.class, Byte.class,
      boolean.class, Boolean.class,
      char.class, Character.class,
      float.class, Float.class,
      double.class, Double.class,
      BigDecimal.class, BigInteger.class
  };

  /** List of integral Java types. */
  private static final Class<?>[] integralNumberTypes = {
      long.class, Long.class,
      int.class, Integer.class,
      short.class, Short.class,
      byte.class, Byte.class,
      BigInteger.class
  };

  /** List of decimal Java types. */
  private static final Class<?>[] decimalNumberTypes = {
      float.class, Float.class,
      double.class, Double.class,
      BigDecimal.class
  };

  /** Mappings from simple Java number types to their Object counterparts */
  private static final PrimitiveTypeMapping[] primitiveNumberTypes = {
      new PrimitiveTypeMapping(long.class, Long.class),
      new PrimitiveTypeMapping(int.class, Integer.class),
      new PrimitiveTypeMapping(short.class, Short.class),
      new PrimitiveTypeMapping(byte.class, Byte.class),
      new PrimitiveTypeMapping(float.class, Float.class),
      new PrimitiveTypeMapping(double.class, Double.class)
  };

  /** Mappings from simple Java non-number types to their Object counterparts */
  private static final PrimitiveTypeMapping[] primitiveNonNumberTypes = {
      new PrimitiveTypeMapping(boolean.class, Boolean.class),
      new PrimitiveTypeMapping(char.class, Character.class),
  };

  /** Map the names of integral Java number types to their classes */
  private static final Map<String, Class<?>> integralNumberTypeMap;

  /** Map the names of decimal Java number types to their classes */
  private static final Map<String, Class<?>> decimalNumberTypeMap;

  /** Map the names of simple Java number types to their classes */
  private static final Map<String, Class<?>> simpleTypeMap;

  /** Map the names of primitive Java types to their classes */
  private static final Map<String, Class<?>> primitiveTypeMap;

  /** Map the names of primitive Java number types to their classes */
  private static final Map<String, Class<?>> primitiveNumberTypeMap;

  private static final Set<Class<?>> IMMUTABLES = CollectionUtil.toSet(
      Boolean.class,
      Short.class, Integer.class, Long.class, BigInteger.class,
      Float.class, Double.class, BigDecimal.class,
      Byte.class, Character.class,
      String.class, java.util.Date.class, java.sql.Date.class, java.sql.Timestamp.class,
      LocalDate.class, LocalDateTime.class, ZonedDateTime.class
  );


  // initialization --------------------------------------------------------------------------------------------------

  static {
    simpleTypeMap = nameToClassMap(simpleTypes);
    integralNumberTypeMap = nameToClassMap(integralNumberTypes);
    decimalNumberTypeMap = nameToClassMap(decimalNumberTypes);
    primitiveNumberTypeMap = new HashMap<>();
    primitiveTypeMap = new HashMap<>();
    for (PrimitiveTypeMapping mapping : primitiveNumberTypes) {
      primitiveNumberTypeMap.put(mapping.primitiveType.getName(), mapping.wrapperType);
      primitiveTypeMap.put(mapping.primitiveType.getName(), mapping.wrapperType);
    }
    for (PrimitiveTypeMapping mapping : primitiveNonNumberTypes) {
      primitiveTypeMap.put(mapping.primitiveType.getName(), mapping.wrapperType);
    }
  }

  private static Map<String, Class<?>> nameToClassMap(Class<?>[] array) {
    Map<String, Class<?>> result = new HashMap<>();
    for (Class<?> type : array) {
      result.put(type.getName(), type);
    }
    return result;
  }

  /** Prevents instantiation of a BeanUtil object. */
  private BeanUtil() {
    // Prevents instantiation of a BeanUtil object
  }

  // type info methods -----------------------------------------------------------------------------------------------

  /** Finds a common supertype for all elements in the collection. */
  public static Class<?> commonSuperType(Collection<?> objects) {
    Iterator<?> iterator = objects.iterator();
    if (!iterator.hasNext()) {
      return null;
    }
    Class<?> result = null;
    while (iterator.hasNext()) {
      Object candidate = iterator.next();
      if (candidate != null) {
        Class<?> candidateClass = candidate.getClass();
        if (result == null || (candidateClass != result && candidateClass.isAssignableFrom(result))) {
          result = candidateClass;
        }
      }
    }
    return result;
  }

  /** Finds a common sub type for all the elements in the collection. */
  public static Class<?> commonSubType(Collection<?> objects) {
    Iterator<?> iterator = objects.iterator();
    if (!iterator.hasNext()) {
      return null;
    }
    Class<?> result = null;
    while (iterator.hasNext()) {
      Object candidate = iterator.next();
      if (candidate != null) {
        Class<?> candidateClass = candidate.getClass();
        if (result == null || (candidateClass != result && result.isAssignableFrom(candidateClass))) {
          result = candidateClass;
        }
      }
    }
    return result;
  }

  /** Tells if the provided class name is the name of a simple Java type
   *  @param className the name to check
   *  @return true if it is a simple type, else false */
  public static boolean isSimpleType(String className) {
    return simpleTypeMap.containsKey(className);
  }

  public static boolean isPrimitiveType(String className) {
    return primitiveTypeMap.containsKey(className);
  }

  public static boolean isPrimitiveNumberType(String className) {
    return primitiveNumberTypeMap.containsKey(className);
  }

  public static boolean isNumberType(Class<?> type) {
    return (isIntegralNumberType(type) || isDecimalNumberType(type));
  }

  public static boolean isIntegralNumberType(Class<?> type) {
    return isIntegralNumberType(type.getName());
  }

  public static boolean isIntegralNumberType(String className) {
    return integralNumberTypeMap.containsKey(className);
  }

  public static boolean isDecimalNumberType(Class<?> type) {
    return isDecimalNumberType(type.getName());
  }

  public static boolean isDecimalNumberType(String className) {
    return decimalNumberTypeMap.containsKey(className);
  }

  public static Class<?> getWrapper(String primitiveClassName) {
    return primitiveTypeMap.get(primitiveClassName);
  }

  /** Tells if the specified class is a collection type.
   *  @param type the class to check
   *  @return true if the class is a collection type, false otherwise */
  public static boolean isCollectionType(Class<?> type) {
    return Collection.class.isAssignableFrom(type);
  }

  public static Class<?>[] getTypes(Object... objects) {
    Class<?>[] result = null;
    if (objects != null) {
      result = new Class[objects.length];
      for (int i = 0; i < objects.length; i++) {
        result[i] = (objects[i] != null ? objects[i].getClass() : null);
      }
    }
    return result;
  }

  // field operations ------------------------------------------------------------------------------------------------

  /** Returns an object's attribute value
   *  @param target        the object to query
   *  @param attributeName the name of the attribute
   *  @return the attribute value */
  public static Object getAttributeValue(Object target, String attributeName) {
    Assert.notNull(target, "target");
    Field field = getField(target.getClass(), attributeName);
    try {
      return field.get(target);
    } catch (IllegalAccessException e) {
      throw ExceptionFactory.getInstance().illegalArgument(
          "Failed to get attribute value " + attributeName + " of " + target, e);
    }
  }

  /** Sets an attribute value of an object.
   *  @param obj       the object to modify
   *  @param fieldName the name of the attribute to set
   *  @param value     the value to assign to the field */
  public static void setAttributeValue(Object obj, String fieldName, Object value) {
    Field field = getField(obj.getClass(), fieldName);
    setAttributeValue(obj, field, value);
  }

  /** Returns a class' static attribute value
   *  @param objectType    the class to query
   *  @param attributeName the name of the attribute
   *  @return the attribute value */
  public static Object getStaticAttributeValue(Class<?> objectType, String attributeName) {
    Field field = getField(objectType, attributeName);
    try {
      return field.get(null);
    } catch (IllegalAccessException e) {
      throw ExceptionFactory.getInstance().illegalArgument(
          "Failed to get static attribute value " + attributeName + " of " + objectType, e);
    }
  }

  /** Sets a static attribute value of a class.
   *  @param objectType the class to modify
   *  @param fieldName  the name of the attribute to set
   *  @param value      the value to assign to the field */
  public static void setStaticAttributeValue(Class<?> objectType, String fieldName, Object value) {
    Field field = getField(objectType, fieldName);
    setAttributeValue(null, field, value);
  }

  /** Sets an attribute value of an object.
   *  @param obj   the object to modify
   *  @param field the attribute to set
   *  @param value the value to assign to the field */
  public static void setAttributeValue(Object obj, Field field, Object value) {
    try {
      field.set(obj, value);
    } catch (IllegalAccessException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Failed to set value for " + field, e);
    }
  }

  /** Returns the generic type information of an attribute.
   *  @param field the field representation of the attribute.
   *  @return an array of types that are used to parameterize the attribute. */
  public static Type[] getGenericTypes(Field field) {
    Type genericFieldType = field.getGenericType();
    if (!(genericFieldType instanceof ParameterizedType)) {
      return NO_CLASSES; // type is not generic
    }
    ParameterizedType pType = (ParameterizedType) genericFieldType;
    Type[] args = pType.getActualTypeArguments();
    Type[] types = new Class[args.length];
    System.arraycopy(args, 0, types, 0, args.length);
    return types;
  }

  // instantiation ---------------------------------------------------------------------------------------------------

  /** Finds a class by its name. */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Class<T> forName(String name) {
    Assert.notNull(name, "class name");
    Class type = simpleTypeMap.get(name);
    if (type != null) {
      return type;
    } else {
      try {
        return (Class<T>) getContextClassLoader().loadClass(name);
      } catch (ClassNotFoundException | NullPointerException e) {
        throw ExceptionFactory.getInstance().illegalArgument(
            "Failed to instantiate class '" + name + "'", e);
      } // this is raised by the Eclipse BundleLoader if it does not find the class

    }
  }

  public static ClassLoader getContextClassLoader() {
    ClassLoader result = Thread.currentThread().getContextClassLoader();
    if (result == null) {
      result = BeanUtil.class.getClassLoader();
    }
    return result;
  }

  public static ClassLoader createJarClassLoader(File jarFile) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (jarFile != null) {
      try {
        classLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()}, classLoader);
      } catch (MalformedURLException e) {
        throw ExceptionFactory.getInstance().internalError("Unexpected error", e);
      }
    }
    return classLoader;
  }

  public static ClassLoader createDirectoryClassLoader(File directory) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try {
      classLoader = new URLClassLoader(new URL[] {directory.toURI().toURL()}, classLoader);
    } catch (MalformedURLException e) {
      throw ExceptionFactory.getInstance().internalError("Unexpected error", e);
    }
    return classLoader;
  }

  public static void runWithJarClassLoader(File jarFile, Runnable action) {
    Thread currentThread = Thread.currentThread();
    ClassLoader contextClassLoader = currentThread.getContextClassLoader();
    try {
      currentThread.setContextClassLoader(createJarClassLoader(jarFile));
      action.run();
    } finally {
      currentThread.setContextClassLoader(contextClassLoader);
    }
  }

  public static <T> T callWithJarClassLoader(File jarFile, Callable<T> action) throws Exception {
    Thread currentThread = Thread.currentThread();
    ClassLoader contextClassLoader = currentThread.getContextClassLoader();
    try {
      currentThread.setContextClassLoader(createJarClassLoader(jarFile));
      return action.call();
    } finally {
      currentThread.setContextClassLoader(contextClassLoader);
    }
  }

  /** Instantiates a class by the default constructor.
   *  @param className the name of the class to instantiate
   *  @return an instance of the class */
  public static Object newInstance(String className) {
    Class<?> type = BeanUtil.forName(className);
    return newInstanceFromDefaultConstructor(type);
  }

  public static <T> T newInstance(Class<T> type) {
    return newInstance(type, false, null);
  }

  /** Creates an object of the specified type.
   *  @param <T>        the class of the object to instantiate
   *  @param type       the class to instantiate
   *  @param parameters the constructor parameters
   *  @return an object of the specified class */
  public static <T> T newInstance(Class<T> type, Object[] parameters) {
    return newInstance(type, false, parameters);
  }

  @SuppressWarnings({"unchecked"})
  public static <T> T newInstance(Class<T> type, boolean autoConvert, Object[] params) {
    if (params == null || params.length == 0) {
      return newInstanceFromDefaultConstructor(type);
    }
    Constructor<T> ctorToUse = null;
    try {
      int paramCount = params.length;
      Constructor<T>[] constructors = (Constructor<T>[]) type.getConstructors();
      List<Constructor<T>> candidates = getConstructorsOfParamCount(paramCount, constructors);
      if (candidates.isEmpty()) {
        throw ExceptionFactory.getInstance().illegalArgument(
            "No constructor with " + paramCount + " parameters found for " + type);
      } else if (candidates.size() == 1) {
        ctorToUse = candidates.get(0);
      } else { // multiple candidates
        ctorToUse = findBestMatchingCall(constructors, params);
      }
      if (ctorToUse != null) {
        return newInstance(ctorToUse, autoConvert, params);
      } else {
        if (!autoConvert) {
          throw ExceptionFactory.getInstance().illegalArgument(
              "No appropriate constructor found: " + type + '(' + ArrayFormat.format(", ", getTypes(params)) + ')');
        }
        return callFirstFunctioningConstructor(candidates, params);
      }
    } catch (SecurityException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Error calling " + ctorToUse, e);
    }
  }

  private static <T> List<Constructor<T>> getConstructorsOfParamCount(int paramCount, Constructor<T>[] constructors) {
    List<Constructor<T>> candidates = new ArrayList<>(constructors.length);
    for (Constructor<T> constructor : constructors) {
      if (constructor.getParameterTypes().length == paramCount) {
        candidates.add(constructor);
      }
    }
    return candidates;
  }

  private static <T> Constructor<T> findBestMatchingCall(Constructor<T>[] candidates, Object[] params) {
    Class<?>[] paramTypes = getTypes(params);
    for (Constructor<T> candidate : candidates) {
      if (paramTypesMatch(paramTypes, candidate.getParameterTypes())) {
        return candidate;
      }
    }
    return null;
  }

  private static <T> T callFirstFunctioningConstructor(List<Constructor<T>> candidates, Object[] parameters) {
    Exception mostRecentException = null;
    for (Constructor<T> candidate : candidates) {
      try {
        return newInstance(candidate, true, parameters);
      } catch (Exception e) {
        mostRecentException = e;
        logger.warn("Exception in constructor call: " + candidate, e);
      }
    }
    // no constructor could be called without exception
    String errMsg = "None of these constructors could be called without exception: " + candidates + ", " +
        "latest exception: " + mostRecentException;
    throw ExceptionFactory.getInstance().internalError(errMsg, null);
  }

  /** Creates a new instance of a class.
   *  @param <T>         the class of the object to instantiate
   *  @param constructor the constructor to invoke
   *  @param params      the parameters to provide to the constructor
   *  @return a new instance of the class */
  public static <T> T newInstance(Constructor<T> constructor, Object... params) {
    return newInstance(constructor, false, params);
  }

  public static <T> T newInstance(Constructor<T> constructor, boolean autoConvert, Object... parameters) {
    if (autoConvert) {
      parameters = convertArray(parameters, constructor.getParameterTypes());
    }
    Class<T> type = constructor.getDeclaringClass();
    if (deprecated(type)) {
      escalator.escalate("Instantiating a deprecated class: " + type.getName(), BeanUtil.class, null);
    }
    try {
      return constructor.newInstance(parameters);
    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Failed to execute " + constructor, e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T clone(T object) {
    Class<?> clazz = object.getClass();
    try {
      Method cloneMethod = clazz.getMethod("clone");
      return (T) cloneMethod.invoke(object);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().cloningFailed("Cloning failed for " + clazz, e);
    }
  }

  public static <T> T[] cloneAll(T[] input) {
    T[] output = ArrayUtil.newInstance(ArrayUtil.componentType(input), input.length);
    for (int i = 0; i < input.length; i++) {
      output[i] = clone(input[i]);
    }
    return output;
  }


  // method operations -----------------------------------------------------------------------------------------------

  /** Finds a method by reflection. This iterates all methods of the class, comparing names and parameter types.
   *  Unlike the method Class.getMethod(String, Class ...), this method is able to match primitive and wrapper types.
   *  If no appropriate method is found, a ConfigurationError is raised.
   *  @param type       the class that holds the method
   *  @param methodName the name of the method
   *  @param paramTypes the parameter types of the method
   *  @return a method with matching names and parameters */
  public static Method getMethod(Class<?> type, String methodName, Class<?>... paramTypes) {
    Method method = findMethod(type, methodName, paramTypes);
    if (method == null) {
      throw ExceptionFactory.getInstance().illegalArgument(
          "method not found in class " + type.getName() + ": " + methodName
          + '(' + ArrayFormat.format(paramTypes) + ')');
    }
    return method;
  }

  /** Finds a method by reflection. This iterates all methods of the class, comparing names and parameter types.
   *  Unlike the method Class.getMethod(String, Class ...), this method is able to match primitive and wrapper types.
   *  If no appropriate method is found, 'null' is returned
   *  @param type       the class that holds the method
   *  @param methodName the name of the method
   *  @param paramTypes the parameter types of the method
   *  @return a method with matching names and parameters */
  public static Method findMethod(Class<?> type, String methodName, Class<?>... paramTypes) {
    Method result = null;
    Method[] methods = type.getMethods();
    for (Method method : methods) {
      if (!methodName.equals(method.getName())) {
        continue;
      }
      Class<?>[] expectedTypes = method.getParameterTypes();
      if (paramTypesMatch(paramTypes, expectedTypes)) {
        if (paramCountMatches(expectedTypes, paramTypes)) {
          return method; // optimal match - return it immediately
        } else {
          result = method; // sub optimal match - store it, but keep on searching for better matches
        }
      }
    }
    return result;
  }

  private static boolean paramCountMatches(Class<?>[] expectedTypes, Class<?>[] paramTypes) {
    return (ArrayUtil.isEmpty(paramTypes) && ArrayUtil.isEmpty(expectedTypes))
        || paramTypes.length == expectedTypes.length;
  }

  public static Method[] findMethodsByName(Class<?> type, String methodName) {
    ArrayBuilder<Method> builder = new ArrayBuilder<>(Method.class);
    for (Method method : type.getMethods()) {
      if (methodName.equals(method.getName())) {
        builder.add(method);
      }
    }
    return builder.toArray();
  }

  @SuppressWarnings("unchecked")
  public static <T> Constructor<T> findConstructor(Class<T> type, Class<?>... paramTypes) {
    Constructor<T>[] ctors = (Constructor<T>[]) type.getConstructors();
    for (Constructor<T> ctor : ctors) {
      if (paramTypesMatch(paramTypes, ctor.getParameterTypes())) {
        return ctor;
      }
    }
    return null;
  }

  /** Invokes a method on an {@link Object}.
   *  @param target     the object on which to invoke the method
   *  @param methodName the name of the method
   *  @param args       the arguments to provide to the method
   *  @return the invoked method's return value. */
  public static Object invoke(Object target, String methodName, Object... args) {
    return invoke(true, target, methodName, args);
  }

  @SuppressWarnings("rawtypes")
  public static Object invoke(boolean strict, Object target, String methodName, Object... args) {
    Assert.notNull(target, "target");
    Class[] argTypes = getTypes(args);
    Method method;
    if (target instanceof Class) {
      method = getMethod((Class) target, methodName, argTypes);
    } else {
      method = getMethod(target.getClass(), methodName, argTypes);
    }
    return invoke(target, method, strict, args);
  }

  public static Object invokeStatic(Class<?> targetClass, String methodName, Object... args) {
    return invokeStatic(targetClass, methodName, true, args);
  }

  public static Object invokeStatic(Class<?> targetClass, String methodName, boolean strict, Object... args) {
    Assert.notNull(targetClass, "targetClass");
    Class<?>[] argClasses = new Class[args.length];
    for (int i = 0; i < args.length; i++) {
      argClasses[i] = (args[i] != null ? args[i].getClass() : null);
    }
    Method method = getMethod(targetClass, methodName, argClasses);
    return invoke(null, method, strict, args);
  }

  /** Invokes a method on an {@link Object}.
   *  @param target the object on which to invoke the mthod
   *  @param method the method to invoke
   *  @param args   the arguments to provide to the method
   *  @return the invoked method's return value. */
  public static Object invoke(Object target, Method method, Object[] args) {
    return invoke(target, method, true, args);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Object invoke(Object target, Method method, boolean strict, Object[] args) {
    try {
      Object[] params;
      Class<?>[] paramTypes = method.getParameterTypes();
      if (paramTypes.length == 0) {
        params = null;
      } else if (args.length == paramTypes.length) { // exact match
        // map one to one
        if (strict) {
          params = args;
        } else {
          params = new Object[paramTypes.length];
          for (int i = 0; i < paramTypes.length; i++) {
            Object arg = args[i];
            if (arg == null) {
              params[i] = null;
            } else {
              Converter converter = ConverterManager.getInstance().createConverter(arg.getClass(), paramTypes[i]);
              params[i] = converter.convert(arg);
            }
          }
        }
      } else if (args.length > paramTypes.length) { // varargs params?
        // map varargs
        params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length - 1; i++) {
          params[i] = (strict ? args[i] : AnyConverter.convert(args[i], paramTypes[i]));
        }
        Class<?> varargsComponentType = paramTypes[paramTypes.length - 1].getComponentType();
        Object varargs = Array.newInstance(varargsComponentType, args.length - paramTypes.length + 1);
        for (int i = 0; i < args.length - paramTypes.length + 1; i++) {
          Object param = args[paramTypes.length - 1 + i];
          if (strict) {
            param = AnyConverter.convert(param, varargsComponentType);
          }
          Array.set(varargs, i, param);
        }
        params[params.length - 1] = varargs;
      } else if (args.length == paramTypes.length - 1) { // varargs of length 0
        params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length - 1; i++) {
          params[i] = (strict ? args[i] : AnyConverter.convert(args[i], paramTypes[i]));
        }
        Class<?> varargsComponentType = paramTypes[paramTypes.length - 1].getComponentType();
        Object varargs = Array.newInstance(varargsComponentType, 0);
        params[params.length - 1] = varargs;
      } else {
        throw ExceptionFactory.getInstance().illegalArgument(
            "Method " + method.getName() + " requires " + paramTypes.length + " params, " +
                "but only " + args.length + " were provided. ");
      }
      return method.invoke(target, params);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionFactory.getInstance().operationFailed("Failed to invoke " + method, e);
    }
  }

  public static boolean paramTypesMatch(Class<?>[] usedTypes, Class<?>[] expectedTypes) {
    // check preconditions
    if (usedTypes == null) {
      usedTypes = new Class<?>[0];
    }
    Assert.notNull(expectedTypes, "expectedTypes");

    // simple plausibility checks by parameter count
    if (expectedTypes.length == 0) {
      return (usedTypes.length == 0);
    } else if (usedTypes.length < expectedTypes.length - 1) { // the last parameter may be an omitted vararg
      return false;
    }

    // check for expected type and possible varargs
    Class<?> lastExpectedType = expectedTypes[expectedTypes.length - 1];
    boolean possibleVararg = lastExpectedType.isArray();

    // Simple case: no vararg
    if (!possibleVararg && usedTypes.length != expectedTypes.length) {
      return false;
    }

    // check the param types before an optional varargs param
    int fixTypeCount = expectedTypes.length - (possibleVararg ? 1 : 0);
    for (int i = 0; i < fixTypeCount; i++) {
      if (!typeMatches(usedTypes[i], expectedTypes[i])) {
        return false;
      }
    }

    // if there is no vararg all lights are green
    if (!possibleVararg) {
      return true;
    }

    // empty vararg parameter
    if (usedTypes.length == expectedTypes.length - 1) {
      return true;
    }

    // check if all used varargs params match the varargs type
    Class<?> componentType = lastExpectedType.getComponentType();
    for (int i = expectedTypes.length - 1; i < usedTypes.length; i++) {
      Class<?> foundType = usedTypes[i];
      if (!typeMatches(foundType, componentType) && !isArrayOfType(componentType, foundType)) {
        return false;
      }
    }
    return true;
  }


  private static boolean isArrayOfType(Class<?> componentType, Class<?> foundType) {
    if (!foundType.isArray()) {
      return false;
    }
    return typeMatches(ArrayUtil.componentType(foundType), ArrayUtil.componentType(componentType));
  }

  // JavaBean operations ---------------------------------------------------------------------------------------------

  /** Returns the bean property descriptor of an attribute
   *  @param beanClass    the class that holds the attribute
   *  @param propertyName the name of the property
   *  @return the attribute's property descriptor */
  public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
    Assert.notNull(beanClass, "beanClass");
    String propertyId = beanClass.getName() + '#' + propertyName;
    PropertyDescriptor result = propertyDescriptors.get(propertyId);
    if (result != null) {
      return result;
    }
    // descriptor is new

    int separatorIndex = propertyName.indexOf('.');
    if (separatorIndex >= 0) {
      String localProperty = propertyName.substring(0, separatorIndex);
      String remoteProperty = propertyName.substring(separatorIndex + 1);
      PropertyDescriptor localPropertyDescriptor = getPropertyDescriptor(beanClass, localProperty);
      if (localPropertyDescriptor == null) {
        return null;
      }
      Class<?> localPropertyType = localPropertyDescriptor.getPropertyType();
      result = getPropertyDescriptor(localPropertyType, remoteProperty);
    } else {
      try {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
          String name = descriptor.getName();
          if (name.equals(propertyName)) {
            result = descriptor;
            break;
          }
        }
      } catch (IntrospectionException e) {
        throw ExceptionFactory.getInstance().illegalArgument(
            "Failed to get property descriptor " + propertyName, e);
      }
    }
    propertyDescriptors.put(propertyId, result);
    return result;
  }

  public static PropertyDescriptor getPropertyDescriptor(
      Class<?> type, String propertyName, boolean required) {
    PropertyDescriptor descriptor = getPropertyDescriptor(type, propertyName);
    if (required && descriptor == null) {
      throw ExceptionFactory.getInstance().internalError(
          type.getName() + " does not have a property " + propertyName, null);
    }
    return descriptor;
  }

  public static boolean hasProperty(Class<?> beanClass, String propertyName) {
    return (getPropertyDescriptor(beanClass, propertyName) != null);
  }

  public static boolean hasWriteableProperty(Class<?> beanClass, String propertyName) {
    PropertyDescriptor descriptor = getPropertyDescriptor(beanClass, propertyName);
    return (descriptor != null && descriptor.getWriteMethod() != null);
  }

  /** returns the name of a property read method.
   *  @param propertyName the name of the property
   *  @param propertyType the type of the property
   *  @return the name of the property read method */
  public static String readMethodName(String propertyName, Class<?> propertyType) {
    if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
      return "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    } else {
      return "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }
  }

  /** Returns the name of a property write method.
   *  @param propertyName the name of the property
   *  @return the name of the property write method */
  public static String writeMethodName(String propertyName) {
    return "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
  }

  /** Finds all property descriptors of a bean class
   *  @param type the class to check
   *  @return all found property descriptors */
  public static PropertyDescriptor[] getPropertyDescriptors(Class<?> type) {
    try {
      return Introspector.getBeanInfo(type).getPropertyDescriptors();
    } catch (IntrospectionException e) {
      throw ExceptionFactory.getInstance().operationFailed("Introspection failed on " + type, e);
    }
  }

  /** Copies a Map's values to the properties of a JavaBean,
   *  using the Map entries' key values as bean property names.
   *  @param sourceBean the bean from which to read the properties
   *  @param targetBean the bean on which to set the properties */
  public static void copyPropertyValues(Object sourceBean, Object targetBean) {
    PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(sourceBean.getClass());
    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
      String name = propertyDescriptor.getName();
      if (propertyDescriptor.getReadMethod() != null) {
        Object value = getPropertyValue(sourceBean, propertyDescriptor);
        setPropertyValue(targetBean, name, value, false, true);
      }
    }
  }

  public static void setPropertyValues(Object bean, Map<String, ?> properties) {
    Class<?> beanClass = bean.getClass();
    Method writeMethod = null;
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        String name = propertyDescriptor.getName();
        Object value = properties.get(name);
        if (value != null) {
          writeMethod = propertyDescriptor.getWriteMethod();
          Object targetTypeObject = AnyConverter.convert(value, propertyDescriptor.getPropertyType());
          writeMethod.invoke(bean, targetTypeObject);
        }
      }
    } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Failed to set property value using " + writeMethod, e);
    }
  }

  public static Map<String, Object> getPropertyValues(Object bean, boolean includeClass) {
    Map<String, Object> result = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (includeClass || !CLASS.equals(propertyName)) {
        result.put(propertyName, getPropertyValue(bean, descriptor));
      }
    }
    return result;
  }

  public static Map<String, Object> getReadablePropertyValues(Object bean, boolean includeClass) {
    Map<String, Object> result = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (descriptor.getReadMethod() != null && (includeClass || !CLASS.equals(propertyName))) {
        result.put(propertyName, getPropertyValue(bean, descriptor));
      }
    }
    return result;
  }

  public static Map<String, Object> getRWPropertyValues(Object bean, boolean includeClass) {
    Map<String, Object> result = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null
          && (includeClass || !CLASS.equals(propertyName))) {
        result.put(propertyName, getPropertyValue(bean, descriptor));
      }
    }
    return result;
  }

  /** Queries a property value on a JavaBean instance
   *  @param bean         the bean to read
   *  @param propertyName the name of the property to read
   *  @return the property value */
  public static Object getPropertyValue(Object bean, String propertyName) {
    return getPropertyValue(bean, propertyName, true);
  }

  public static Object getPropertyValue(Object bean, String propertyName, boolean propertyRequired) {
    PropertyDescriptor descriptor = getPropertyDescriptor(bean.getClass(), propertyName);
    if (descriptor == null) {
      if (propertyRequired) {
        throw ExceptionFactory.getInstance().internalError(
            "Property '" + propertyName + "' not found in class " + bean.getClass(), null);
      } else {
        return null;
      }
    }
    return getPropertyValue(bean, descriptor);
  }

  private static Object getPropertyValue(Object bean, PropertyDescriptor descriptor) {
    Method readMethod;
    try {
      readMethod = descriptor.getReadMethod();
      return readMethod.invoke(bean);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionFactory.getInstance().illegalArgument(
          "Failed to get property value " + descriptor.getName(), e);
    }
  }

  /** Sets a property value on a JavaBean object.
   *  @param bean          the bean on which to set the property
   *  @param propertyName  the name of the property to set
   *  @param propertyValue the value to set the property to */
  public static void setPropertyValue(Object bean, String propertyName, Object propertyValue) {
    setPropertyValue(bean, propertyName, propertyValue, true);
  }

  public static void setPropertyValue(Object bean, String propertyName, Object propertyValue, boolean strict) {
    setPropertyValue(bean, propertyName, propertyValue, strict, !strict);
  }

  public static void setPropertyValue(Object bean, String propertyName, Object propertyValue, boolean required, boolean autoConvert) {
    Method writeMethod;
    try {
      Class<?> beanClass = bean.getClass();
      PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanClass, propertyName);
      if (propertyDescriptor == null) {
        if (required) {
          throw ExceptionFactory.getInstance().internalError(
              beanClass + " does not have a property '" + propertyName + "'", null);
        } else {
          return;
        }
      }
      writeMethod = propertyDescriptor.getWriteMethod();
      if (writeMethod != null) {
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        if (propertyValue != null) {
          Class<?> argType = propertyValue.getClass();
          if (!propertyType.isAssignableFrom(argType) && !isWrapperTypeOf(propertyType, propertyValue) && !autoConvert) {
            throw ExceptionFactory.getInstance().illegalArgument(
                "ArgumentType mismatch: expected " + propertyType.getName() + ", found "
                    + propertyValue.getClass().getName());
          } else {
            propertyValue = AnyConverter.convert(propertyValue, propertyType);
          }
        }
        writeMethod.invoke(bean, propertyValue);
      } else if (required) {
        throw ExceptionFactory.getInstance().illegalArgument(
            "Cannot write read-only property '" + propertyDescriptor.getName() + "' of " + beanClass);
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Failed to set value of property " + propertyName, e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <B, P> List<P> extractProperties(Collection<B> beans, String propertyName) {
    List<P> result = new ArrayList<>(beans.size());
    for (B bean : beans) {
      result.add((P) getPropertyValue(bean, propertyName));
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <B, P> P[] extractProperties(B[] beans, String propertyName, Class<P> propertyType) {
    P[] result = ArrayUtil.newInstance(propertyType, beans.length);
    for (int i = 0; i < beans.length; i++) {
      B bean = beans[i];
      result[i] = (P) getPropertyValue(bean, propertyName);
    }
    return result;
  }

  // class operations ------------------------------------------------------------------------------------------------

  /**
   * Prints information about a class' parents and methods to a PrintWriter
   *
   * @param object  the object to examine
   * @param printer the {@link PrintWriter} used to write the text representation
   */
  public static void printClassInfo(Object object, PrintWriter printer) {
    if (object == null) {
      printer.println("null");
      return;
    }
    Class<?> type = object.getClass();
    printer.println(type);
    if (type.getSuperclass() != null) {
      printer.println("extends " + type.getSuperclass());
    }
    for (Class<?> interf : type.getInterfaces()) {
      printer.println("implements " + interf);
    }
    for (Method method : type.getMethods()) {
      printer.println(method);
    }
  }

  /** Checks if a class fulfills the JavaBeans contract.
   *  @param cls the class to check */
  public static void checkJavaBean(Class<?> cls) {
    try {
      Constructor<?> constructor = cls.getDeclaredConstructor();
      int classModifiers = cls.getModifiers();
      if (Modifier.isInterface(classModifiers)) {
        throw ExceptionFactory.getInstance().illegalArgument(cls.getName() + " is an interface");
      }
      if (Modifier.isAbstract(classModifiers)) {
        throw ExceptionFactory.getInstance().illegalArgument(cls.getName() + " cannot be instantiated - it is an abstract class");
      }
      int modifiers = constructor.getModifiers();
      if (!Modifier.isPublic(modifiers)) {
        throw ExceptionFactory.getInstance().illegalArgument("No public default constructor in " + cls);
      }
    } catch (NoSuchMethodException e) {
      throw ExceptionFactory.getInstance().illegalArgument("No default constructor in class " + cls);
    } catch (SecurityException e) {
      logger.error("I am not allowed to check the class by using reflection, " +
          "so I just can hope the class is alright and go on: ", e);
    }
  }

  /** Tells if a class is deprecated.
   *  @param type the class to check for deprecation
   *  @return true if the class is deprecated, else false
   *  @since 0.2.05 */
  public static boolean deprecated(Class<?> type) {
    Annotation[] annotations = type.getDeclaredAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation instanceof Deprecated) {
        return true;
      }
    }
    return false;
  }

  public static String getClassName(Object product) {
    return (product != null ? product.getClass().getName() : null);
  }

  public static List<Class<?>> getClasses(String packageName) {
    ClassLoader classLoader = getContextClassLoader();
    String packagePath = packageName.replace('.', '/');
    Enumeration<URL> resourceUris;
    try {
      resourceUris = classLoader.getResources(packagePath);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError("Error getting class loader resources", e);
    }
    List<Class<?>> classes = new ArrayList<>();
    while (resourceUris.hasMoreElements()) {
      URL resource = resourceUris.nextElement();
      String protocol = resource.getProtocol();
      if ("jar".equals(protocol)) {
        findClassesInJar(resource.getFile(), packagePath, classes);
      } else if ("file".equals(protocol)) {
        findClassesInDirectory(resource, packageName, classes);
      } else {
        throw ExceptionFactory.getInstance().programmerUnsupported(
            "Not a supported protocol for classloader resources: " + protocol);
      }
    }
    return classes;
  }

  public static Object getFieldValue(Object target, String name, boolean strict) {
    Class<?> type = target.getClass();
    try {
      Field field = type.getField(name);
      return getFieldValue(field, target);
    } catch (NoSuchFieldException e) {
      if (strict) {
        throw ExceptionFactory.getInstance().illegalArgument("Field not found: " + type.getName() + '.' + name, e);
      } else {
        escalator.escalate("Class '" + type + "' does not have a field '" + name + "'", type, name);
        return null;
      }
    }
  }

  public static Object getFieldValue(Field field, Object target) {
    try {
      if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
        return field.get(null);
      } else {
        return field.get(target);
      }
    } catch (IllegalArgumentException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Error getting field value", e);
    } catch (IllegalAccessException e) {
      throw ExceptionFactory.getInstance().accessFailed("Error accessing " + field, e);
    }
  }

  /** Returns a Field object that represents an attribute of a class
   *  @param type the class that holds the attribute
   *  @param name the name of the attribute
   *  @return a Field object that represents the attribute */
  public static Field getField(Class<?> type, String name) {
    return getField(type, name, true);
  }

  public static Field getField(Class<?> type, String name, boolean required) {
    try {
      return type.getField(name);
    } catch (NoSuchFieldException e) {
      if (required) {
        throw ExceptionFactory.getInstance().illegalArgument("Field not found: " + type.getName() + '.' + name, e);
      } else {
        return null;
      }
    }
  }

  public static void setFieldValue(Field field, Object target, Object value, boolean autoConvert) {
    try {
      if (value != null) {
        Class<?> targetType = field.getType();
        if (!targetType.isAssignableFrom(value.getClass())) {
          if (autoConvert) {
            value = AnyConverter.convert(value, targetType);
          } else {
            throw ExceptionFactory.getInstance().illegalArgument("expected value of " + targetType +
                " or child class, found incompatible " + value.getClass());
          }
        }
      }
      if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
        field.set(null, value);
      } else {
        field.set(target, value);
      }
    } catch (IllegalArgumentException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Error setting field value", e);
    } catch (IllegalAccessException e) {
      throw ExceptionFactory.getInstance().accessFailed("Error accessing " + field, e);
    }
  }

  public static Method[] findMethodsByAnnotation(
      Class<?> owner, Class<? extends Annotation> annotationClass) {
    Method[] methods = owner.getMethods();
    ArrayBuilder<Method> builder = new ArrayBuilder<>(Method.class);
    for (Method method : methods) {
      if (method.getAnnotation(annotationClass) != null) {
        builder.add(method);
      }
    }
    return builder.toArray();
  }

  public static <C, I> Type[] getGenericInterfaceParams(Class<C> checkedClass, Class<I> searchedInterface) {
    for (Type type : checkedClass.getGenericInterfaces()) {
      ParameterizedType pt = (ParameterizedType) type;
      if (searchedInterface.equals(pt.getRawType())) {
        return pt.getActualTypeArguments();
      }
    }
    if (!Object.class.equals(checkedClass.getSuperclass())) {
      return getGenericInterfaceParams(checkedClass.getSuperclass(), searchedInterface);
    }
    throw ExceptionFactory.getInstance().illegalArgument(
        checkedClass + " does not implement interface with generic parameters: " + searchedInterface, null);
  }

  public static String className(Object o) {
    if (o == null) {
      return null;
    }
    return (o instanceof Class ? ((Class<?>) o).getName() : o.getClass().getName());
  }

  public static String simpleClassName(Object o) {
    if (o == null) {
      return null;
    }
    return (o instanceof Class ? ((Class<?>) o).getName() : o.getClass().getSimpleName());
  }


  // General method support, i.e. for toString(), equals(), hashCode() -----------------------------------------------

  /**
   * Tries to convert both arguments to the same type and then compares them
   *
   * @param o1 the first object to compare
   * @param o2 the second object to compare
   * @return true if they are equal, otherwise false
   */
  public static boolean equalsIgnoreType(Object o1, Object o2) {
    if (NullSafeComparator.equals(o1, o2)) {
      return true;
    }
    if (o1 == null || o2 == null) {
      return false;
    }
    if (o1.getClass() == o2.getClass()) {
      return false;
    }
    // OK, both are not null, but they have a different type
    if (o1 instanceof String && o2 instanceof Number) {
      Object tmp = o1;
      o1 = o2;
      o2 = tmp;
    }
    if (o1 instanceof Number) {
      if (o2 instanceof String) {
        o2 = AnyConverter.convert(o2, o1.getClass());
      }
      return (((Number) o1).doubleValue() == ((Number) o2).doubleValue());
    }
    return false;
  }

  public static int nullSafeHashCode(Object object) {
    return (object != null ? object.hashCode() : 0);
  }

  public static boolean existsClass(String className) {
    try {
      if (NON_CLASS_NAMES.contains(className)) {
        return false;
      }
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException e) {
      NON_CLASS_NAMES.add(className);
      return false;
    }
  }

  public static String toString(Object bean) {
    return toString(bean, false);
  }

  public static String toString(Object bean, boolean simple) {
    if (bean == null) {
      return null;
    }
    Class<?> beanClass = bean.getClass();
    StringBuilder builder = new StringBuilder(simple ? beanClass.getSimpleName() : bean.getClass().getName());
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    boolean first = true;
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (!CLASS.equals(propertyName) && descriptor.getReadMethod() != null) {
        if (first) {
          builder.append('[');
        } else {
          builder.append(", ");
        }

        Object value = getPropertyValue(bean, propertyName);
        String valueString = ToStringConverter.convert(value, "null");
        builder.append(propertyName).append("=").append(valueString);
        first = false;
      }
    }
    if (!first) {
      builder.append(']');
    }
    return builder.toString();
  }

  public static String uncapitalizeProperty(String text) {
    int i = 0;
    while (i < text.length() && Character.isUpperCase(text.charAt(i))
        && (i == 0 || i + 1 == text.length() || Character.isUpperCase(text.charAt(i + 1)))) {
      i++;
    }
    return text.substring(0, i).toLowerCase() + text.substring(i);
  }

  public static String packageFolder(Class clazz) {
    return clazz.getPackageName().replace('.', SystemInfo.getFileSeparator());
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static boolean typeMatches(Class<?> foundType, Class<?> expectedType) {
    if (foundType == null) {
      return true;
    }
    if (expectedType.isAssignableFrom(foundType)) {
      return true;
    }
    if (isPrimitiveType(expectedType.getName()) &&
        foundType.equals(getWrapper(expectedType.getName()))) {
      return true;
    }
    if (isPrimitiveType(foundType.getName()) &&
        expectedType.equals(getWrapper(foundType.getName()))) {
      return true;
    }
    return isNumberType(foundType) && isNumberType(expectedType);
  }

  /** Creates an instance of the class using the default constructor.
   *  @param type the type to instantiate
   *  @return a new instance of the type
   *  @since 0.2.06 */
  @SuppressWarnings("cast")
  private static <T> T newInstanceFromDefaultConstructor(Class<T> type) {
    if (type == null) {
      return null;
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Instantiating {}", type.getSimpleName());
    }
    if (deprecated(type)) {
      escalator.escalate("Instantiating a deprecated class: " + type.getName(), BeanUtil.class, null);
    }
    try {
      return type.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw ExceptionFactory.getInstance().illegalArgument("Failed to instantiate " + type, e);
    }
  }

  private static void findClassesInDirectory(URL url, String packagePath, List<Class<?>> classes) {
    try {
      findClassesInDirectory(new File(url.toURI()), packagePath, classes);
    } catch (URISyntaxException e) {
      throw ExceptionFactory.getInstance().internalError("Error calculating URI", e);
    }
  }

  private static void findClassesInDirectory(File directory, String packagePath, List<Class<?>> classes) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        String fileName = file.getName();
        if (file.isDirectory()) {
          findClassesInDirectory(file, packagePath + "." + fileName, classes);
        } else if (fileName.endsWith(".class") && !fileName.contains("$")) {
          String className = packagePath + '.' + fileName.substring(0, fileName.length() - 6);
          classes.add(BeanUtil.forName(className));
        }
      }
    }
  }

  private static void findClassesInJar(String path, String packagePath, List<Class<?>> classes) {
    // extract jar file name
    String fileName = path;
    if (fileName.contains("!")) {
      fileName = fileName.substring(0, fileName.indexOf('!'));
    }
    // extract classes
    try (JarFile jarFile = new JarFile(new File(new URL(fileName).toURI()))) {
      Enumeration<JarEntry> entries = jarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        String entryName = entry.getName();
        if (entryName.startsWith(packagePath) && entryName.endsWith(".class") && !entry.isDirectory()
            && !entry.getName().contains("$")) {
          String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
          classes.add(BeanUtil.forName(className));
        }
      }
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().internalError("error mapping file path", e);
    }
  }

  private static Object[] convertArray(Object[] values, Class<?>[] targetTypes) {
    Object[] result = new Object[values.length];
    for (int i = 0; i < values.length; i++) {
      result[i] = AnyConverter.convert(values[i], targetTypes[i]);
    }
    return result;
  }

  private static boolean isWrapperTypeOf(Class<?> propertyType,
                                         Object propertyValue) {
    String propertyTypeName = propertyType.getName();
    return (isPrimitiveType(propertyTypeName) && getWrapper(propertyType.getName()) == propertyValue.getClass());
  }

  public static boolean isImmutable(Class<?> type) {
    return IMMUTABLES.contains(type);
  }

  /** Represents a primitive-to-wrapper mapping. */
  private static final class PrimitiveTypeMapping {
    /** The Primitive type to map from. */
    public final Class<?> primitiveType;
    /** The Wrapper type to map to. */
    public final Class<?> wrapperType;

    public PrimitiveTypeMapping(Class<?> primitiveType, Class<?> wrapperType) {
      this.primitiveType = primitiveType;
      this.wrapperType = wrapperType;
    }
  }

}
