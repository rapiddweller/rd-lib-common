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
package com.rapiddweller.common.file;

import java.text.Format;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.io.File;

/**
 * Formats file names as local names or absolute path.
 * Created: 13.05.2007 07:41:51
 * @author Volker Bergmann
 */
public class FilenameFormat extends Format {

	private static final long serialVersionUID = 8865264142496144195L;

	private boolean fullPathUsed;

    public FilenameFormat() {
        this(true);
    }

    public FilenameFormat(boolean fullPathUsed) {
        this.fullPathUsed = fullPathUsed;
    }

    public boolean isFullPathUsed() {
        return fullPathUsed;
    }

    public void setFullPathUsed(boolean fullPathUsed) {
        this.fullPathUsed = fullPathUsed;
    }

    @Override
    public StringBuffer format(Object fileObject, StringBuffer toAppendTo, FieldPosition pos) {
        File file = (File) fileObject;
        String filename;
        if (fullPathUsed)
            filename = file.getAbsolutePath();
        else
            filename = file.getName();
        return toAppendTo.append(filename);
    }

    @Override
    public Object parseObject(String filename, ParsePosition pos) {
        return new File(filename);
    }
}
