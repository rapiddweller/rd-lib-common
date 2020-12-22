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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * {@link PrintWriter} implementation which writes to a {@link File} 
 * and provides the file's identity.
 * Created: 14.06.2011 09:10:05
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class FilePrintWriter extends PrintWriter {

	protected File file;
	protected String encoding;
	
	public FilePrintWriter(File file) throws FileNotFoundException {
		super(file);
		this.file = file;
	}

	public FilePrintWriter(File file, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, encoding);
		this.file = file;
		this.encoding = encoding;
	}

	public File getFile() {
		return file;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
}
