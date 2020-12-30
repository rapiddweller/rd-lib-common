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

import com.rapiddweller.common.Base64Codec;
import com.rapiddweller.common.ConversionException;

/**
 * Converts base64-encoded Strings to <code>byte</code> arrays.
 * Created: 26.02.2010 08:23:49
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class Base64ToByteArrayConverter extends ThreadSafeConverter<String, byte[]> {

    public Base64ToByteArrayConverter() {
        super(String.class, byte[].class);
    }

    @Override
	public byte[] convert(String target) throws ConversionException {
        return Base64Codec.decode(target);
    }

}
