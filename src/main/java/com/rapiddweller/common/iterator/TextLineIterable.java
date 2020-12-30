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
package com.rapiddweller.common.iterator;

import com.rapiddweller.common.HeavyweightIterator;
import com.rapiddweller.common.HeavyweightTypedIterable;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.ReaderLineIterator;

import java.io.IOException;

/**
 * Creates Iterator object that iterate through the lines of a text file from the specified uri.
 * Created: 27.08.2007 19:13:40
 * @author Volker Bergmann
 */
public class TextLineIterable implements HeavyweightTypedIterable<String> {

    private final String uri;

    public TextLineIterable(String uri) {
        this.uri = uri;
    }

    @Override
	public Class<String> getType() {
        return String.class;
    }

    @Override
	public HeavyweightIterator<String> iterator() {
        try {
            return new ReaderLineIterator(IOUtil.getReaderForURI(uri));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create an Iterator for URI '" + uri + "'", e);
        }
    }
    
}
