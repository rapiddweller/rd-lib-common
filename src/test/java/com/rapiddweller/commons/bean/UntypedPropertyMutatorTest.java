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
import static org.junit.Assert.fail;

import com.rapiddweller.commons.UpdateFailedException;
import com.rapiddweller.commons.bean.UntypedPropertyMutator;
import org.junit.Test;

/**
 * Tests the {@link UntypedPropertyMutator}.
 * Created: 20.02.2007 08:52:49
 * @since 0.2
 * @author Volker Bergmann
 */
public class UntypedPropertyMutatorTest {

	@Test
    public void testLocalProperty() throws UpdateFailedException {
        UntypedPropertyMutator aNameMutator = new UntypedPropertyMutator("name", true, false);
        ABean a = new ABean();
        aNameMutator.setValue(a, "aName");
        assertEquals("aName", a.name);
        aNameMutator.setValue(a, null);
        assertEquals(null, a.name);
    }
    
	@Test
    public void testNonStrict() {
	    UntypedPropertyMutator mutator = new UntypedPropertyMutator("bla", false, true);
		mutator.setValue(null, null);
		mutator.setValue(new ABean(), null);
	    UntypedPropertyMutator readOnly = new UntypedPropertyMutator("readOnly", false, true);
	    readOnly.setValue(new ABean(), "bla");
    }

	@Test
    public void testStrictSetOnNull() {
    	try {
	    	UntypedPropertyMutator mutator = new UntypedPropertyMutator("bla", true, false);
	    	mutator.setValue(null, null);
	    	fail(UpdateFailedException.class.getSimpleName() + " expected");
    	} catch (UpdateFailedException e) {
    		// expected
    	}
    }

	@Test
    public void testStrictMissingProperty() {
    	try {
	    	UntypedPropertyMutator mutator = new UntypedPropertyMutator("bla", true, false);
	    	mutator.setValue(new ABean(), null);
	    	fail(UpdateFailedException.class.getSimpleName() + " expected");
    	} catch (UpdateFailedException e) {
    		// expected
    	}
    }

	@Test
    public void testStrictReadOnlyProperty() {
    	try {
	    	UntypedPropertyMutator mutator = new UntypedPropertyMutator("readOnly", true, false);
	    	mutator.setValue(new ABean(), null);
	    	fail(UpdateFailedException.class.getSimpleName() + " expected");
    	} catch (UpdateFailedException e) {
    		// expected
    	}
    }
	
}
