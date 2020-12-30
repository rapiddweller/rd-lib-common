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
package com.rapiddweller.common.converter;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConversionException;

/**
 * Interprets a String as class name and returns the corresponding class object.
 * Created: 05.08.2007 06:38:38
 * @author Volker Bergmann
 */
@SuppressWarnings("rawtypes")
public class String2ClassConverter extends ThreadSafeConverter<String, Class> {

    public String2ClassConverter() {
        super(String.class, Class.class);
    }

    @Override
	public Class convert(String className) throws ConversionException {
        return BeanUtil.forName(className);
    }
    
}
