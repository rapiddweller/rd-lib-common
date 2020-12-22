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
package com.rapiddweller.commons.ui;

/**
 * Common abstraction for all classes that can provide information output to a human user.
 * Created at 21.12.2008 11:33:26
 * @since 0.4.7
 * @author Volker Bergmann
 */

public abstract class InfoPrinter {
	
	public void printLines(String... lines) {
		printLines(null, lines);
	}
	
	public abstract void printLines(Object owner, String... lines);
}
