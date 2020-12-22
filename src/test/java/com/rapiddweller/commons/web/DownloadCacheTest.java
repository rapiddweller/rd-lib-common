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
package com.rapiddweller.commons.web;

import java.net.URL;

import com.rapiddweller.commons.DatabeneTestUtil;
import org.junit.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tests the {@link DownloadCache}.
 * Created: 15.08.2010 10:34:15
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class DownloadCacheTest {
	
	Logger LOGGER = LogManager.getLogger(DownloadCacheTest.class);
	
	@Test
	public void test() throws Exception {
		if (!DatabeneTestUtil.isOnline()) {
			LOGGER.warn("Skipping " + getClass().getName() + " since we're offline");
			return;
		}
		DownloadCache cache = new DownloadCache();
		cache.get(new URL("http://bergmann-it.de"));
		cache.get(new URL("http://bergmann-it.de"));
	}

}
