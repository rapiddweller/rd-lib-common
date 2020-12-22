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
package com.rapiddweller.commons.version;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date-related implementation of the {@link VersionNumberComponent} interface,
 * which exhibits the same ordinal behavior as a snapshot version.
 * Created: 12.02.2010 09:50:06
 * @since 0.6.0
 * @author Volker Bergmann
 */
public class DateVersionNumberComponent extends VersionNumberComponent {

	private static final long serialVersionUID = 6057638663166486859L;
	
	private final String dateString;
	private final Date date;

	public DateVersionNumberComponent(String dateString) throws ParseException {
		this.dateString = dateString;
		this.date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
	}

	@Override
	public int compareTo(VersionNumberComponent that) {
		if (that instanceof DateVersionNumberComponent)
			return this.date.compareTo(((DateVersionNumberComponent) that).date);
		else
			return StringVersionNumberComponent.SNAPSHOT.compareTo(that);
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return dateString;
	}

	@Override
	public int hashCode() {
		return date.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		DateVersionNumberComponent that = (DateVersionNumberComponent) obj;
		return this.date.equals(that.date);
	}
	
}
