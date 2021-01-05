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

import com.rapiddweller.common.converter.Base64ToByteArrayConverter;

import java.lang.annotation.Annotation;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.html.dom.HTMLDocumentImpl;

import org.junit.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.PrintWriter;

/**
 * Tests the BeanUtil class.
 * Created: 05.04.2007 07:51:00
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class BeanUtilTest {

    // type info tests -------------------------------------------------------------------------------------------------

    @Test
    public void testCommonSuperType() {
        assertNull(BeanUtil.commonSuperType(CollectionUtil.toList()));
        assertEquals(Integer.class, BeanUtil.commonSuperType(CollectionUtil.toList(1, 2, 3)));
        assertEquals(Object.class, BeanUtil.commonSuperType(CollectionUtil.toList(new Object(), 3)));
        assertNull(BeanUtil.commonSuperType(new ArrayList<>()));
    }

    @Test
    public void testCommonSubType() {
        assertNull(BeanUtil.commonSubType(CollectionUtil.toList()));
        assertEquals(Integer.class, BeanUtil.commonSubType(CollectionUtil.toList(1, 2, 3)));
        assertEquals(Integer.class, BeanUtil.commonSubType(CollectionUtil.toList(new Object(), 3)));
        assertNull(BeanUtil.commonSubType(new ArrayList<>()));
    }

    @Test
    public void testIsSimpleType() {
        assertFalse(BeanUtil.isSimpleType("Class Name"));
        assertTrue(BeanUtil.isSimpleType("java.lang.Boolean"));
    }

    @Test
    public void testIsSimpleTypeByName() {
        assertTrue(BeanUtil.isSimpleType("int"));
        assertTrue(BeanUtil.isSimpleType("java.lang.Integer"));
        assertTrue(BeanUtil.isSimpleType("java.lang.String"));
        assertFalse(BeanUtil.isSimpleType("java.lang.StringBuffer"));
        assertFalse(BeanUtil.isSimpleType("com.rapiddweller.common.BeanUtil"));
        assertFalse(BeanUtil.isSimpleType(null));
    }

    @Test
    public void testIsPrimitiveType() {
        assertFalse(BeanUtil.isPrimitiveType("Class Name"));
    }

    @Test
    public void testIsPrimitiveNumberType() {
        assertFalse(BeanUtil.isPrimitiveNumberType("Class Name"));
    }

    @Test
    public void testIsNumberType() {
        assertFalse(BeanUtil.isNumberType(Object.class));
        assertTrue(BeanUtil.isNumberType(Byte.class));
        assertTrue(BeanUtil.isNumberType(Double.class));
    }

    @Test
    public void testIsIntegralNumberType() {
        assertFalse(BeanUtil.isIntegralNumberType(Object.class));
        assertTrue(BeanUtil.isIntegralNumberType(Byte.class));
        assertFalse(BeanUtil.isIntegralNumberType("Class Name"));
        assertTrue(BeanUtil.isIntegralNumberType("java.lang.Byte"));
    }

    @Test
    public void testIsDecimalNumberType() {
        assertFalse(BeanUtil.isDecimalNumberType(Object.class));
        assertTrue(BeanUtil.isDecimalNumberType(Double.class));
        assertFalse(BeanUtil.isDecimalNumberType("Class Name"));
        assertTrue(BeanUtil.isDecimalNumberType("java.lang.Double"));
    }

    @Test
    public void testIsPrimitive() {
        assertTrue(BeanUtil.isPrimitiveType(int.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Integer.class.getName()));
        assertTrue(BeanUtil.isPrimitiveType(char.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Character.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(String.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(StringBuffer.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(BeanUtil.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(null));
    }

    @Test
    public void testIsPrimitiveNumber() {
        assertTrue(BeanUtil.isPrimitiveType(int.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Integer.class.getName()));
        assertTrue(BeanUtil.isPrimitiveType(char.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(Character.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(String.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(StringBuffer.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(BeanUtil.class.getName()));
        assertFalse(BeanUtil.isPrimitiveType(null));
    }

    @Test
    public void testGetWrapper() {
        assertEquals(Integer.class, BeanUtil.getWrapper(int.class.getName()));
        assertEquals(Character.class, BeanUtil.getWrapper(char.class.getName()));
        assertNull(BeanUtil.getWrapper("Primitive Class Name"));
    }

    @Test
    public void testIsCollectionType() {
        assertTrue(BeanUtil.isCollectionType(Collection.class));
        assertTrue(BeanUtil.isCollectionType(List.class));
        assertTrue(BeanUtil.isCollectionType(ArrayList.class));
        assertTrue(BeanUtil.isCollectionType(Set.class));
        assertTrue(BeanUtil.isCollectionType(HashSet.class));
        assertFalse(BeanUtil.isCollectionType(Map.class));
        assertFalse(BeanUtil.isCollectionType(Object.class));
    }

    @Test(expected = NullPointerException.class)
    public void testGetTypes() {
        assertEquals(1, BeanUtil.getTypes("objects").length);
        assertEquals(1, BeanUtil.getTypes(null).length);
        assertNull(BeanUtil.getTypes(null));
    }

    // field tests -----------------------------------------------------------------------------------------------------

    @Test
    public void testGetAttributeValue() {
        P p = new P();
        assertEquals(1, BeanUtil.getAttributeValue(p, "val"));
    }

    @Test
    public void testSetAttribute() {
        P p = new P();
        BeanUtil.setAttributeValue(p, "val", 2);
        assertEquals(2, p.val);
    }

    @Test
    public void testGetStaticAttributeValue() {
        B.stat = "x";
        assertEquals("x", BeanUtil.getStaticAttributeValue(B.class, "stat"));
    }

    @Test
    public void testSetStaticAttribute() {
        BeanUtil.setStaticAttributeValue(B.class, "stat", "y");
        assertEquals("y", B.stat);
    }

    @Test
    public void testGetGenericTypes() throws NoSuchFieldException {
        Object o = new Object() {
            @SuppressWarnings("unused")
            public List<Integer> list;
        };
        Class<?> c = o.getClass();
        Field f = c.getField("list");
        assertTrue("Test for generic type failed",
                Arrays.deepEquals(new Class[]{Integer.class}, BeanUtil.getGenericTypes(f)));
    }

    // instantiation tests ---------------------------------------------------------------------------------------------

    @Test
    public void testFindClasses() throws Exception {
        List<Class<?>> classes;
        // test directory in class path
        classes = BeanUtil.getClasses(BeanUtil.class.getPackage().getName());
        assertTrue(classes.contains(BeanUtil.class));
        assertTrue(classes.contains(TimeUtil.class));
    }

    @Test
    public void testForName() {
        Class<P> type = BeanUtil.forName("com.rapiddweller.common.BeanUtilTest$P");
        assertEquals(P.class, type);
    }

    @Test
    public void testForName2() {
        Class<Object> actualForNameResult = BeanUtil.<Object>forName("com.rapiddweller.common.BeanUtil");
        assertSame(BeanUtil.class, actualForNameResult);
    }

    @Test
    public void testForName3() {
        Class<Object> actualForNameResult = BeanUtil.<Object>forName("java.lang.Boolean");
        assertSame(Boolean.class, actualForNameResult);
    }

    @Test
    public void testCreateJarClassLoader() {
        assertTrue(BeanUtil.createJarClassLoader(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()) instanceof java.net.URLClassLoader);
    }

    @Test
    public void testCreateDirectoryClassLoader() {
        assertTrue(BeanUtil.createDirectoryClassLoader(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()) instanceof java.net.URLClassLoader);
    }

    @Test
    public void testNewInstanceWithConstructorParams() {
        P p = BeanUtil.newInstance(P.class, null);
        assertEquals(1, p.val);
        p = BeanUtil.newInstance(P.class, new Object[]{2});
        assertEquals(2, p.val);
    }

    @Test
    public void testNewInstanceWithParamConversion() {
        P p = BeanUtil.newInstance(P.class, false, new Object[]{2});
        assertEquals(2, p.val);
        p = BeanUtil.newInstance(P.class, false, new Object[]{"2"});
        assertEquals(2, p.val);
    }

    @Test
    public void testNewInstance() {
        assertThrows(ConfigurationError.class,
                () -> BeanUtil.<Object>newInstance(Object.class, true, new Object[]{"parameters"}));
        assertNull(BeanUtil.<Object>newInstance((Class<Object>) null, true, null));
        assertNull(BeanUtil.<Object>newInstance((Class<Object>) null, true, new Object[]{}));
        assertThrows(ConfigurationError.class,
                () -> BeanUtil.<Object>newInstance(Object.class, new Object[]{"parameters"}));
        assertNull(BeanUtil.<Object>newInstance((Class<Object>) null, null));
        assertNull(BeanUtil.<Object>newInstance((Class<Object>) null, new Object[]{}));
        assertTrue(BeanUtil.newInstance("com.rapiddweller.common.BeanUtil") instanceof BeanUtil);
    }

    @Test
    public void testNewInstanceFromClassName() {
        P p = (P) BeanUtil.newInstance(P.class.getName());
        assertEquals(1, p.val);
    }

    @Test
    public void testNewInstanceFromConstructor() throws SecurityException, NoSuchMethodException {
        Constructor<P> constructor = P.class.getDeclaredConstructor(int.class);
        P p = BeanUtil.newInstance(constructor, 1000);
        assertEquals(1000, p.val);
    }

    @Test
    public void testCloneAll() {
        assertThrows(RuntimeException.class, () -> BeanUtil.<Object>cloneAll(new Object[]{"input"}));
        assertEquals(0, BeanUtil.<Object>cloneAll(new Object[]{}).length);
    }

    // method tests ----------------------------------------------------------------------------------------------------

    @Test
    public void testGetMethod() throws IllegalAccessException, InvocationTargetException {
        Method method = BeanUtil.getMethod(P.class, "getVal");
        P p = new P();
        assertEquals(1, method.invoke(p));
        method = BeanUtil.getMethod(P.class, "setVal", Integer.class);
        method.invoke(p, 2);
        assertEquals(2, p.val);
        try {
            BeanUtil.findMethod(P.class, "setBlaBla", Integer.class);
        } catch (ConfigurationError e) {
            // desired behavior
        }
    }

    @Test
    public void testFindMethod() throws IllegalAccessException, InvocationTargetException {
        Method method = BeanUtil.findMethod(P.class, "getVal");
        P p = new P();
        assertEquals(1, method.invoke(p));
        method = BeanUtil.findMethod(P.class, "setVal", Integer.class);
        method.invoke(p, 2);
        assertEquals(2, p.val);
        assertNull(BeanUtil.findMethod(P.class, "setBlaBla", Integer.class));
    }

    @Test
    public void testFindMethod2() {
        Class<?> type = Object.class;
        assertNull(BeanUtil.findMethod(type, "Method Name", Object.class));
    }

    @Test
    public void testFindMethod3() {
        Class<?> type = Object.class;
        assertNull(BeanUtil.findMethod(type, "wait", Object.class));
    }

    @Test
    public void testFindMethod4() {
        assertNull(BeanUtil.findMethod(Object.class, "!", null));
    }

    @Test
    public void testFindMethodsByName() {
        assertEquals(0, BeanUtil.findMethodsByName(Object.class, "Method Name").length);
        assertEquals(3, BeanUtil.findMethodsByName(Object.class, "wait").length);
    }

    @Test
    public void testFindConstructor() {
        Class<?> type = Object.class;
        assertNull(BeanUtil.<Object>findConstructor((Class<Object>) type, Object.class));
    }

    @Test
    public void testFindConstructor2() {
        Class<?> type = Boolean.class;
        assertNull(BeanUtil.findConstructor((Class<Object>) type, Object.class));
    }


    @Test
    public void testFindConstructor4() {
        Class<?> type = Boolean.class;
        assertNull(BeanUtil.<Object>findConstructor((Class<Object>) type, Byte.class));
    }

    @Test
    public void testFindConstructor5() {
        Class<?> type = Boolean.class;
        assertNull(BeanUtil.<Object>findConstructor((Class<Object>) type, Double.class));
    }

    @Test
    public void testInvoke() {
        P p = new P();
        assertEquals(1, BeanUtil.invoke(p, "getVal"));
        assertEquals(1, BeanUtil.invoke(p, "getVal", (Object[]) null));
        BeanUtil.invoke(p, "setVal", 2);
        assertEquals(2, p.val);
    }

    @Test(expected = ConfigurationError.class)
    public void testInvokeToFewParams() {
        P p = new P();
        BeanUtil.invoke(p, "setVal");
    }

    @Test
    public void testInvokeStatic() {
        B.setStat("x");
        assertEquals("x", BeanUtil.invokeStatic(B.class, "getStat"));
        BeanUtil.invokeStatic(B.class, "setStat", "u");
        assertEquals("u", B.stat);
    }

    @Test
    public void testInvokeVarargs() {
        V v = new V();
        // varargs1
        assertEquals(0, BeanUtil.invoke(v, "varargs1"));
        assertEquals(1, BeanUtil.invoke(false, v, "varargs1", 1));
        assertEquals(2, BeanUtil.invoke(false, v, "varargs1", 1, 2));
        // varargs2
        assertEquals(-1, BeanUtil.invoke(false, v, "varargs2", 1));
        assertEquals(1, BeanUtil.invoke(false, v, "varargs2", 1, 1));
        assertEquals(2, BeanUtil.invoke(false, v, "varargs2", 1, 1, 2));
        assertEquals(2, BeanUtil.invoke(false, v, "varargs2", 1, 1, 2));
        // varargs3
        assertEquals("x", BeanUtil.invoke(false, v, "varargs3", "x"));
        assertEquals(1, BeanUtil.invoke(false, v, "varargs3", "x", 1));
        assertEquals(2, BeanUtil.invoke(false, v, "varargs3", "x", 1, 2));
    }

    @Test
    public void testTypesMatch() {
        // no-arg method calls
        assertTrue(BeanUtil.typesMatch(new Class[]{}, new Class[]{}));
        assertFalse(BeanUtil.typesMatch(new Class[]{String.class}, new Class[]{}));
        // Identical types
        assertTrue(BeanUtil.typesMatch(new Class[]{String.class}, new Class[]{String.class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{C.class}, new Class[]{B.class}));
        // incompatible types
        assertFalse(BeanUtil.typesMatch(new Class[]{B.class}, new Class[]{C.class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{A.class}, new Class[]{I.class}));
        // autoboxing
        assertTrue(BeanUtil.typesMatch(new Class[]{int.class}, new Class[]{Integer.class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{Integer.class}, new Class[]{int.class}));
        // varargs
        assertTrue(BeanUtil.typesMatch(new Class[]{}, new Class[]{Integer[].class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{Integer.class}, new Class[]{Integer[].class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{Integer.class}, new Class[]{Integer.class, Integer[].class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{Integer.class, Integer.class},
                new Class[]{Integer.class, Integer[].class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{Object.class}, new Class[]{Object.class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{null}, new Class[]{Object.class}));
        assertTrue(BeanUtil.typesMatch(null, new Class[]{}));
        assertFalse(BeanUtil.typesMatch(new Class[]{}, new Class[]{Object.class}));
        assertFalse(BeanUtil.typesMatch(new Class[]{Object.class}, new Class[]{BeanUtil.class}));
        assertFalse(BeanUtil.typesMatch(new Class[]{Byte.class}, new Class[]{BeanUtil.class}));
        assertFalse(BeanUtil.typesMatch(new Class[]{Double.class}, new Class[]{BeanUtil.class}));
        assertTrue(BeanUtil.typesMatch(new Class[]{Double.class}, new Class[]{Byte.class}));
    }

    // property tests --------------------------------------------------------------------------------------------------

    @Test
    public void testGetPropertyDescriptor() throws IllegalAccessException, InvocationTargetException {
        PropertyDescriptor desc = BeanUtil.getPropertyDescriptor(P.class, "val");
        assertEquals("val", desc.getName());
        P p = new P();
        desc.getWriteMethod().invoke(p, 2);
        assertEquals(2, p.val);
    }

    @Test
    public void testGetPropertyDescriptor10() {
        assertNull(BeanUtil.getPropertyDescriptor(Object.class, "Property Name", false));
    }

    @Test
    public void testGetPropertyDescriptor2() {
        assertNull(BeanUtil.getPropertyDescriptor(Object.class, "Property Name"));
    }

    @Test
    public void testGetPropertyDescriptor3() {
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.getPropertyDescriptor(null, "Property Name"));
    }

    @Test
    public void testGetPropertyDescriptor4() {
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.getPropertyDescriptor(null, null));
    }

    @Test
    public void testGetPropertyDescriptor5() {
        assertNull(BeanUtil.getPropertyDescriptor(Object.class, ".class"));
    }

    @Test
    public void testGetPropertyDescriptor6() {
        assertThrows(UnsupportedOperationException.class,
                () -> BeanUtil.getPropertyDescriptor(Object.class, "Property Name", true));
    }

    @Test
    public void testGetPropertyDescriptor7() {
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.getPropertyDescriptor(null, "Property Name", true));
    }

    @Test
    public void testGetPropertyDescriptor8() {
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.getPropertyDescriptor(null, null, true));
    }

    @Test
    public void testGetPropertyDescriptor9() {
        assertThrows(UnsupportedOperationException.class,
                () -> BeanUtil.getPropertyDescriptor(Object.class, ".class", true));
    }

    @Test
    public void testHasProperty() {
        assertTrue(BeanUtil.hasProperty(B.class, "val"));
        assertFalse(BeanUtil.hasProperty(B.class, "blaBla"));
        assertFalse(BeanUtil.hasProperty(Object.class, "Property Name"));
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.hasProperty(null, "Property Name"));
        assertTrue(BeanUtil.hasProperty(Object.class, "class"));
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.hasProperty(null, null));
        assertFalse(BeanUtil.hasProperty(Object.class, ".class"));
    }

    @Test
    public void testHasWriteableProperty() {
        assertFalse(BeanUtil.hasWriteableProperty(Object.class, "Property Name"));
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.hasWriteableProperty(null, "Property Name"));
        assertFalse(BeanUtil.hasWriteableProperty(Object.class, "class"));
        assertThrows(IllegalArgumentException.class, () -> BeanUtil.hasWriteableProperty(null, null));
        assertFalse(BeanUtil.hasWriteableProperty(Object.class, ".class"));
    }

    @Test
    public void testReadMethodName() {
        assertEquals("getVal", BeanUtil.readMethodName("val", int.class));
        assertEquals("isValid", BeanUtil.readMethodName("valid", boolean.class));
        assertEquals("isValid", BeanUtil.readMethodName("valid", Boolean.class));
        assertEquals("getProperty Name", BeanUtil.readMethodName("Property Name", Object.class));
        assertEquals("isProperty Name", BeanUtil.readMethodName("Property Name", Boolean.class));
    }

    @Test
    public void testWriteMethodName() {
        assertEquals("setVal", BeanUtil.writeMethodName("val"));
        assertEquals("setValid", BeanUtil.writeMethodName("valid"));
        assertEquals("setProperty Name", BeanUtil.writeMethodName("Property Name"));
    }

    @Test
    public void testGetPropertyDescriptors() {
        PropertyDescriptor[] descriptors = BeanUtil.getPropertyDescriptors(B.class);
        assertEquals(2, descriptors.length);
    }

    @Test
    public void testGetPropertyDescriptors2() {
        assertEquals(1, BeanUtil.getPropertyDescriptors(Object.class).length);
    }

    @Test
    public void testGetPropertyValues() {
        assertEquals(4, BeanUtil.getPropertyValues("bean", true).size());
        assertEquals(3, BeanUtil.getPropertyValues("bean", false).size());
    }

    @Test
    public void testGetReadablePropertyValues() {
        assertEquals(4, BeanUtil.getReadablePropertyValues("bean", true).size());
        assertEquals(1, BeanUtil.getReadablePropertyValues(4, true).size());
        assertEquals(3, BeanUtil.getReadablePropertyValues("bean", false).size());
    }

    @Test
    public void testGetRWPropertyValues() {
        assertTrue(BeanUtil.getRWPropertyValues("bean", true).isEmpty());
        assertTrue(BeanUtil.getRWPropertyValues(4, true).isEmpty());
    }

    @Test
    public void testGetPropertyValue() {
        P p = new P();
        assertEquals(p.getVal(), BeanUtil.getPropertyValue(p, "val"));
    }

    @Test
    public void testGetPropertyValue10() {
        assertNull(BeanUtil.getPropertyValue("bean", "Property Name", false));
    }

    @Test
    public void testGetPropertyValue2() {
        assertThrows(ConfigurationError.class, () -> BeanUtil.getPropertyValue("bean", "Property Name"));
    }

    @Test
    public void testGetPropertyValue4() {
        assertEquals(4, ((byte[]) BeanUtil.getPropertyValue("bean", "bytes")).length);
    }

    @Test
    public void testGetPropertyValue5() {
        assertThrows(ConfigurationError.class, () -> BeanUtil.getPropertyValue("bean", ".class"));
    }

    @Test
    public void testGetPropertyValue6() {
        assertThrows(ConfigurationError.class, () -> BeanUtil.getPropertyValue("bean", "Property Name", true));
    }

    @Test
    public void testGetPropertyValue7() {
        assertFalse((Boolean) BeanUtil.getPropertyValue("bean", "blank", true));
    }

    @Test
    public void testGetPropertyValue8() {
        assertEquals(4, ((byte[]) BeanUtil.getPropertyValue("bean", "bytes", true)).length);
    }

    @Test
    public void testGetPropertyValue9() {
        assertThrows(ConfigurationError.class, () -> BeanUtil.getPropertyValue("bean", ".class", true));
    }

    @Test
    public void testSetPropertyValue() {
        P p = new P();
        BeanUtil.setPropertyValue(p, "val", 2);
        assertEquals(2, p.getVal());
    }

    @Test
    public void testExtractProperties() {
        assertTrue(BeanUtil.<Object, Object>extractProperties(new ArrayList<Object>(), "Property Name").isEmpty());
        assertThrows(ConfigurationError.class,
                () -> BeanUtil.<Object, Object>extractProperties(new Object[]{"beans"}, "Property Name", Object.class));
        assertEquals(0, BeanUtil.<Object, Object>extractProperties(new Object[]{}, "Property Name", Object.class).length);
        assertEquals(1, BeanUtil.<Object, Object>extractProperties(new Object[]{"beans"}, "blank", Object.class).length);
        assertEquals(1, BeanUtil.<Object, Object>extractProperties(new Object[]{"beans"}, "bytes", Object.class).length);
        assertThrows(ConfigurationError.class,
                () -> BeanUtil.<Object, Object>extractProperties(new Object[]{"beans"}, ".class", Object.class));
    }

    @Test
    public void testExtractProperties2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertThrows(ConfigurationError.class,
                () -> BeanUtil.<Object, Object>extractProperties(objectList, "Property Name"));
    }

    @Test
    public void testExtractProperties3() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        objectList.add(null);
        objectList.add("e");
        assertThrows(ConfigurationError.class,
                () -> BeanUtil.<Object, Object>extractProperties(objectList, "Property Name"));
    }

    @Test
    public void testExtractProperties4() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertEquals(1, BeanUtil.<Object, Object>extractProperties(objectList, "blank").size());
    }

    @Test
    public void testExtractProperties5() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertEquals(1, BeanUtil.<Object, Object>extractProperties(objectList, "bytes").size());
    }

    @Test
    public void testExtractProperties6() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertThrows(ConfigurationError.class, () -> BeanUtil.<Object, Object>extractProperties(objectList, ".class"));
    }

    // class tests -----------------------------------------------------------------------------------------------------

    @Test
    public void testPrintClassInfo() {
        BeanUtil.printClassInfo(B.class, new PrintWriter(System.out));
    }

    @Test
    public void testCheckJavaBean() {
        BeanUtil.checkJavaBean(B.class);
    }

    @Test
    public void testDeprecated() {
        assertFalse(BeanUtil.deprecated(Object.class));
        assertTrue(BeanUtil.deprecated(Dep.class));
        assertFalse(BeanUtil.deprecated(Object.class));
    }

    @Test
    public void testGetClasses() {
        assertTrue(BeanUtil.getClasses("java.text").isEmpty());
        assertTrue(BeanUtil.getClasses("Package Name").isEmpty());
    }

    @Test
    public void testGetFieldValue() {
        assertNull(BeanUtil.getFieldValue("target", "Name", false));
    }

    @Test
    public void testFindMethodsByAnnotation() {
        Class<?> owner = Object.class;
        assertEquals(0, BeanUtil.findMethodsByAnnotation(owner, Annotation.class).length);
    }

    @Test
    public void testClassName() {
        assertEquals("java.lang.String", BeanUtil.<Object>className("o"));
        assertNull(BeanUtil.<Object>className(null));
    }

    @Test
    public void testSimpleClassName() {
        assertEquals("String", BeanUtil.<Object>simpleClassName("o"));
        assertNull(BeanUtil.<Object>simpleClassName(null));
    }

    @Test
    public void testEqualsIgnoreType() {
        assertTrue(BeanUtil.equalsIgnoreType("1", 1));
        assertTrue(BeanUtil.equalsIgnoreType(1., 1));
        assertFalse(BeanUtil.equalsIgnoreType(1., 2));
        assertFalse(BeanUtil.equalsIgnoreType("o1", "o2"));
        assertFalse(BeanUtil.equalsIgnoreType(null, "o2"));
        assertTrue(BeanUtil.equalsIgnoreType("o2", "o2"));
        assertFalse(BeanUtil.equalsIgnoreType("o1", null));
        assertTrue(BeanUtil.equalsIgnoreType(0, new AtomicInteger()));
        assertFalse(BeanUtil.equalsIgnoreType(0, new AtomicInteger(42)));
        assertFalse(BeanUtil.equalsIgnoreType(new Base64ToByteArrayConverter(), 0));
        assertFalse(BeanUtil.equalsIgnoreType("O1", new Base64ToByteArrayConverter()));
    }

    @Test
    public void testEqualsIgnoreType2() {
        AtomicInteger o1 = new AtomicInteger();
        assertFalse(BeanUtil.equalsIgnoreType(o1, new AtomicInteger()));
    }

    @Test
    public void testNullSafeHashCode() {
        assertEquals(-1023368385, BeanUtil.nullSafeHashCode("object"));
        assertEquals(0, BeanUtil.nullSafeHashCode(null));
    }

    @Test
    public void testExistsClass() {
        assertFalse(BeanUtil.existsClass("Class Name"));
        assertTrue(BeanUtil.existsClass("com.rapiddweller.common.BeanUtil"));
    }

    @Test
    public void testToString() {
        assertEquals("java.lang.String[blank=false, bytes=YmVhbg==, empty=false]", BeanUtil.toString("bean"));
        assertNull(BeanUtil.toString(null));
        assertEquals("java.lang.Character", BeanUtil.toString('\u0000'));
        assertEquals("String[blank=false, bytes=YmVhbg==, empty=false]", BeanUtil.toString("bean", true));
        assertNull(BeanUtil.toString(null, true));
        assertEquals("Character", BeanUtil.toString('\u0000', true));
        assertEquals("java.lang.String[blank=false, bytes=YmVhbg==, empty=false]", BeanUtil.toString("bean", false));
    }

    @Test
    public void testToString3() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        BeanUtil.toString(htmlDocumentImpl);
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
        assertTrue(htmlDocumentImpl.getDomConfig() instanceof org.apache.xerces.dom.DOMConfigurationImpl);
    }

    @Test
    public void testToString5() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        htmlDocumentImpl.setDocumentURI("class");
        BeanUtil.toString(htmlDocumentImpl);
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
        assertTrue(htmlDocumentImpl.getDomConfig() instanceof org.apache.xerces.dom.DOMConfigurationImpl);
    }

    @Test
    public void testToString6() {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(
                "Date[date=1, day=4, hours=0, minutes=0, month=0, seconds=0, time=-3600000, timezoneOffset=-60," + " year=70]",
                BeanUtil.toString(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()), true));
    }

    @Test
    public void testToString7() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        BeanUtil.toString(htmlDocumentImpl, true);
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
        assertTrue(htmlDocumentImpl.getDomConfig() instanceof org.apache.xerces.dom.DOMConfigurationImpl);
    }

    @Test
    public void testToString9() {
        HTMLDocumentImpl htmlDocumentImpl = new HTMLDocumentImpl();
        htmlDocumentImpl.setDocumentURI("class");
        BeanUtil.toString(htmlDocumentImpl, true);
        assertEquals("", htmlDocumentImpl.getTitle());
        assertNull(htmlDocumentImpl.getParentNode());
        assertEquals(1, htmlDocumentImpl.getLength());
        assertTrue(htmlDocumentImpl.hasChildNodes());
        assertTrue(htmlDocumentImpl.getDomConfig() instanceof org.apache.xerces.dom.DOMConfigurationImpl);
    }

    // Test classes ----------------------------------------------------------------------------------------------------

    public interface I {
    }

    public static class A implements I {
        P b = new P();

        public P getP() {
            return b;
        }

    }

    public static class B<E extends Collection<Integer>> {

        public int val;
        public static String stat = "x";
        public E gen;

        public B() {
            this(1);
        }

        public B(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public static String getStat() {
            return stat;
        }

        public static void setStat(String stat) {
            B.stat = stat;
        }
    }

    public static class C extends B<Set<Integer>> {
    }

    @Deprecated
    public static class Dep {

    }

    public static class P {

        public int val;

        public P() {
            this(1);
        }

        public P(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    public static class V {
        public int varargs1(int... args) {
            return (args.length > 0 ? ArrayUtil.lastElementOf(args) : 0);
        }

        public int varargs2(int x) {
            return -1;
        }

        public int varargs2(int x, int... args) {
            return (args.length > 0 ? ArrayUtil.lastElementOf(args) : 0);
        }

        public Object varargs3(String s, int... args) {
            return (args.length > 0 ? ArrayUtil.lastElementOf(args) : s);
        }
    }

}
