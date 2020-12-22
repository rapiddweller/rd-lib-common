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

import java.io.File;
import java.io.FilenameFilter;

import com.rapiddweller.commons.Filter;

/**
 * Wraps a {@link FilenameFilter} with a {@link FileFilter}.
 * Created: 02.04.2010 15:09:23
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class FileByNameFilter implements FileFilter {
	
	private Filter<String> pathFilter;
	
	public FileByNameFilter(Filter<String> realFilter) {
	    this.pathFilter = realFilter;
    }

	@Override
	public boolean accept(File file) {
	    return (pathFilter == null || pathFilter.accept(file.getAbsolutePath()));
    }

}
