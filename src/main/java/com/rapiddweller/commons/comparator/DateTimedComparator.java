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

package com.rapiddweller.commons.comparator;

import java.util.Comparator;

import com.rapiddweller.commons.DateTimed;

/**
 * Comparator for objects of classes that implement the {@link DateTimed} interface.<br><br>
 * Created: 09.11.2019 12:54:09
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class DateTimedComparator implements Comparator<DateTimed> {

	@Override
	public int compare(DateTimed dt1, DateTimed dt2) {
		return dt1.getDateTime().compareTo(dt2.getDateTime());
	}

}
