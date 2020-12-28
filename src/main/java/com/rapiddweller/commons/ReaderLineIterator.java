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
package com.rapiddweller.commons;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;

/**
 * Iterator implementation that returns text lines provided by a reader.
 * Created: 01.05.2007 08:06:46
 * @since 0.1
 * @author Volker Bergmann
 */
public class ReaderLineIterator implements HeavyweightIterator<String> {

    private BufferedReader reader;
    private final boolean skipEmptyLines;
    private String next;

    private int lineCount;

    public ReaderLineIterator(Reader reader) {
    	this(reader, false);
    }
    
    public ReaderLineIterator(Reader reader, boolean skipEmptyLines) {
        if (reader instanceof BufferedReader)
            this.reader = (BufferedReader) reader;
        else
            this.reader = new BufferedReader(reader);
        this.skipEmptyLines = skipEmptyLines;
        this.lineCount = 0;
        fetchNext();
    }

    @Override
	public void close() {
        if (reader != null) {
            IOUtil.close(reader);
            reader = null;
        }
    }

    public int lineCount() {
        return lineCount;
    }

    // Iterator interface ----------------------------------------------------------------------------------------------

    @Override
	public boolean hasNext() {
        return reader != null && next != null;
    }

    @Override
	public String next() {
        String result = next;
        fetchNext();
        return result;
    }

    @Override
	public void remove() {
        throw new UnsupportedOperationException("Not supported");
    }

    // helpers ---------------------------------------------------------------------------------------------------------

    private void fetchNext() {
        try {
            if (reader != null) {
            	do {
            		next = reader.readLine();
            	} while (skipEmptyLines && next != null && next.trim().length() == 0);
                if (next == null)
                    close();
                lineCount++;
            } else {
                next = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
