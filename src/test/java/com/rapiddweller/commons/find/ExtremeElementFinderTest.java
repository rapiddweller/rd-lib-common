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
package com.rapiddweller.commons.find;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import com.rapiddweller.commons.Element;
import com.rapiddweller.commons.visitor.CollectionElement;
import com.rapiddweller.commons.visitor.ExtremeElementFinder;

/**
 * Tests the {@link ExtremeElementFinder}.
 * Created: 04.02.2007 09:33:01
 * @author Volker Bergmann
 */
public class ExtremeElementFinderTest {

	@Test
    public void test() {
        Collection<String> collection = Arrays.asList("Alpha", "Bravo");
        Element<String> wrapper = new CollectionElement<>(collection);
        String min = ExtremeElementFinder.findMin(wrapper);
        assertEquals("Alpha", min);
        assertEquals("Bravo", ExtremeElementFinder.findMax(wrapper));
    }
	
}
