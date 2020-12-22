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
package com.rapiddweller.commons.tag;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the TagUtil class.
 * Created: 16.11.2013 07:16:00
 * @since 0.5.25
 * @author Volker Bergmann
 */

public class TagUtilTest {
	
	@Test
	public void testHasTag() {
		TaggedBean bean = new TaggedBean("Alpha", "Beta");
		
		// test perfect match
		assertTrue(TagUtil.hasTag( "Alpha", bean, false, false));
		assertTrue(TagUtil.hasTag( "Beta",  bean, false, false));
		assertFalse(TagUtil.hasTag("Gamma", bean, false, false));
		
		// test partial match
		assertTrue( TagUtil.hasTag("Alp", bean, false, true));
		assertTrue( TagUtil.hasTag("Bet", bean, false, true));
		assertFalse(TagUtil.hasTag("Gam", bean, false, true));
		
		// test ignoreCase match
		assertTrue(TagUtil.hasTag("alpha", bean, true, false));
		assertTrue(TagUtil.hasTag("beta", bean, true, false));
		assertFalse(TagUtil.hasTag("gamma", bean, true, false));
		
		// test partial ignoreCase match
		assertTrue(TagUtil.hasTag("alp", bean, true, true));
		assertTrue(TagUtil.hasTag("bet", bean, true, true));
		assertFalse(TagUtil.hasTag("gam", bean, true, true));
	}
	
	static class TaggedBean extends AbstractTagged {

		public TaggedBean(String... tags) {
			for (String tag : tags)
				addTag(tag);
		}
		
	}
	
}
