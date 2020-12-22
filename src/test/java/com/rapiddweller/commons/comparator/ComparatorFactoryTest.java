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
package com.rapiddweller.commons.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Comparator;

import com.rapiddweller.commons.Person;

import org.junit.Test;

/**
 * Tests the {@link ComparatorFactory}.
 * Created: 16.03.2008 15:13:56
 * @author Volker Bergmann
 */
public class ComparatorFactoryTest {
    
	@Test
    public void testStringCollator() {
        Comparator<String> stringComparator = ComparatorFactory.getComparator(String.class);
        assertNotNull(stringComparator);
        assertEquals(-1, stringComparator.compare("1", "2"));
    }
    
	@Test
    public void testPersonComparator() {
        Comparator<Person> personComparator = ComparatorFactory.getComparator(Person.class);
        assertNotNull(personComparator);
        Person alice = new Person("Alice", 23);
        Person bob   = new Person("Bob",   34);
        assertEquals(-1, personComparator.compare(alice, bob));
    }
    
    public static final class MyComparator implements Comparator<Person> {

        @Override
		public int compare(Person p1, Person p2) {
            return IntComparator.compare(p1.getAge(), p2.getAge());
        }
    }

}
