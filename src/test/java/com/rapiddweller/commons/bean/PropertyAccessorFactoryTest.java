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

import static org.junit.Assert.assertEquals;

import com.rapiddweller.commons.bean.PropertyAccessorFactory;

import org.junit.Test;

/**
 * Tests the {@link PropertyAccessorFactory}.
 * Created: 21.07.2007 15:03:40
 * @author Volker Bergmann
 */
public class PropertyAccessorFactoryTest {

	@SuppressWarnings("unchecked")
    @Test
    public void test() {
        ABean a = new ABean();
        a.name = "aName";
        a.b = new BBean();
        a.b.name = "bName";
        a.b.c = new CBean();
        a.b.c.name = "cName";
        // test simple properties
        assertEquals("aName", PropertyAccessorFactory.getAccessor("name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("doesntExist", false).getValue(null));
        assertEquals("aName", PropertyAccessorFactory.getAccessor(ABean.class, "name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "doesntExist", false).getValue(null));
        // test navigated properties
        assertEquals("bName", PropertyAccessorFactory.getAccessor("b.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.doesntExist", false).getValue(null));
        assertEquals("bName", PropertyAccessorFactory.getAccessor(ABean.class, "b.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.doesntExist", false).getValue(null));
        // test twofold navigated properties
        assertEquals("cName", PropertyAccessorFactory.getAccessor("b.c.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.c.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor("b.c.doesntExist", false).getValue(null));
        assertEquals("cName", PropertyAccessorFactory.getAccessor(ABean.class, "b.c.name").getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.c.doesntExist", false).getValue(a));
        assertEquals(null, PropertyAccessorFactory.getAccessor(ABean.class, "b.c.doesntExist", false).getValue(null));
    }
    
}
