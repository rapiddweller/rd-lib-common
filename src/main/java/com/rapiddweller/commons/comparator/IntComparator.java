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
 * Compares values of type Integer or int.
 * Created: 05.04.2005 19:25:25
 * @author Volker Bergmann
 */
public class IntComparator implements Comparator<Integer> {

    @Override
	public int compare(Integer i1, Integer i2) {
        return (i1).compareTo(i2);
    }

    public static int compare(int i1, int i2) {
        return (i1 < i2 ? -1 : (i1 > i2 ? 1 : 0));
    }

}
