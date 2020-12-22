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

/**
 * Compares values of type Double or double.
 * Created: 05.04.2005 19:25:25
 * @author Volker Bergmann
 */
public class DoubleComparator implements Comparator<Double> {

    @Override
	public int compare(Double d1, Double d2) {
        return d1.compareTo(d2);
    }

    public static int compare(double d1, double d2) {
    	if (Double.isNaN(d1))
    		d1 = 0;
    	if (Double.isNaN(d2))
    		d2 = 0;
    	if (d1 > d2)
            return 1;
        else if (d1 < d2)
            return -1;
        return 0;
    }

}
