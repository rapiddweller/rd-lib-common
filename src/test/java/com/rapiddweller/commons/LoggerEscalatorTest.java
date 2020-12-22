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
package com.rapiddweller.commons;

import org.junit.Test;

/**
 * Tests the {@link LoggerEscalator}.
 * Created at 02.05.2008 15:16:22
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class LoggerEscalatorTest {
	
	@Test
	public void test() {
		LoggerEscalator escalator = new LoggerEscalator();
		escalator.escalate("mess", this, "cause");
		// identical escalation should be ignored 
		escalator.escalate("mess", this, "cause");
		// escalation that differs only in cause should be ignored 
		escalator.escalate("mess", this, "other cause");
		// escalation with other message is added
		escalator.escalate("mess2", this, "cause");
	}
	
}
