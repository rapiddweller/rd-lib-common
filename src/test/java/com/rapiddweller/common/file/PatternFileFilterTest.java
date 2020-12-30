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
package com.rapiddweller.common.file;

import static org.junit.Assert.*;

import java.io.File;

import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.IOUtil;
import org.junit.Test;

/**
 * Tests the {@link PatternFileFilter}.
 * Created: 26.02.2010 10:05:08
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class PatternFileFilterTest {
	
	File targetDir = new File("target");
	File targetFile = new File("target", getClass().getSimpleName() + ".txt");

	@Test
	public void testPattern() {
		assertTrue(new PatternFileFilter(null, true, true).accept(targetDir));
		assertTrue(new PatternFileFilter("t.*", true, true).accept(targetDir));
		assertFalse(new PatternFileFilter("x.*", true, true).accept(targetDir));
	}

	@Test
	public void testAcceptFolders() {
		assertTrue(new PatternFileFilter(null, false, true).accept(targetDir));
		assertFalse(new PatternFileFilter(null, true, false).accept(targetDir));
	}

	@Test
	public void testAcceptFiles() throws Exception {
		IOUtil.writeTextFile(targetFile.getAbsolutePath(), "test");
		try {
			assertFalse(new PatternFileFilter(null, false, true).accept(targetFile));
			assertTrue(new PatternFileFilter(null, true, false).accept(targetFile));
		} finally {
			FileUtil.deleteIfExists(targetFile);
		}
	}

}
