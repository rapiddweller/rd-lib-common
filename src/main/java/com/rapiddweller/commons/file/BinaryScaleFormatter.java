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
package com.rapiddweller.commons.file;

import java.text.DecimalFormat;

import com.rapiddweller.commons.BinaryScale;

/**
 * Formats a number in a human-friendly manner, e.g. a file size in megabytes.
 * Created: 06.03.2011 15:12:55
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class BinaryScaleFormatter {
	
	private final BinaryScale scale;
	private final DecimalFormat numberFormat;
	private final String unit;
	
	public BinaryScaleFormatter(BinaryScale scale, String unit) {
		this.scale = scale;
		this.unit = unit;
		this.numberFormat = new DecimalFormat();
		setGroupingUsed(true);
	}
	
	public void setGroupingUsed(boolean groupingUsed) {
		this.numberFormat.setGroupingUsed(groupingUsed);
	}
	
	public String format(long number) { 
		if (scale != null)
			return applyScale(scale, number);
		else
			return applyAutoScale(number);
	}

	private String applyScale(BinaryScale scale, long number) {
		return numberFormat.format(Math.floor((number + scale.getFactor() - 1) / scale.getFactor())) + " " + scale.getDesignator() + unit;
	}

	private String applyAutoScale(long number) {
		if (number >= 10 * BinaryScale.TERA.getFactor())
			return applyScale(BinaryScale.TERA, number);
		else if (number >= 10 * BinaryScale.GIGA.getFactor())
			return applyScale(BinaryScale.GIGA, number);
		else if (number >= 10 * BinaryScale.MEGA.getFactor())
			return applyScale(BinaryScale.MEGA, number);
		else if (number >= 10 * BinaryScale.KILO.getFactor())
			return applyScale(BinaryScale.KILO, number);
		else 
			return applyScale(BinaryScale.NONE, number);
	}

}
