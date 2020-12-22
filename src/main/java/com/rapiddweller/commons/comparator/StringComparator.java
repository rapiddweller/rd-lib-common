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

import java.text.Collator;
import java.util.Locale;
import java.util.Comparator;

/**
 * Compares two Strings with a Collator. If no Collator is specified, 
 * it uses the default Collator of the default Locale.
 * Created: 03.06.2005 16:13:15
 * @since 0.1
 * @author Volker Bergmann
 */
public class StringComparator implements Comparator<String> {

    private Collator collator;

    public StringComparator() {
        this(Locale.getDefault());
    }

    public StringComparator(Locale locale) {
        this.collator = Collator.getInstance(locale);
    }

    @Override
	public int compare(String o1, String o2) {
        return collator.compare(o1, o2);
    }

}
