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

import com.rapiddweller.commons.FileUtil;

import java.io.File;

/**
 * Matches files by checking their suffix to be one of the specified values.
 * Created: 23.04.2007 07:59:34
 * @author Volker Bergmann
 */
public class MultiFileSuffixFilter implements FileFilter {

    private final String[] suffixes;
    private final boolean caseSensitive;

    public MultiFileSuffixFilter(boolean caseSensitive, String ... suffixes) {
        this.suffixes = suffixes;
        this.caseSensitive = caseSensitive;
    }

    @Override
	public boolean accept(File file) {
        for (String suffix : suffixes)
            if (FileUtil.hasSuffix(file, suffix, caseSensitive))
                return true;
        return false;
    }

}
