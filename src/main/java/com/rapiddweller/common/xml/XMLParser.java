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
package com.rapiddweller.common.xml;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;

/**
 * XML Parser which is able to parse strings, files and content provides from URIs.
 * Created: 04.06.2012 13:17:53
 * @since 0.5.16
 * @author Volker Bergmann
 */
public class XMLParser {

	private final ClassLoader jaxpClassLoader;
	
	public XMLParser() {
		this(null);
	}
	
	public XMLParser(ClassLoader jaxpClassLoader) {
		this.jaxpClassLoader = (jaxpClassLoader != null ? jaxpClassLoader : getClass().getClassLoader());
	}
	
	public Document parse(File file) throws IOException {
		return parse(file.getCanonicalPath());
	}
	
	public Document parse(String uri) throws IOException {
		return XMLUtil.parse(uri, true, null, null, jaxpClassLoader);
	}
	
	public Document parseString(String text) {
		return XMLUtil.parseString(text, null, jaxpClassLoader);
	}
	
}
