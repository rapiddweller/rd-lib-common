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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Bundles reflection and introspection related operations.
 * Created: 01.07.2006 08:44:33
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public final class BeanUtil {

  private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

  private static final HashSet<String> NON_CLASS_NAMES = new HashSet<>(100);

  private static final Escalator escalator = new LoggerEscalator();

  // (static) attributes ---------------------------------------------------------------------------------------------

  private static final Map<String, PropertyDescriptor> propertyDescriptors = new HashMap<>();

  /**
   * List of simple Java types.
   */
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

  private static final Class<?>[] integralNumberTypes = {
      long.class, Long.class,
      int.class, Integer.class,
      short.class, Short.class,
      byte.class, Byte.class,
      BigInteger.class
  };

  private static final Class<?>[] decimalNumberTypes = {
      float.class, Float.class,
      double.class, Double.class,
      BigDecimal.class
  };

  private static final PrimitiveTypeMapping[] primitiveNumberTypes = {
      new PrimitiveTypeMapping(long.class, Long.class),
      new PrimitiveTypeMapping(int.class, Integer.class),
      new PrimitiveTypeMapping(short.class, Short.class),
      new PrimitiveTypeMapping(byte.class, Byte.class),
      new PrimitiveTypeMapping(float.class, Float.class),
      new PrimitiveTypeMapping(double.class, Double.class)
  };

  private static final PrimitiveTypeMapping[] primitiveNonNumberTypes = {
      new PrimitiveTypeMapping(boolean.class, Boolean.class),
      new PrimitiveTypeMapping(char.class, Character.class),
  };

  /**
   * Map of integral Java number types
   */
  private static final Map<String, Class<?>> integralNumberTypeMap;

  /**
   * Map of decimal Java number types
   */
  private static final Map<String, Class<?>> decimalNumberTypeMap;

  /**
   * Map of simple Java types
   */
  private static final Map<String, Class<?>> simpleTypeMap;

  /**
   * Map of primitive Java types
   */
  private static final Map<String, Class<?>> primitiveTypeMap;

  /**
   * Map of primitive Java number types
   */
  private static final Map<String, Class<?>> primitiveNumberTypeMap;

  // initialization --------------------------------------------------------------------------------------------------

  static {
    simpleTypeMap = map(simpleTypes);
    integralNumberTypeMap = map(integralNumberTypes);
    decimalNumberTypeMap = map(decimalNumberTypes);
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

  private static Map<String, Class<?>> map(Class<?>[] array) {
    Map<String, Class<?>> result = new HashMap<>();
    for (Class<?> type : array) {
      result.put(type.getName(), type);
    }
    return result;
  }

  /**
   * Prevents instantiation of a BeanUtil object.
   */
  private BeanUtil() {
  }

  // type info methods -----------------------------------------------------------------------------------------------

  /**
   * Common super type class.
   *
   * @param objects the objects
   * @return the class
   */
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
        if (result == null) {
          result = candidateClass;
        } else if (candidateClass != result && candidateClass.isAssignableFrom(result)) {
          result = candidateClass;
        }
      }
    }
    return result;
  }

  /**
   * Common sub type class.
   *
   * @param objects the objects
   * @return the class
   */
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
        if (result == null) {
          result = candidateClass;
        } else if (candidateClass != result && result.isAssignableFrom(candidateClass)) {
          result = candidateClass;
        }
      }
    }
    return result;
  }

  /**
   * Tells if the provided class name is the name of a simple Java type
   *
   * @param className the name to check
   * @return true if it is a simple type, else false
   */
  public static boolean isSimpleType(String className) {
    return simpleTypeMap.containsKey(className);
  }

  /**
   * Is primitive type boolean.
   *
   * @param className the class name
   * @return the boolean
   */
  public static boolean isPrimitiveType(String className) {
    return primitiveTypeMap.containsKey(className);
  }

  /**
   * Is primitive number type boolean.
   *
   * @param className the class name
   * @return the boolean
   */
  public static boolean isPrimitiveNumberType(String className) {
    return primitiveNumberTypeMap.containsKey(className);
  }

  /**
   * Is number type boolean.
   *
   * @param type the type
   * @return the boolean
   */
  public static boolean isNumberType(Class<?> type) {
    return (isIntegralNumberType(type) || isDecimalNumberType(type));
  }

  /**
   * Is integral number type boolean.
   *
   * @param type the type
   * @return the boolean
   */
  public static boolean isIntegralNumberType(Class<?> type) {
    return isIntegralNumberType(type.getName());
  }

  /**
   * Is integral number type boolean.
   *
   * @param className the class name
   * @return the boolean
   */
  public static boolean isIntegralNumberType(String className) {
    return integralNumberTypeMap.containsKey(className);
  }

  /**
   * Is decimal number type boolean.
   *
   * @param type the type
   * @return the boolean
   */
  public static boolean isDecimalNumberType(Class<?> type) {
    return isDecimalNumberType(type.getName());
  }

  /**
   * Is decimal number type boolean.
   *
   * @param className the class name
   * @return the boolean
   */
  public static boolean isDecimalNumberType(String className) {
    return decimalNumberTypeMap.containsKey(className);
  }

  /**
   * Gets wrapper.
   *
   * @param primitiveClassName the primitive class name
   * @return the wrapper
   */
  public static Class<?> getWrapper(String primitiveClassName) {
    return primitiveTypeMap.get(primitiveClassName);
  }

  /**
   * Tells if the specified class is a collection type.
   *
   * @param type the class to check
   * @return true if the class is a collection type, false otherwise
   */
  public static boolean isCollectionType(Class<?> type) {
    return Collection.class.isAssignableFrom(type);
  }

  /**
   * Get types class [ ].
   *
   * @param objects the objects
   * @return the class [ ]
   */
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

  /**
   * Returns an object's attribute value
   *
   * @param obj           the object to query
   * @param attributeName the name of the attribute
   * @return the attribute value
   */
  public static Object getAttributeValue(Object obj, String attributeName) {
    if (obj == null) {
      throw new IllegalArgumentException("Object may not be null");
    }
    Field field = getField(obj.getClass(), attributeName);
    try {
      return field.get(obj);
    } catch (IllegalAccessException e) {
      throw ExceptionMapper.configurationException(e, field);
    }
  }

  /**
   * Sets an attribute value of an object.
   *
   * @param obj       the object to modify
   * @param fieldName the name of the attribute to set
   * @param value     the value to assign to the field
   */
  public static void setAttributeValue(Object obj, String fieldName, Object value) {
    Field field = getField(obj.getClass(), fieldName);
    setAttributeValue(obj, field, value);
  }

  /**
   * Returns a class' static attribute value
   *
   * @param objectType    the class to query
   * @param attributeName the name of the attribute
   * @return the attribute value
   */
  public static Object getStaticAttributeValue(Class<?> objectType, String attributeName) {
    Field field = getField(objectType, attributeName);
    try {
      return field.get(null);
    } catch (IllegalAccessException e) {
      throw ExceptionMapper.configurationException(e, field);
    }
  }

  /**
   * Sets a static attribute value of a class.
   *
   * @param objectType the class to modify
   * @param fieldName  the name of the attribute to set
   * @param value      the value to assign to the field
   */
  public static void setStaticAttributeValue(Class<?> objectType, String fieldName, Object value) {
    Field field = getField(objectType, fieldName);
    setAttributeValue(null, field, value);
  }

  /**
   * Sets an attribute value of an object.
   *
   * @param obj   the object to modify
   * @param field the attribute to set
   * @param value the value to assign to the field
   */
  public static void setAttributeValue(Object obj, Field field, Object value) {
    try {
      field.set(obj, value);
    } catch (IllegalAccessException e) {
      throw ExceptionMapper.configurationException(e, field);
    }
  }

  /**
   * Returns the generic type information of an attribute.
   *
   * @param field the field representation of the attribute.
   * @return an array of types that are used to parameterize the attribute.
   */
  public static Class<?>[] getGenericTypes(Field field) {
    Type genericFieldType = field.getGenericType();
    if (!(genericFieldType instanceof ParameterizedType)) {
      return null; // type is not generic
    }
    ParameterizedType pType = (ParameterizedType) genericFieldType;
    Type[] args = pType.getActualTypeArguments();
    Class<?>[] types = new Class[args.length];
    System.arraycopy(args, 0, types, 0, args.length);
    return types;
  }

  // instantiation ---------------------------------------------------------------------------------------------------

  /**
   * For name class.
   *
   * @param <T>  the type parameter
   * @param name the name
   * @return the class
   */
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
        throw ExceptionMapper.configurationException(e, name);
      } // this is raised by the Eclipse BundleLoader if it does not find the class

    }
  }

  /**
   * Gets context class loader.
   *
   * @return the context class loader
   */
  public static ClassLoader getContextClassLoader() {
    ClassLoader result = Thread.currentThread().getContextClassLoader();
    if (result == null) {
      result = BeanUtil.class.getClassLoader();
    }
    return result;
  }

  /**
   * Create jar class loader class loader.
   *
   * @param jarFile the jar file
   * @return the class loader
   */
  public static ClassLoader createJarClassLoader(File jarFile) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (jarFile != null) {
      try {
        classLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()}, classLoader);
      } catch (MalformedURLException e) {
        throw new RuntimeException("Unexpected error", e);
      }
    }
    return classLoader;
  }

  /**
   * Create directory class loader class loader.
   *
   * @param directory the directory
   * @return the class loader
   */
  public static ClassLoader createDirectoryClassLoader(File directory) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try {
      classLoader = new URLClassLoader(new URL[] {directory.toURI().toURL()}, classLoader);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Unexpected error", e);
    }
    return classLoader;
  }

  /**
   * Run with jar class loader.
   *
   * @param jarFile the jar file
   * @param action  the action
   */
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

  /**
   * Call with jar class loader t.
   *
   * @param <T>     the type parameter
   * @param jarFile the jar file
   * @param action  the action
   * @return the t
   * @throws Exception the exception
   */
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

  /**
   * Instantiates a class by the default constructor.
   *
   * @param className the name of the class to instantiate
   * @return an instance of the class
   */
  public static Object newInstance(String className) {
    Class<?> type = BeanUtil.forName(className);
    return newInstanceFromDefaultConstructor(type);
  }

  /**
   * New instance t.
   *
   * @param <T>  the type parameter
   * @param type the type
   * @return the t
   */
  public static <T> T newInstance(Class<T> type) {
    return newInstance(type, true, null);
  }

  /**
   * Creates an object of the specified type.
   *
   * @param <T>        the class of the object to instantiate
   * @param type       the class to instantiate
   * @param parameters the constructor parameters
   * @return an object of the specified class
   */
  public static <T> T newInstance(Class<T> type, Object[] parameters) {
    return newInstance(type, true, parameters);
  }

  /**
   * New instance t.
   *
   * @param <T>        the type parameter
   * @param type       the type
   * @param strict     the strict
   * @param parameters the parameters
   * @return the t
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> T newInstance(Class<T> type, boolean strict, Object[] parameters) {
    if (parameters == null || parameters.length == 0) {
      return newInstanceFromDefaultConstructor(type);
    }
    Constructor<T> constructorToUse = null;
    try {
      Constructor<T>[] constructors = (Constructor<T>[]) type.getConstructors();
      List<Constructor<T>> candidates = new ArrayList<>(constructors.length);
      int paramCount = parameters.length;
      for (Constructor<T> constructor : constructors) {
        if (constructor.getParameterTypes().length == paramCount) {
          candidates.add(constructor);
        }
      }
      if (candidates.size() == 1) {
        constructorToUse = candidates.get(0);
      } else if (candidates.size() == 0) {
        throw new ConfigurationError("No constructor with " + paramCount + " parameters found for " + type);
      } else {
        // there are several candidates - find the first one with matching types
        Class<?>[] paramTypes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
          paramTypes[i] = parameters[i].getClass();
        }
        for (Constructor c : type.getConstructors()) {
          if (typesMatch(c.getParameterTypes(), paramTypes)) {
            constructorToUse = c;
            break;
          }
        }
        // there is no ideal match
        if (constructorToUse == null) {
          if (strict) {
            throw new NoSuchMethodException("No appropriate constructor found: " + type + '(' + ArrayFormat.format(", ", paramTypes) + ')');
          }
          Exception mostRecentException = null;
          for (Constructor<T> candidate : candidates) {
            try {
              return newInstance(candidate, strict, parameters);
            } catch (Exception e) {
              mostRecentException = e;
              logger.warn("Exception in constructor call: " + candidate, e);
              continue; // ignore exception and try next constructor
            }
          }
          // no constructor could be called without exception
          String errMsg = (mostRecentException != null ?
              "None of these constructors could be called without exception: " + candidates + ", latest exception: " + mostRecentException :
              type + " has no appropriate constructor for the arguments " + ArrayFormat.format(", ", parameters));
          throw new ConfigurationError(errMsg);
        }
      }
      if (!strict) {
        parameters = convertArray(parameters, constructorToUse.getParameterTypes());
      }
      return newInstance(constructorToUse, parameters);
    } catch (SecurityException e) {
      throw ExceptionMapper.configurationException(e, constructorToUse);
    } catch (NoSuchMethodException e) {
      throw ExceptionMapper.configurationException(e, type);
    }
  }

  /**
   * Creates a new instance of a class.
   *
   * @param <T>         the class of the object to instantiate
   * @param constructor the constructor to invoke
   * @param params      the parameters to provide to the constructor
   * @return a new instance of the class
   */
  public static <T> T newInstance(Constructor<T> constructor, Object... params) {
    return newInstance(constructor, true, params);
  }

  /**
   * New instance t.
   *
   * @param <T>         the type parameter
   * @param constructor the constructor
   * @param strict      the strict
   * @param parameters  the parameters
   * @return the t
   */
  public static <T> T newInstance(Constructor<T> constructor, boolean strict, Object... parameters) {
    if (!strict) {
      parameters = convertArray(parameters, constructor.getParameterTypes());
    }
    Class<T> type = constructor.getDeclaringClass();
    if (deprecated(type)) {
      escalator.escalate("Instantiating a deprecated class: " + type.getName(), BeanUtil.class, null);
    }
    try {
      return constructor.newInstance(parameters);
    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
      throw ExceptionMapper.configurationException(e, type);
    }
  }

  /**
   * Clone t.
   *
   * @param <T>    the type parameter
   * @param object the object
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public static <T> T clone(T object) {
    try {
      Method cloneMethod = object.getClass().getMethod("clone");
      return (T) cloneMethod.invoke(object);
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new RuntimeException("Unexpected exception", e); // This is not supposed to happen
    } catch (InvocationTargetException e) {
      throw new RuntimeException("Execption occured in clone() method", e);
    }
  }

  /**
   * Clone all t [ ].
   *
   * @param <T>   the type parameter
   * @param input the input
   * @return the t [ ]
   */
  public static <T> T[] cloneAll(T[] input) {
    T[] output = ArrayUtil.newInstance(ArrayUtil.componentType(input), input.length);
    for (int i = 0; i < input.length; i++) {
      output[i] = clone(input[i]);
    }
    return output;
  }


  // method operations -----------------------------------------------------------------------------------------------

  /**
   * Finds a method by reflection. This iterates all methods of the class, comparing names and parameter types.
   * Unlike the method Class.getMethod(String, Class ...), this method is able to match primitive and wrapper types.
   * If no appropriate method is found, a ConfigurationError is raised.
   *
   * @param type       the class that holds the method
   * @param methodName the name of the method
   * @param paramTypes the parameter types of the method
   * @return a method with matching names and parameters
   */
  public static Method getMethod(Class<?> type, String methodName, Class<?>... paramTypes) {
    Method method = findMethod(type, methodName, paramTypes);
    if (method == null) {
      throw new ConfigurationError("method not found in class " + type.getName() + ": " + methodName
          + '(' + ArrayFormat.format(paramTypes) + ')');
    }
    return method;
  }

  /**
   * Finds a method by reflection. This iterates all methods of the class, comparing names and parameter types.
   * Unlike the method Class.getMethod(String, Class ...), this method is able to match primitive and wrapper types.
   * If no appropriate method is found, 'null' is returned
   *
   * @param type       the class that holds the method
   * @param methodName the name of the method
   * @param paramTypes the parameter types of the method
   * @return a method with matching names and parameters
   */
  public static Method findMethod(Class<?> type, String methodName, Class<?>... paramTypes) {
    Method result = null;
    for (Method method : type.getMethods()) {
      if (!methodName.equals(method.getName())) {
        continue;
      }
      if (typesMatch(paramTypes, method.getParameterTypes())) {
        result = method;
        if ((ArrayUtil.isEmpty(paramTypes) && ArrayUtil.isEmpty(method.getParameterTypes())) ||
            paramTypes.length == method.getParameterTypes().length) {
          return method; // optimal match - return it immediately
        } else {
          result = method; // sub optimal match - store it, but keep on searching for better matches
        }
      }
    }
    return result;
  }

  /**
   * Find methods by name method [ ].
   *
   * @param type       the type
   * @param methodName the method name
   * @return the method [ ]
   */
  public static Method[] findMethodsByName(Class<?> type, String methodName) {
    ArrayBuilder<Method> builder = new ArrayBuilder<>(Method.class);
    for (Method method : type.getMethods()) {
      if (methodName.equals(method.getName())) {
        builder.add(method);
      }
    }
    return builder.toArray();
  }

  /**
   * Find constructor constructor.
   *
   * @param <T>        the type parameter
   * @param type       the type
   * @param paramTypes the param types
   * @return the constructor
   */
  @SuppressWarnings("unchecked")
  public static <T> Constructor<T> findConstructor(Class<T> type, Class<?>... paramTypes) {
    Constructor<T>[] ctors = (Constructor<T>[]) type.getConstructors();
    for (Constructor<T> ctor : ctors) {
      if (typesMatch(paramTypes, ctor.getParameterTypes())) {
        return ctor;
      }
    }
    return null;
  }

  /**
   * Invokes a method on a bean.
   *
   * @param target     the object on which to invoke the mthod
   * @param methodName the name of the method
   * @param args       the arguments to provide to the method
   * @return the invoked method's return value.
   */
  public static Object invoke(Object target, String methodName, Object... args) {
    return invoke(true, target, methodName, args);
  }

  /**
   * Invoke object.
   *
   * @param strict     the strict
   * @param target     the target
   * @param methodName the method name
   * @param args       the args
   * @return the object
   */
  @SuppressWarnings("rawtypes")
  public static Object invoke(boolean strict, Object target, String methodName, Object... args) {
    if (target == null) {
      throw new IllegalArgumentException("target is null");
    }
    Class[] argTypes = getTypes(args);
    Method method;
    if (target instanceof Class) {
      method = getMethod((Class) target, methodName, argTypes);
    } else {
      method = getMethod(target.getClass(), methodName, argTypes);
    }
    return invoke(target, method, strict, args);
  }

  /**
   * Invoke static object.
   *
   * @param targetClass the target class
   * @param methodName  the method name
   * @param args        the args
   * @return the object
   */
  public static Object invokeStatic(Class<?> targetClass, String methodName, Object... args) {
    return invokeStatic(targetClass, methodName, true, args);
  }

  /**
   * Invoke static object.
   *
   * @param targetClass the target class
   * @param methodName  the method name
   * @param strict      the strict
   * @param args        the args
   * @return the object
   */
  public static Object invokeStatic(Class<?> targetClass, String methodName, boolean strict, Object... args) {
    if (targetClass == null) {
      throw new IllegalArgumentException("target is null");
    }
    Class<?>[] argClasses = new Class[args.length];
    for (int i = 0; i < args.length; i++) {
      argClasses[i] = (args[i] != null ? args[i].getClass() : null);
    }
    Method method = getMethod(targetClass, methodName, argClasses);
    return invoke(null, method, strict, args);
  }

  /**
   * Invokes a method on a bean.
   *
   * @param target the object on which to invoke the mthod
   * @param method the method to invoke
   * @param args   the arguments to provide to the method
   * @return the invoked method's return value.
   */
  public static Object invoke(Object target, Method method, Object[] args) {
    return invoke(target, method, true, args);
  }

  /**
   * Invoke object.
   *
   * @param target the target
   * @param method the method
   * @param strict the strict
   * @param args   the args
   * @return the object
   */
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
        throw new RuntimeException("Method " + method.getName() + " requires " + paramTypes.length + " params, " +
            "but only " + args.length + " were provided. ");
      }
      return method.invoke(target, params);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionMapper.configurationException(e, method);
    }
  }

  /**
   * Types match boolean.
   *
   * @param usedTypes     the used types
   * @param expectedTypes the expected types
   * @return the boolean
   */
  public static boolean typesMatch(Class<?>[] usedTypes, Class<?>[] expectedTypes) {
    // expectedTypes is empty
    if (ArrayUtil.isEmpty(expectedTypes)) {
      return ArrayUtil.isEmpty(usedTypes);
    }
    Class<?> lastExpectedType = ArrayUtil.lastElementOf(expectedTypes);
    if (lastExpectedType.isArray()) {
      // process varargs parameter
      if (usedTypes.length < expectedTypes.length - 1) {
        return false; // fault
      }
      if (usedTypes.length == expectedTypes.length - 1) {
        return typesMatch(usedTypes, ArrayUtil.copyOfRange(expectedTypes, 0, usedTypes.length)); // empty varargs
      }
      // check if all used types match the varargs type
      if (usedTypes.length >= expectedTypes.length) {
        Class<?> componentType = lastExpectedType.getComponentType();
        for (int i = expectedTypes.length - 1; i < usedTypes.length; i++) {
          Class<?> foundType = usedTypes[i];
          if (!typeMatches(foundType, componentType)) {
            return false;
          }
        }
        return true;
      }
    }
    if (usedTypes.length != expectedTypes.length) {
      return false;
    }
    if (expectedTypes.length == 0 && usedTypes.length == 0) {
      return true;
    }
    for (int i = 0; i < usedTypes.length; i++) {
      Class<?> expectedType = expectedTypes[i];
      Class<?> foundType = usedTypes[i];
      if (!typeMatches(foundType, expectedType)) {
        return false;
      }
    }
    return true;
  }

  // JavaBean operations ---------------------------------------------------------------------------------------------

  /**
   * Returns the bean property descriptor of an attribute
   *
   * @param beanClass    the class that holds the attribute
   * @param propertyName the name of the property
   * @return the attribute's property descriptor
   */
  public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
    if (beanClass == null) {
      throw new IllegalArgumentException("beanClass is null");
    }
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
        throw ExceptionMapper.configurationException(e, propertyName);
      }
    }
    propertyDescriptors.put(propertyId, result);
    return result;
  }

  /**
   * Gets property descriptor.
   *
   * @param type         the type
   * @param propertyName the property name
   * @param required     the required
   * @return the property descriptor
   */
  public static PropertyDescriptor getPropertyDescriptor(
      Class<?> type, String propertyName, boolean required) {
    PropertyDescriptor descriptor = getPropertyDescriptor(type, propertyName);
    if (required && descriptor == null) {
      throw new UnsupportedOperationException(type.getName() + " does not have a property " + propertyName);
    }
    return descriptor;
  }

  /**
   * Has property boolean.
   *
   * @param beanClass    the bean class
   * @param propertyName the property name
   * @return the boolean
   */
  public static boolean hasProperty(Class<?> beanClass, String propertyName) {
    return (getPropertyDescriptor(beanClass, propertyName) != null);
  }

  /**
   * Has writeable property boolean.
   *
   * @param beanClass    the bean class
   * @param propertyName the property name
   * @return the boolean
   */
  public static boolean hasWriteableProperty(Class<?> beanClass, String propertyName) {
    PropertyDescriptor descriptor = getPropertyDescriptor(beanClass, propertyName);
    return (descriptor != null && descriptor.getWriteMethod() != null);
  }

  /**
   * returns the name of a property read method.
   *
   * @param propertyName the name of the property
   * @param propertyType the type of the property
   * @return the name of the property read method
   */
  public static String readMethodName(String propertyName, Class<?> propertyType) {
    if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
      return "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    } else {
      return "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }
  }

  /**
   * returns the name of a property write method.
   *
   * @param propertyName the name of the property
   * @return the name of the property write method
   */
  public static String writeMethodName(String propertyName) {
    return "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
  }

  /**
   * Finds all property descriptors of a bean class
   *
   * @param type the class to check
   * @return all found property descriptors
   */
  public static PropertyDescriptor[] getPropertyDescriptors(Class<?> type) {
    try {
      return Introspector.getBeanInfo(type).getPropertyDescriptors();
    } catch (IntrospectionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Copies a Map's values to the properties of a JavaBean,
   * using the Map entries' key values as bean property names.
   *
   * @param sourceBean the bean from which to read the properties
   * @param targetBean the bean on which to set the properties
   */
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

  /**
   * Sets property values.
   *
   * @param bean       the bean
   * @param properties the properties
   */
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
    } catch (IntrospectionException e) {
      throw ExceptionMapper.configurationException(e, beanClass);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionMapper.configurationException(e, writeMethod);
    }
  }
    
/*
    public static Class getPropertyType(Class beanClass, String propertyName) {
        PropertyDescriptor descriptor = getPropertyDescriptor(beanClass, propertyName);
        return (descriptor != null ? descriptor.getPropertyType() : null);
    }
*/

  /**
   * Gets property values.
   *
   * @param bean         the bean
   * @param includeClass the include class
   * @return the property values
   */
  public static Map<String, ?> getPropertyValues(Object bean, boolean includeClass) {
    Map<String, Object> result = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (includeClass || !"class".equals(propertyName)) {
        result.put(propertyName, getPropertyValue(bean, descriptor));
      }
    }
    return result;
  }

  /**
   * Gets readable property values.
   *
   * @param bean         the bean
   * @param includeClass the include class
   * @return the readable property values
   */
  public static Map<String, ?> getReadablePropertyValues(Object bean, boolean includeClass) {
    Map<String, Object> result = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (descriptor.getReadMethod() != null && (includeClass || !"class".equals(propertyName))) {
        result.put(propertyName, getPropertyValue(bean, descriptor));
      }
    }
    return result;
  }

  /**
   * Gets rw property values.
   *
   * @param bean         the bean
   * @param includeClass the include class
   * @return the rw property values
   */
  public static Map<String, ?> getRWPropertyValues(Object bean, boolean includeClass) {
    Map<String, Object> result = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(bean.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      String propertyName = descriptor.getName();
      if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null && (includeClass || !"class".equals(propertyName))) {
        result.put(propertyName, getPropertyValue(bean, descriptor));
      }
    }
    return result;
  }

  /**
   * Queries a property value on a JavaBean instance
   *
   * @param bean         the bean to read
   * @param propertyName the name of the property to read
   * @return the property value
   */
  public static Object getPropertyValue(Object bean, String propertyName) {
    return getPropertyValue(bean, propertyName, true);
  }

  /**
   * Gets property value.
   *
   * @param bean             the bean
   * @param propertyName     the property name
   * @param propertyRequired the property required
   * @return the property value
   */
  public static Object getPropertyValue(Object bean, String propertyName, boolean propertyRequired) {
    PropertyDescriptor descriptor = getPropertyDescriptor(bean.getClass(), propertyName);
    if (descriptor == null) {
      if (propertyRequired) {
        throw new ConfigurationError("Property '" + propertyName + "' not found in class " + bean.getClass());
      } else {
        return null;
      }
    }
    return getPropertyValue(bean, descriptor);
  }

  private static Object getPropertyValue(Object bean, PropertyDescriptor descriptor) {
    Method readMethod = null;
    try {
      readMethod = descriptor.getReadMethod();
      return readMethod.invoke(bean);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionMapper.configurationException(e, readMethod);
    }
  }

  /**
   * Sets a property value on a JavaBean object.
   *
   * @param bean          the bean on which to set the property
   * @param propertyName  the name of the property to set
   * @param propertyValue the value to set the property to
   */
  public static void setPropertyValue(Object bean, String propertyName, Object propertyValue) {
    setPropertyValue(bean, propertyName, propertyValue, true);
  }

  /**
   * Sets property value.
   *
   * @param bean          the bean
   * @param propertyName  the property name
   * @param propertyValue the property value
   * @param strict        the strict
   */
  public static void setPropertyValue(Object bean, String propertyName, Object propertyValue, boolean strict) {
    setPropertyValue(bean, propertyName, propertyValue, strict, !strict);
  }

  /**
   * Sets property value.
   *
   * @param bean          the bean
   * @param propertyName  the property name
   * @param propertyValue the property value
   * @param required      the required
   * @param autoConvert   the auto convert
   */
  public static void setPropertyValue(Object bean, String propertyName, Object propertyValue, boolean required, boolean autoConvert) {
    Method writeMethod = null;
    try {
      Class<?> beanClass = bean.getClass();
      PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanClass, propertyName);
      if (propertyDescriptor == null) {
        if (required) {
          throw new ConfigurationError(beanClass + " does not have a property '" + propertyName + "'");
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
            throw new IllegalArgumentException("ArgumentType mismatch: expected "
                + propertyType.getName() + ", found " + propertyValue.getClass().getName());
          } else {
            propertyValue = AnyConverter.convert(propertyValue, propertyType);
          }
        }
        writeMethod.invoke(bean, propertyValue);
      } else if (required) {
        throw new UnsupportedOperationException("Cannot write read-only property '"
            + propertyDescriptor.getName() + "' of " + beanClass);
      } else {
        // no write method but property is not required, so ignore it silently
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw ExceptionMapper.configurationException(e, writeMethod);
    }
  }

  /**
   * Extract properties list.
   *
   * @param <BEAN>       the type parameter
   * @param <PROP_TYPE>  the type parameter
   * @param beans        the beans
   * @param propertyName the property name
   * @return the list
   */
  @SuppressWarnings("unchecked")
  public static <BEAN, PROP_TYPE> List<PROP_TYPE> extractProperties(Collection<BEAN> beans, String propertyName) {
    List<PROP_TYPE> result = new ArrayList<>(beans.size());
    for (BEAN bean : beans) {
      result.add((PROP_TYPE) getPropertyValue(bean, propertyName));
    }
    return result;
  }

  /**
   * Extract properties prop type [ ].
   *
   * @param <BEAN>       the type parameter
   * @param <PROP_TYPE>  the type parameter
   * @param beans        the beans
   * @param propertyName the property name
   * @param propertyType the property type
   * @return the prop type [ ]
   */
  @SuppressWarnings("unchecked")
  public static <BEAN, PROP_TYPE> PROP_TYPE[] extractProperties(BEAN[] beans, String propertyName, Class<PROP_TYPE> propertyType) {
    PROP_TYPE[] result = ArrayUtil.newInstance(propertyType, beans.length);
    for (int i = 0; i < beans.length; i++) {
      BEAN bean = beans[i];
      result[i] = (PROP_TYPE) getPropertyValue(bean, propertyName);
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

  /**
   * Checks if a class fulfills the JavaBeans contract.
   *
   * @param cls the class to check
   */
  public static void checkJavaBean(Class<?> cls) {
    try {
      Constructor<?> constructor = cls.getDeclaredConstructor();
      int classModifiers = cls.getModifiers();
      if (Modifier.isInterface(classModifiers)) {
        throw new RuntimeException(cls.getName() + " is an interface");
      }
      if (Modifier.isAbstract(classModifiers)) {
        throw new RuntimeException(cls.getName() + " cannot be instantiated - it is an abstract class");
      }
      int modifiers = constructor.getModifiers();
      if (!Modifier.isPublic(modifiers)) {
        throw new RuntimeException("No public default constructor in " + cls);
      }
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("No default constructor in class " + cls);
    } catch (SecurityException e) {
      logger.error("I am not allowed to check the class by using reflection, " +
          "so I just can hope the class is alright and go on: ", e);
    }
  }

  /**
   * Tells if a class is deprecated.
   *
   * @param type the class to check for deprecation
   * @return true if the class is deprecated, else false
   * @since 0.2.05
   */
  public static boolean deprecated(Class<?> type) {
    Annotation[] annotations = type.getDeclaredAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation instanceof Deprecated) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets classes.
   *
   * @param packageName the package name
   * @return the classes
   */
  public static List<Class<?>> getClasses(String packageName) {
    try {
      ClassLoader classLoader = getContextClassLoader();
      String packagePath = packageName.replace('.', '/');
      Enumeration<URL> resourceUris = classLoader.getResources(packagePath);
      List<Class<?>> classes = new ArrayList<>();
      while (resourceUris.hasMoreElements()) {
        URL resource = resourceUris.nextElement();
        String protocol = resource.getProtocol();
        if ("jar".equals(protocol)) {
          findClassesInJar(resource.getFile(), packagePath, classes);
        } else if ("file".equals(protocol)) {
          findClassesInDirectory(new File(resource.toURI()), packageName, classes);
        } else {
          throw new UnsupportedOperationException("Not a supported protocol: " + protocol);
        }
      }
      return classes;
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets field value.
   *
   * @param target the target
   * @param name   the name
   * @param strict the strict
   * @return the field value
   */
  public static Object getFieldValue(Object target, String name, boolean strict) {
    Class<?> type = target.getClass();
    try {
      Field field = type.getField(name);
      return getFieldValue(field, target, strict);
    } catch (NoSuchFieldException e) {
      if (strict) {
        throw ExceptionMapper.configurationException(e, type.getName() + '.' + name);
      } else {
        escalator.escalate("Class '" + type + "' does not have a field '" + name + "'", type, name);
        return null;
      }
    }
  }

  /**
   * Gets field value.
   *
   * @param field  the field
   * @param target the target
   * @param strict the strict
   * @return the field value
   */
  public static Object getFieldValue(Field field, Object target, boolean strict) {
    try {
      if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
        return field.get(null);
      } else {
        return field.get(target);
      }
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Returns a Field object that represents an attribute of a class
   *
   * @param type the class that holds the attribute
   * @param name the name of the attribute
   * @return a Field object that represents the attribute
   */
  public static Field getField(Class<?> type, String name) {
    try {
      return type.getField(name);
    } catch (NoSuchFieldException e) {
      throw ExceptionMapper.configurationException(e, type.getName() + '.' + name);
    }
  }

  /**
   * Find methods by annotation method [ ].
   *
   * @param owner           the owner
   * @param annotationClass the annotation class
   * @return the method [ ]
   */
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

  /**
   * Get generic interface params type [ ].
   *
   * @param <C>               the type parameter
   * @param <I>               the type parameter
   * @param checkedClass      the checked class
   * @param searchedInterface the searched interface
   * @return the type [ ]
   */
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
    throw new ConfigurationError(checkedClass + " does not implement interface with generic parameters: " + searchedInterface);
  }

  /**
   * Class name string.
   *
   * @param <T> the type parameter
   * @param o   the o
   * @return the string
   */
  public static <T> String className(Object o) {
    if (o == null) {
      return null;
    }
    return (o instanceof Class ? ((Class<?>) o).getName() : o.getClass().getName());
  }

  /**
   * Simple class name string.
   *
   * @param <T> the type parameter
   * @param o   the o
   * @return the string
   */
  public static <T> String simpleClassName(Object o) {
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

  /**
   * Null safe hash code int.
   *
   * @param object the object
   * @return the int
   */
  public static int nullSafeHashCode(Object object) {
    return (object != null ? object.hashCode() : 0);
  }

  /**
   * Exists class boolean.
   *
   * @param className the class name
   * @return the boolean
   */
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

  /**
   * To string string.
   *
   * @param bean the bean
   * @return the string
   */
  public static String toString(Object bean) {
    return toString(bean, false);
  }

  /**
   * To string string.
   *
   * @param bean   the bean
   * @param simple the simple
   * @return the string
   */
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
      if (!"class".equals(propertyName) && descriptor.getReadMethod() != null) {
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

  /**
   * Creates an instance of the class using the default constructor.
   *
   * @param type the type to instantiate
   * @return a new instance of the type
   * @since 0.2.06
   */
  @SuppressWarnings("cast")
  private static <T> T newInstanceFromDefaultConstructor(Class<T> type) {
    if (type == null) {
      return null;
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Instantiating " + type.getSimpleName());
    }
    if (deprecated(type)) {
      escalator.escalate("Instantiating a deprecated class: " + type.getName(), BeanUtil.class, null);
    }
    try {
      return type.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw ExceptionMapper.configurationException(e, type);
    }
  }

  private static void findClassesInDirectory(File directory, String packagePath, List<Class<?>> classes) {
    File[] files = directory.listFiles();
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

  private static void findClassesInJar(String path, String packagePath, List<Class<?>> classes)
      throws IOException, URISyntaxException {
    // extract jar file name
    String fileName = path;
    if (fileName.contains("!")) {
      fileName = fileName.substring(0, fileName.indexOf('!'));
    }
    // extract classes
    JarFile jarFile = null;
    try {
      jarFile = new JarFile(new File(new URL(fileName).toURI()));
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
    } finally {
      IOUtil.close(jarFile);
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

  /**
   * Represents a primitive-to-wrapper mapping.
   */
  private static final class PrimitiveTypeMapping {
    /**
     * The Primitive type.
     */
    public Class<?> primitiveType;
    /**
     * The Wrapper type.
     */
    public Class<?> wrapperType;

    /**
     * Instantiates a new Primitive type mapping.
     *
     * @param primitiveType the primitive type
     * @param wrapperType   the wrapper type
     */
    public PrimitiveTypeMapping(Class<?> primitiveType, Class<?> wrapperType) {
      this.primitiveType = primitiveType;
      this.wrapperType = wrapperType;
    }
  }

}
