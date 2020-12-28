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

import java.text.Collator;
import java.util.Comparator;

/**
 * Compares to {@link Named} objects by their name.
 * Created: 12.08.2010 09:26:04
 * @since 0.5.4
 * @author Volker Bergmann
 */
public class NameComparator implements Comparator<Named> {
	
	private final Comparator<String> nameComparator;
	
	public NameComparator() {
		this.nameComparator = new NullSafeComparator<>(Collator.getInstance());
	}
	
	@Override
	public int compare(Named named1, Named named2) {
	    return nameComparator.compare(named1.getName(), named2.getName());
    }

}
