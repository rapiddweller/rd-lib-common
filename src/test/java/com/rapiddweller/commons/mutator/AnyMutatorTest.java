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
package com.rapiddweller.commons.mutator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.context.DefaultContext;
import com.rapiddweller.commons.mutator.AnyMutator;

import org.junit.Test;

/**
 * Tests the {@link AnyMutator}.
 * Created: 01.02.2008 08:06:11
 * @author Volker Bergmann
 */
public class AnyMutatorTest {

	@Test
    public void testBeanProperty() {
        A a = new A();
        assertEquals(1, a.x);
        AnyMutator.setValue(a, "x", 2);
        assertEquals(2, a.x);
    }
    
	@Test
    public void testAttribute() {
        C c = new C();
        assertEquals(1, c.x);
        AnyMutator.setValue(c, "x", 2);
        assertEquals(2, c.x);
    }
    
	@Test
    public void testBeanPropertyGraph() {
        A a = new A();
        a.setB(new B());
        // check preconditions
        assertEquals("alpha", a.b.y);
        assertEquals(-1, a.b.z);
        // set b.y and check it
        AnyMutator.setValue(a, "b.y", "bravo");
        assertEquals("bravo", a.b.y);
        // set b.z and check the whole result object
        AnyMutator.setValue(a, "b.z", 2);
        assertEquals(2, a.b.z);
        assertEquals("bravo", a.b.y);
    }
    
	@Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        assertNull(map.get("alpha"));
        AnyMutator.setValue(map, "alpha", "bravo");
        assertEquals("bravo", map.get("alpha"));
    }
    
	@Test
    public void testContext() {
        Context context = new DefaultContext();
        assertNull(context.get("alpha"));
        AnyMutator.setValue(context, "alpha", "bravo");
        assertEquals("bravo", context.get("alpha"));
    }
    
	@Test
    public void testGraph() {
        Context context = new DefaultContext();
        Map<String, Object> map = new HashMap<>();
        context.set("map", map);
        A a = new A();
        map.put("a", a);
        B b = new B();
        a.setB(b);
        AnyMutator.setValue(context, "map.a.b.y", "it works!");
        assertEquals("it works!", b.y);
    }
    
    // tested classes --------------------------------------------------------------------------------------------------
    
    public static class A {
        
        public int x = 1;
        
        public void setX(int x) { 
            this.x = x; 
        }
        
        public B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }
    
    public static class B {
        
        public String y = "alpha";
        public int z = -1;
        
        public void setY(String y) {
            this.y = y;
        }

		public void setZ(int z) {
			this.z = z;
		}
    }
    
    public static class C {
        public int x = 1;
    }
    
}
