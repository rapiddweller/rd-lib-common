/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

package com.rapiddweller.common.io;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the {@link HttpClient}.<br><br>
 * Created: 20.05.2021 21:59:57
 * @since 2.14.0
 * @author Volker Bergmann
 */

public class HttpClientTest {
	
	@Test
	public void test() throws Exception {
		String content = new HttpClient().getContentOf("http://www.google.com");
		System.out.println(content);
		assertTrue(content.startsWith("<!doctype"));
	}
	
}
