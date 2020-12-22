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

import com.rapiddweller.commons.UpdateFailedException;
import com.rapiddweller.commons.bean.TypedPropertyMutator;
import org.junit.Test;

/**
 * Tests the {@link TypedPropertyMutator}.
 * Created: 20.02.2007 08:52:49
 * @author Volker Bergmann
 */
public class TypedPropertyMutatorTest {

	@Test
    public void testLocalProperty() throws UpdateFailedException {
        TypedPropertyMutator aNameMutator = new TypedPropertyMutator(ABean.class, "name", true, false);
        ABean a = new ABean();
        aNameMutator.setValue(a, "aName");
        assertEquals("aName", a.name);
        aNameMutator.setValue(a, null);
        assertEquals(null, a.name);
    }

}
