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

import java.time.LocalDate;
import java.util.Collection;

/**
 * Provides utility methods for implementors of {@link LocalDatedUtil}.<br><br>
 * Created: 28.07.2019 19:52:33
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class LocalDatedUtil {

	private LocalDatedUtil() { }

	public static <E extends LocalDated> E soonestFutureElement(Collection<E> elements) {
		E result = null;
		LocalDate now = LocalDate.now();
		for (E element : elements) {
			LocalDate date = element.getDate();
			if (date != null && date.isAfter(now)) {
				if (result == null || date.isBefore(result.getDate()))
					result = element;
			}
		}
		return result;
	}

}
