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

package com.rapiddweller.common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formats values human-friendly.<br><br>
 * Created: 20.12.2017 08:58:50
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class UiFormatter {

	public static String format(Date date) {
		return DateFormat.getDateInstance().format(date);
	}
	
	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static String formatShort(Date date) {
		return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
	}

	public static String format(double d) {
		return new DecimalFormat("#,##0.00").format(d);
	}

	public static String formatPct(double d) {
		return new DecimalFormat("#,##0.0%").format(d);
	}
	
}
