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

import java.io.File;

/**
 * FileFilter implementation that accepts only directories.
 * Created: 07.05.2007 23:57:46
 * @author Volker Bergmann
 */
public class DirectoryFileFilter implements FileFilter {
	
	private static final DirectoryFileFilter INSTANCE = new DirectoryFileFilter();
	
    @Override
	public boolean accept(File file) {
        return file.isDirectory();
    }

	public static FileFilter instance() {
	    return INSTANCE;
    }

}
