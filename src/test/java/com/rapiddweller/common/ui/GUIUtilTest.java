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
package com.rapiddweller.common.ui;

import java.io.File;

import com.rapiddweller.common.ui.GUIUtil;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link GUIUtil}.
 * Created: 06.02.2014 13:35:19
 * @since 0.5.26
 * @author Volker Bergmann
 */

public class GUIUtilTest {
	@Ignore //because fails in docker
	@Test
	public void testTakeScreenshot() throws Exception {
		File file = File.createTempFile("screenshot.png", ".png");
		try {
			GUIUtil.takeScreenshot(file.getAbsolutePath(), "png");
			System.out.println(file);
		} finally {
			assertTrue(file.delete());
		}
	}

}
