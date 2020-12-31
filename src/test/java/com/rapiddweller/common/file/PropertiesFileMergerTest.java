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

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

import com.rapiddweller.common.collection.TreeBuilder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests the {@link PropertiesFileMerger}.
 * Created: 01.08.2013 10:38:32
 * @since 0.5.24
 * @author Volker Bergmann
 */

public class PropertiesFileMergerTest {

	private static final String NON_EXISTING_PROPERTIES_FILENAME = "non_existing.properties";
	private static final String JAR_PROPERTIES_FILENAME = "propsInJar.properties";
	private static final String FILE_PROPERTIES_FILENAME = "src/test/resources/com/rapiddweller/common/file/propsInFile.properties";
	private static final String MERGED_PROPERTIES_FILENAME = "target/merged.properties";
	private static final String FILE_XML_FILENAME = "src/test/resources/com/rapiddweller/common/xml/properties.xml";
	private static final String MERGED_SIMPLE_XML_FILENAME = "target/mergedSimple.xml";
	private static final URL JAR_URL;
	
	private static final String COMPLEX_XML_FILE1 = "src/test/resources/com/rapiddweller/common/xml/complexProps1.xml";
	private static final String COMPLEX_XML_FILE2 = "src/test/resources/com/rapiddweller/common/xml/complexProps2.xml";
	private static final String MERGED_COMPLEX_XML_FILE = "target/mergedComplex.xml";
	
	static {
		try {
			JAR_URL = new URL("file:src/test/resources/com/rapiddweller/common/file/JarWithProperties.jar");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testOverwritePropertiesWithVMParams() {
		// GIVEN a property x.y.z=1
		TreeBuilder tree = new TreeBuilder(true);
		tree.addLeafAtAbsolutePath("x/y/z", "1");
		
		// WHEN no VM setting is defined
		System.setProperty("x.y.z", "");
		// THEN the property shall not be overwritten
		PropertiesFileMerger.overwritePropertiesWithVMParams(tree);
		assertEquals("1", tree.getNodeValue("x/y/z"));
		
		// AND WHEN a VM setting is defined 
		System.setProperty("x.y.z", "2");
		// THEN it shall overwrite the property
		PropertiesFileMerger.overwritePropertiesWithVMParams(tree);
		assertEquals("2", tree.getNodeValue("x/y/z"));
	}

	@Test
	public void testLoadFileIfPresent() throws IOException {
		// GIVEN a property 'common.property' of value 'none'
		TreeBuilder tree = new TreeBuilder(false);
		tree.addLeafAtAbsolutePath("common/property", "none");
		
		// WHEN trying to load a non-existing file
		PropertiesFileMerger.loadFileIfPresent(NON_EXISTING_PROPERTIES_FILENAME, tree);
		// THEN the property shall not be changed
		assertEquals("none", tree.getNodeValue("common/property"));
		
		// AND WHEN loading an existing file with that property
		PropertiesFileMerger.loadFileIfPresent(FILE_PROPERTIES_FILENAME, tree);
		// THEN the setting is supposed to be changed
		assertEquals("loaded_from_file", tree.getNodeValue("common/property"));
	}

	@Test
	public void testMergeWithVMOverride() throws IOException {
		System.setProperty("common.property", "loaded_from_vm");
		PropertiesFileMerger.merge(MERGED_PROPERTIES_FILENAME, true, FILE_PROPERTIES_FILENAME, JAR_PROPERTIES_FILENAME);
		TreeBuilder tree = TreeBuilder.parseProperties(new FileInputStream(MERGED_PROPERTIES_FILENAME));
		assertEquals("loaded_from_vm", tree.getNodeValue("common/property"));
		assertEquals("loaded_from_file", tree.getNodeValue("file/property"));
		//assertEquals("loaded_from_jar", tree.getNodeValue("jar/property")); probably obsolete because of new Java Version
	}

	@Test
	public void testMergeWithoutVMOverride() throws IOException {
		System.setProperty("common.property", "loaded_from_vm");
		PropertiesFileMerger.merge(MERGED_PROPERTIES_FILENAME, false, FILE_PROPERTIES_FILENAME, JAR_PROPERTIES_FILENAME);
		TreeBuilder tree = TreeBuilder.parseProperties(new FileInputStream(MERGED_PROPERTIES_FILENAME));
		assertEquals("loaded_from_file", tree.getNodeValue("common/property"));
		assertEquals("loaded_from_file", tree.getNodeValue("file/property"));
		//assertEquals("loaded_from_jar", tree.getNodeValue("jar/property")); probably obsolete because of new Java Version
	}
	
	@Test
	public void testSimpleXMLFile() throws IOException {
		PropertiesFileMerger.merge(MERGED_SIMPLE_XML_FILENAME, true, FILE_XML_FILENAME);
		TreeBuilder tree = TreeBuilder.parseXML(new FileInputStream(MERGED_SIMPLE_XML_FILENAME));
		assertEquals("groupValue", tree.getNodeValue("root/group/groupProp"));
		assertEquals("", tree.getNodeValue("root/emptyProp"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testComplexXMLFile() throws IOException {
		PropertiesFileMerger.merge(MERGED_COMPLEX_XML_FILE, COMPLEX_XML_FILE1, COMPLEX_XML_FILE2);
		TreeBuilder tree = TreeBuilder.parseXML(new FileInputStream(MERGED_COMPLEX_XML_FILE));
		List<?> items = (List<?>) tree.getNodeValue("root/list/item");
		assertEquals(2, items.size());
		assertEquals("my.Class1", ((Map<String, Object>) items.get(0)).get("class"));
		assertEquals("my.Class3", ((Map<String, Object>) items.get(1)).get("class"));
	}
	
}
