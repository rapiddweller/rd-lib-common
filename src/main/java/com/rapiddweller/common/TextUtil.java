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

import java.util.List;

import com.rapiddweller.common.converter.ToStringConverter;

/**
 * Provides text utilities.<br><br>
 * Created: 21.11.2019 11:53:38
 * @since 1.0.12
 * @author Volker Bergmann
 */

public class TextUtil {

	public static String formatList(List<?> list) {
		StringBuilder text = new StringBuilder();
		for (Object item : list)
			text.append(format(item)).append(SystemInfo.LF);
		return text.toString();
	}

	public static String formatTable(Object[][] table, char separator) {
		StringBuilder text = new StringBuilder();
		for (Object[] row : table) {
			for (int i = 0; i < row.length; i++) {
				text.append(format(row[i]));
				if (i < row.length - 1)
					text.append(separator);
			}
			text.append(SystemInfo.LF);
		}
		return text.toString();
	}

	private static String format(Object object) {
		if (object instanceof Number)
			return HF.format(((Number) object).doubleValue());
		else
			return ToStringConverter.convert(object, "");
	}

}
